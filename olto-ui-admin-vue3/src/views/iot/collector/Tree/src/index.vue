<template>
  <div class="tree-container">
    <el-input v-model="searchValue" style="margin-bottom: 8px" placeholder="请输入" allowClear>
      <template #append>
        <el-button @click="handleSearch()">
          <Icon icon="ep:search" />
        </el-button>
      </template>
    </el-input>

    <div class="add-btn">
      <XButton
        class="add-btn"
        type="primary"
        @click="handlAdd()"
        title="新增采集器"
        pre-icon="ep:plus"
      />
    </div>
    <el-tree
      :data="dataSource"
      node-key="id"
      default-expand-all
      :expand-on-click-node="false"
      @node-click="handleNodeClick"
    >
      <template #default="{ data }">
        <span class="custom-tree-node">
          <span class="custom-tree-title">
            <span class="tree-left-title"> {{ data.name }} </span>
            <ElTag class="tree-left-tag" type="info" effect="plain" v-if="data.id !== '*'">
              {{ data?.runningState === 1 ? '未运行' : '运行中' }}
            </ElTag>
            <ElTag class="tree-left-tag2" effect="plain" v-if="data.id !== '*'">
              {{ data?.state === 1 ? '正常' : '禁用' }}
            </ElTag>
            <span v-if="data.id !== '*'" class="func-btns">
              <el-tooltip class="box-item" effect="dark" content="编辑" placement="top-start">
                <XTextButton
                  class="tree-compatible-wrapper"
                  pre-icon="ep:edit-pen"
                  @click="handlEdit(data.id)"
                  tag="div"
                />
              </el-tooltip>
              <el-tooltip
                class="box-item"
                effect="dark"
                :content="data['runningState'] === 1 ? '运行' : '停止'"
                placement="top-start"
              >
                <XTextButton
                  class="tree-compatible-wrapper"
                  :pre-icon="data.runningState === 1 ? 'ep:caret-right' : 'ep:caret-left'"
                  @click="handleUse(data.id)"
                />
              </el-tooltip>
              <el-tooltip class="box-item" effect="dark" content="删除" placement="top-start">
                <XTextButton
                  class="tree-compatible-wrapper"
                  pre-icon="ep:delete"
                  @click="handlDelete(data.id)"
                />
              </el-tooltip>
            </span>
          </span>
        </span>
      </template>
    </el-tree>
    <XModal v-model="dialogVisible" :title="dialogTitle">
      <el-form ref="formRef" :model="formData" :rules="rules">
        <el-form-item label="采集名称" prop="name">
          <el-input placeholder="请输入采集名称" v-model="formData.name" />
        </el-form-item>
        <!-- <el-form-item label="所属设备" prop="deviceId">
          <el-select
            style="width: 100%"
            v-model="formData.deviceId"
            placeholder="请选择所属设备"
            allowClear
            show-search
          >
            <el-option
              v-for="item in deviceOptions"
              :key="item.id"
              :label="item.name"
              :value="(item.id as unknown as number)"
            />
          </el-select>
        </el-form-item> -->
        <!-- <el-form-item label="采集动作" prop="collectAction">
          <el-input placeholder="请输入采集动作" v-model="formData.collectAction" />
        </el-form-item> -->
        <el-form-item label="采集说明" prop="description">
          <el-input
            placeholder="请输入采集说明"
            type="textarea"
            v-model="formData.description"
            :autosize="{ minRows: 2, maxRows: 4 }"
          />
        </el-form-item>
      </el-form>
      <!-- 操作按钮 -->
      <template #footer>
        <!-- 按钮：保存 -->
        <XButton
          type="primary"
          :title="t('action.save')"
          :loading="actionLoading"
          @click="submitForm()"
        />
        <!-- 按钮：关闭 -->
        <XButton
          :loading="actionLoading"
          :title="t('dialog.close')"
          @click="dialogVisible = false"
        />
      </template>
    </XModal>
  </div>
</template>

<script setup lang="ts" name="IotCollectorTree">
import { XButton } from '@/components/XButton'
import { XModal } from '@/components/XModal'
import { ElTag } from 'element-plus'

import * as collectorApi from '@/api/iot/collector'
import { geIotDeviceListApi, IotDeviceVO } from '@/api/iot/device'
import { cloneDeep } from 'lodash-es'

