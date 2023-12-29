<template>
  <div>
    <div class="search">
      <ElForm ref="queryFormRef" inline="true" labelWidth="200" model="queryFormModel">
        <ElFormItem label="设备编号" labelWidth="auto" prop="code" style="margin-bottom: 0">
          <ElInput v-model="queryFormModel.name" placeholder="输入设备编号" />
        </ElFormItem>
        <ElFormItem label="设备协议" labelWidth="auto" prop="protocol" style="margin-bottom: 0">
          <el-select
            v-model="queryFormModel.protocol"
            :placeholder="t('common.selectText')"
            style="width: 100%"
          >
            <el-option
              v-for="item in protocolOptions"
              :key="item.id"
              :label="item.name"
              :value="(item.id as unknown as number)"
            />
          </el-select>
        </ElFormItem>
        <ElFormItem style="margin-bottom: 0">
          <ElButton type="primary" @click="handleQuery" :loading="loading">
            <Icon icon="ep:search" />
          </ElButton>
          <ElButton @click="resetQuery" style="padding: '8px'">
            <Icon icon="ep:refresh" />
          </ElButton>
        </ElFormItem>
      </ElForm>
    </div>
    <ElCard :gutter="12" shadow="never" style="margin-top: 10px">
      <template #header>
        <div class="card-header">
          <el-row justify="space-between" style="margin-bottom: 0">
            <el-row :gutter="12" align="middle" class="mb8">
              <el-col :span="12">
                <XButton @click="handleAdd" type="primary" preIcon="ep:plus" title="新增" />
              </el-col>
            </el-row>
          </el-row>
        </div>
      </template>
      <el-row class="card-list flex">
        <el-col class="card-item" v-for="(item, index) in tableList" :key="index">
          <div class="text-box">
            <div class="title flex align-center">
              <div class="title-l">
                <div class="icon">
                  <svg
                    xmlns="http://www.w3.org/2000/svg"
                    fill="none"
                    viewBox="0 0 12 12"
                    class="design-iconfont"
                    width="14"
                    height="14"
                  >
                    <g clip-path="url(#j0mugonyr__clip0_251_330)">
                      <path
                        fill-rule="evenodd"
                        clip-rule="evenodd"
                        d="M6.95761 0.419307C6.44384 0.216676 5.61085 0.216676 5.09708 0.419307L1.14977 1.97613C0.37912 2.28007 0.379119 2.77286 1.14977 3.07681L5.09708 4.63363C5.61085 4.83626 6.44383 4.83626 6.9576 4.63363L10.9049 3.07681C11.6756 2.77287 11.6756 2.28007 10.9049 1.97613L6.95761 0.419307ZM4.71529 11.7889C4.81375 11.8261 4.9175 11.8449 5.01442 11.8449C5.39836 11.8449 5.68606 11.5502 5.68623 11.1445V6.40366C5.68623 5.91426 5.32209 5.39722 4.86887 5.22658L1.0909 3.80515C0.992271 3.76795 0.896713 3.74918 0.806615 3.74918C0.453732 3.74918 0.225586 4.02408 0.225586 4.44949V9.19038C0.225586 9.69786 0.555433 10.204 0.98937 10.3673L4.71529 11.7889ZM6.94351 11.7653H6.92337C6.94674 11.7653 6.97141 11.7667 6.99683 11.7681C7.07053 11.7722 7.15062 11.7767 7.22421 11.7491L10.9994 10.3675C11.4482 10.1987 11.8291 9.68148 11.8291 9.19038V4.44949C11.8291 4.03722 11.5383 3.74918 11.1564 3.74918C11.0481 3.74993 10.9407 3.76891 10.8387 3.80532L7.11859 5.22658C6.67936 5.39193 6.36862 5.89788 6.36862 6.40366V11.1445C6.36862 11.5633 6.58738 11.7653 6.94351 11.7653Z"
                        fill="url(#j0mugonyr__paint0_linear_251_330)"
                      />
                    </g>
                    <defs>
                      <linearGradient
                        id="j0mugonyr__paint0_linear_251_330"
                        x1=".225586"
                        y1=".267334"
                        x2="11.8031"
                        y2="11.8708"
                        gradientUnits="userSpaceOnUse"
                      >
                        <stop stop-color="#1B3149" />
                        <stop offset="1" stop-color="#717D8A" />
                      </linearGradient>
                      <clipPath id="j0mugonyr__clip0_251_330">
                        <path fill="#fff" d="M0 0H12V12H0z" />
                      </clipPath>
                    </defs>
                  </svg>
                </div>
                {{ item['name'] }}
              </div>
              <!-- 状态 -->
              <div class="title-r">
                <status-tag
                  :type="item['state'] ? 'success' : 'danger'"
                  :text="item['state'] ? '在线' : '离线'"
                />
              </div>
            </div>
            <div class="text flex">
              <div class="txt">
                <div class="txt-item">
                  <div class="label">设备编号</div>
                  <div class="value active">{{ item['code'] }}</div>
                </div>
                <div class="txt-item">
                  <div class="label">协议内容</div>
                  <div class="value active">{{ item['messageProtocol'] }}</div>
                </div>
                <template v-if="item['protocolType'] === 1">
                  <div class="txt-item">
                    <div class="label">设备串口</div>
                    <div class="value">{{ item['serialCom'] }}</div>
                  </div>
                  <div class="txt-item">
                    <div class="label">波特率</div>
                    <div class="value">{{ item['baudRate'] }}</div>
                  </div>
                </template>
                <template v-else>
                  <div class="txt-item">
                    <div class="label">设备地址</div>
                    <div class="value">{{ item['host'] }}</div>
                  </div>
                  <div class="txt-item">
                    <div class="label">设备端口</div>
                    <div class="value">{{ item['port'] }}</div>
                  </div>
                </template>
              </div>
              <div class="img">
                <img src="@/assets/imgs/pic_device.png" alt="" />
              </div>
            </div>
          </div>
          <div class="btn-group">
            <!-- pre-icon="ep:edit-pen" -->
            <XButton
              class="cu-btn"
              type="primary"
              plain
              pre-icon="ep:edit-pen"
              title="编辑"
              @click="handleUpdate(item['id'])"
            />
            <!-- pre-icon="ep:view" -->
            <!-- <XButton
              class="cu-btn"
              type="success"
              pre-icon="ep:circle-check"
              plain
              @click="handleRun(item)"
              title="启动"
            /> -->

            <XButton
              class="cu-btn"
              type="success"
              pre-icon="ep:circle-check"
              plain
              @click="handleTest(item)"
              title="调试"
            />
            <el-divider direction="vertical" />
            <el-popconfirm title="是否确认删除?" @confirm="handleDel(item['id'])">
              <template #reference>
                <XButton type="danger" pre-icon="ep:delete" plain />
              </template>
            </el-popconfirm>
          </div>
        </el-col>
      </el-row>
      <div class="pagination-container">
        <el-pagination
          :current-page="queryFormModel.pageNum"
          :page-size="queryFormModel.pageSize"
          layout="total, sizes, prev, pager, next, jumper"
          :page-sizes="[5, 10, 20, 30, 50, 100]"
          :total="queryFormModel.totalResult || 0"
          @size-change="handleSizeChange"
          @current-change="handleCurrentChange"
        />
      </div>
    </ElCard>
  </div>
  <XModal v-model="dialogVisible" :title="dialogTitle" height="60%" width="30%">
    <el-form ref="formRef" :model="formData" :rules="rules">
      <el-form-item label="设备名称" prop="name">
        <el-input v-model="formData.name" />
      </el-form-item>
      <el-form-item label="设备编号" prop="code">
        <el-input v-model="formData.code" />
      </el-form-item>
      <el-form-item label="设备协议" prop="protocol">
        <el-select
          v-model="formData.protocol"
          :placeholder="t('common.selectText')"
          style="width: 100%"
          @change="handleProtocolChange"
        >
          <el-option
            v-for="item in protocolOptions"
            :key="item.id"
            :label="item.name"
            :value="(item.id as unknown as number)"
          />
        </el-select>
      </el-form-item>
      <template v-for="column in columns" :key="column.prop">
        <el-form-item
          :prop="column.prop"
          :label="column.label"
          :rules="column.rules"
          :title="column.tip"
        >
          <el-input
            v-if="!column.type || column.type == 'input'"
            v-model.trim="formData[column.prop]"
            placeholder="请输入"
          />
          <el-input-number
            style="width: 100%"
            v-else-if="column.type == 'number'"
            v-model="formData[column.prop]"
            controls-position="right"
            :min="0"
          />
          <el-select
            clearable
            v-else-if="column.type == 'select'"
            v-model="formData[column.prop]"
            placeholder="请选择"
            style="width: 100%"
          >
            <el-option
              v-for="rv in column.dicData"
              :label="rv.label"
              :key="rv.value"
              :value="rv.value"
            />
          </el-select>
        </el-form-item>
      </template>
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
      <XButton :loading="actionLoading" :title="t('dialog.close')" @click="dialogVisible = false" />
    </template>
  </XModal>
  <plc-test v-model="plcVisible" :deviceObj="deviceObj" />
</template>
<script setup lang="ts" name="IotDevice">
import * as IotDeviceApi from '@/api/iot/device'
import { IotProtocolVO, getProtocolListApi } from '@/api/iot/protocol'
import { cloneDeep } from 'lodash-es'
import { useUserStore } from '@/store/modules/user'
import { useWebSocket } from '@vueuse/core'
import { PlcTest } from './src'
const { t } = useI18n()
const message = useMessage()

const userStore = useUserStore()

const server = ref(
  (import.meta.env.VITE_BASE_URL + '/websocket/message').replace('http', 'ws') +
    '?userId=' +
    userStore.getUser.id
)
// const getIsOpen = computed(() => status.value === 'OPEN')

const { status, data, open } = useWebSocket(server.value, {
  autoReconnect: false,
  heartbeat: true
})

const { emitter } = useEmitt()

watchEffect(() => {
  console.log(status.value)
  if (status.value === 'CLOSED') {
    open()
  }
})

watchEffect(() => {
  if (data.value) {
    try {
      const res = JSON.parse(data.value)
      emitter.emit('messageEmitt', res)
    } catch (error) {
      console.log(error)
    }
  }
})

// data
// const defaultImg = ref('@/assets/imgs/pic_device.png')
const queryFormModel = reactive({
  name: '',
  protocol: null,
  totalResult: 0,
  pageNum: 1,
  pageSize: 20
})
const loading = ref(false)
const queryFormRef = ref(null)