const { t } = useI18n()
const message = useMessage()
const emits = defineEmits(['change'])

// data
const searchValue = ref()

const root = [
  {
    id: '*',
    name: '全部',
    children: []
  }
]

const dataSource: any = ref([...cloneDeep(root)])

const dialogVisible = ref(false)
const dialogTitle = ref('')
const formRef = ref()
const actionType = ref('create')
const actionLoading = ref(false)

const formRoot = {
  name: '',
  deviceId: null,
  collectAction: '',
  description: ''
}
const formData = reactive(cloneDeep(formRoot))

const rules = reactive({
  name: required,
  deviceId: required
  // collectAction: required
})

const deviceOptions = ref<IotDeviceVO[]>([])

const selectedKey = ref('')

watch(
  () => selectedKey.value,
  (id) => {
    if (id) {
      emits('change', id)
    }
  }
)
//methods
//submit form
const submitForm = async () => {
  const form = unref(formRef)
  if (!form) return
  const data = await form.validate()
  if (!data) {
    return
  }
  actionLoading.value = true
  try {
    if (actionType.value === 'create') {
      collectorApi.createCollectorApi(formData)
      message.success(t('common.createSuccess'))
    } else {
      collectorApi.updateCollectorApi(formData)
      message.success(t('common.updateSuccess'))
    }
    await getCollectorList()
    dialogVisible.value = false
  } finally {
    actionLoading.value = false
  }
}
const handleSearch = () => {}

const setDialogTile = (type: string) => {
  rest()
  dialogTitle.value = t('action.' + type)
  actionType.value = type
  dialogVisible.value = true
}

const handlAdd = () => {
  setDialogTile('create')
}

const rest = () => {
  for (const key in formData) {
    delete formData[key]
  }
  Object.assign(formData, cloneDeep(formRoot))
}

// 点击行
const handleNodeClick = async (row: { [key: string]: any }) => {
  selectedKey.value = row?.id
}

const handlEdit = async (rowId: number) => {
  setDialogTile('update')
  await nextTick()
  const res = await collectorApi.getCollectorApi(rowId)
  Object.assign(formData, { ...res })
}

const handleUse = async (rowId: number) => {
  try {
    await collectorApi.runCollectorApi(rowId)
    getCollectorList()
  } catch (error) {}
}

const handlDelete = async (rowId: number) => {
  message.confirm('是否确认删除?', t('common.reminder')).then(async () => {
    await collectorApi.deleteCollectorApi(rowId)
    await getCollectorList()
  })
}

const getCollectorList = async () => {
  const res = await collectorApi.getCollectororListApi()
  dataSource.value = [...cloneDeep(root)]
  dataSource.value[0].children = res
}

const getDevicelList = async () => {
  const res = await geIotDeviceListApi()
  deviceOptions.value.push(...res)
}
// created
onMounted(async () => {
  await getCollectorList()
  await getDevicelList()
})
</script>

<style scoped lang="scss">
.tree-container {
  padding-right: 24px;
  width: 370px;
  .add-btn {
    margin: 10px 0;

    :deep(.ant-btn-primary) {
      width: 100%;
    }
  }
  .custom-tree-node {
    flex: 1 1 auto;
    position: relative;
    z-index: auto;
    min-height: 24px;
    margin: 0;
    padding: 0 4px;
    color: inherit;
    line-height: 24px;
    background: transparent;
    border-radius: 2px;
    cursor: pointer;
    transition: all 0.3s, border 0s, line-height 0s, box-shadow 0s;
    .custom-tree-title {
      display: flex;
      justify-content: space-between;
      align-items: center;

      .tree-left-title {
        display: -webkit-box;
        -webkit-box-orient: vertical;
        word-break: break-all;
        overflow: hidden;
        vertical-align: bottom;
      }
      .tree-left-tag {
        width: 70px;
        display: flex;
        justify-content: center;
        margin: 0 8px 0 0;
      }
      .tree-left-tag2 {
        width: 50px;
        display: flex;
        justify-content: center;
        margin: 0 8px 0 0;
      }
      .func-btns {
        display: block;
        font-size: 14px;
        width: 100px;
        margin-right: -10px;
        .tree-compatible-wrapper {
          margin-left: 2px;
          display: inline-block;
          // cursor: not-allowed;
          line-height: 1;
        }
      }
    }
  }
}
</style>