const tableList = ref([])
const deviceObj = ref({})
//
const dialogVisible = ref(false)
const dialogTitle = ref('')
const actionType = ref('create')
const formRef = ref()
const root = {
  id: 0,
  name: '', // 设备名称
  code: '', // 设备编号
  protocol: null, // 消息协议
  connectMode: '', // 连接方式
  locationLon: '',
  locationLat: ''
}
const formData = reactive(cloneDeep(root))
// rules
const rules = reactive({
  name: required,
  code: required,
  protocol: required
})
const protocolOptions = reactive<IotProtocolVO[]>([])

interface CommonColumn {
  prop: string
  label: string
  tip: string
  type: string
  rules: any
  dicData?: IotDeviceApi.SelectDic[]
}
const columns = ref<CommonColumn[]>([])
const actionLoading = ref(false)

const plcVisible = ref(false)
//method

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
      IotDeviceApi.createIotDeviceApi(formData)
      message.success(t('common.createSuccess'))
    } else {
      IotDeviceApi.updateIotDeviceApi(formData)
      message.success(t('common.updateSuccess'))
    }
    dialogVisible.value = false
    handleIotDevicePage()
  } finally {
    actionLoading.value = false
  }
}

const handleProtocolChange = async (id) => {
  const protocol = unref(protocolOptions).find((item) => item.id === id)
  if (protocol === undefined) return
  const { protocolType } = protocol

  if (protocolType === 1) {
    const ports = await IotDeviceApi.getSerialPorts()
    columns.value = [
      ...[
        {
          prop: 'serialCom',
          label: '串口',
          tip: '串口',
          type: 'select',
          rules: [{ required: true, message: '串口必填' }],
          dicData: ports
        },
        {
          prop: 'baudRate',
          label: '波特率',
          tip: '波特率',
          type: 'number',
          rules: [{ required: true, message: '串口波特率必填' }]
        },
        {
          prop: 'dataBits',
          label: '数据位',
          tip: '数据位',
          type: 'number',
          rules: [{ required: true, message: '串口数据位必填' }]
        },
        {
          prop: 'stopBits',
          label: '停止位',
          tip: '停止位',
          type: 'number',
          rules: [{ required: true, message: '串口停止位必填' }]
        },
        {
          prop: 'parityBits',
          label: '校验位',
          tip: '校验位',
          type: 'number',
          rules: [{ required: true, message: '串口校验位必填' }]
        }
      ]
    ]
  } else {
    columns.value = [
      ...[
        {
          prop: 'host',
          label: '设备地址',
          tip: '设备地址',
          type: 'input',
          rules: [{ required: true, message: '设备地址必填' }]
        },
        {
          prop: 'port',
          label: '设备端口',
          tip: '设备端口',
          type: 'number',
          rules: [{ required: true, message: '设备端口必填' }]
        }
      ]
    ]
  }
}

// const IotDeviceApi = (data) => {
//   if (unref(dialogVisible)) {
//     const { id, name, code } = data
//     Object.assign(formData, { machineId: id, name: name, deviceSn: code })
//   }
//   machineVisible.value = false
// }

const handleAdd = () => {
  setDialogTile('create')
}

const setDialogTile = (type: string) => {
  rest()
  dialogTitle.value = t('action.' + type)
  actionType.value = type
  dialogVisible.value = true
}

//清空
const rest = () => {
  for (const key in formData) {
    delete formData[key]
  }
  Object.assign(formData, cloneDeep(root))

  columns.value = []
}

const handleUpdate = async (rowId: number) => {
  setDialogTile('update')
  await nextTick()
  // 设置数据
  const res = await IotDeviceApi.getIotDeviceApi(rowId)
  const { protocol } = res
  handleProtocolChange(protocol)
  Object.assign(formData, res)
}

// const handleView = (rowId: number) => {
//   console.log(rowId)
// }
//调试
const handleTest = async (row) => {
  const { id, name } = row
  deviceObj.value = { id: id, name: name }
  plcVisible.value = true
}

const handleDel = async (rowId: number) => {
  await IotDeviceApi.deleteIotDeviceApi(rowId)
  handleIotDevicePage()
}

const handleSizeChange = (val: number) => {
  queryFormModel.pageSize = val
  handleIotDevicePage()
}

const handleCurrentChange = (val: number) => {
  queryFormModel.pageNum = val
  handleIotDevicePage()
}

const handleQuery = () => {
  IotDeviceApi.testApi()
  handleIotDevicePage()
}

const resetQuery = () => {
  Object.assign(queryFormModel, { name: '' })
  handleIotDevicePage()
}

// created
const handleIotDevicePage = async () => {
  const res = await IotDeviceApi.getIotDevicePageApi(unref(queryFormModel))
  const { list, total } = res
  tableList.value = list
  queryFormModel.totalResult = total
}

const getIotDeviceProtocol = async () => {
  const res = await getProtocolListApi()
  protocolOptions.push(...res)
}

// created
onMounted(async () => {
  await handleIotDevicePage()
  await getIotDeviceProtocol()
})
</script>
<style>
.search {
  margin-bottom: 0.75rem;
  border-radius: 0.25rem;
  border: 1px solid var(--el-border-color-light);
  background-color: var(--el-bg-color-overlay);
  padding: 16px;
}
.card-list {
  .card-item {
    border: 1px solid #d8dee5;
    border-radius: 3px;
    margin-right: 16px;
    margin-bottom: 16px;
    flex: 0 0 calc(25% - 12px);
    width: calc(25% - 12px);
    &:nth-child(4n) {
      margin-right: 0;
    }
    .text-box {
      padding: 16px;
      .title {
        font-size: 16px;
        font-weight: 600;
        align-items: center;
        margin-bottom: 12px;
        justify-content: space-between;
        .title-l {
          display: flex;
          align-items: center;
        }
        .icon {
          margin-right: 10px;
          display: flex;
          align-items: center;
        }
      }
      .text {
        align-items: center;
        font-size: 14px;
        .txt {
          flex: 1;
          .txt-item {
            margin-bottom: 10px;
            &:last-child {
              margin-bottom: 0;
            }
          }
          .label {
            display: inline-block;
            margin-right: 10px;
            color: #717c8e;
          }
          .value {
            display: inline-block;
            color: #0b1d30;
            &.active {
              color: #0070ff;
            }
          }
        }
        .img {
          width: 100px;
          height: 100px;
          img {
            width: 100%;
            height: auto;
          }
        }
      }
    }
    .btn-group {
      padding: 12px 16px;
      border-top: 1px solid #dcdfe1;
      .cu-btn {
        width: calc((100% - 76px) / 2);
      }
      .el-button {
        padding: 8px;
      }
    }
  }
}
@media screen and (max-width: 1560px) {
  .card-list .card-item .btn-group {
    padding: 12px;
    .el-button {
      font-size: 12px;
    }
    .cu-btn {
      width: calc((100% - 59px) / 3);
    }
    .el-button + .el-button {
      margin-left: 6px;
    }
  }
}
@media screen and (max-width: 1400px) {
  .card-list .card-item {
    width: calc(100% / 3 - 8px);
    flex: 0 0 calc(100% / 3 - 8px);
    margin-right: 12px;
    &:nth-child(4n) {
      margin-right: 12px;
    }
    &:nth-child(3n) {
      margin-right: 0;
    }
  }
}
.pagination-container {
  background: #fff;
  padding: 32px 16px;
  .el-pagination {
    float: right;
  }
}
</style>
