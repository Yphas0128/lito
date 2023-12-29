<template>
  <div class="full-page-warp" ref="fullPage" :style="{ minHeight: `calc(100vh - ${y + 24}px)` }">
    <div class="full-page-warp-content">
      <div class="jtable-body">
        <div class="jtable-body-header" v-if="!!queryParams.collectorId">
          <XButton type="primary" @click="handlAddPonit" preIcon="ep:plus" title="添加点位" />
        </div>
        <div class="jtable-content">
          <div class="jtable-card">
            <div class="jtable-item" v-for="(item, index) in data" :key="index">
              <div class="card">
                <div class="card-warp">
                  <div class="card-content">
                    <div class="card-header">
                      <div class="card-header-left j-ellipsis">
                        <div class="card-box-title">
                          {{ item.deviceName }}: {{ item.name }}(点位)
                        </div>
                      </div>
                      <div class="card-header-right">
                        <div class="card-box-action">
                          <XButton
                            type="danger"
                            preIcon="ep:delete"
                            @click="handleDelete(item?.id)"
                            plain
                          />
                        </div>
                      </div>
                    </div>
                    <!-- @click="handleClick"  -->
                    <div class="card-item" style="display: flex">
                      <!-- 图片 -->
                      <div class="card-item-avatar">
                        <img src="@/assets/imgs/device-modbus.png" />
                      </div>

                      <!-- 内容 -->
                      <div class="card-item-body">
                        <div class="card-box-content">
                          <div class="card-box-content-left">
                            <div class="card-box-content-left-1">
                              <!-- <div class="ard-box-content-left-1-title">
                                <span class="j-ellipsis" style="max-width: 150px">
                                  {{ item.name }}
                                </span>
                              </div> -->
                              <div class="ard-box-content-left-1-title">
                                <span class="j-ellipsis"> -- </span>
                              </div>
                              <XTextButton
                                preIcon="ep:edit"
                                style="margin-left: 12px"
                                @click="handleEditDevice(item)"
                              />
                              <!-- <XTextButton preIcon="ep:reading" /> -->
                            </div>
                          </div>
                          <div class="card-box-content-right">
                            <span
                              class="j-ellipsis"
                              style="width: calc(100% - 10px); margin-bottom: 10px"
                            >
                              <div class="card-box-content-right-1">
                                <span> {{ item.num }}(寄存器数量) </span>
                                <span> {{ item.address }}(地址) </span>
                              </div>
                            </span>
                            <span
                              class="j-ellipsis"
                              style="width: calc(100% - 10px); margin-bottom: 10px"
                            >
                              <div class="card-box-content-right-2">
                                <span>读,写</span>
                                <span>采集频率{{ item.frequency }}s</span>
                              </div>
                            </span>
                          </div>
                        </div>
                      </div>
                    </div>
                    <!-- <div class="card-state success">
                      <div class="card-state-content">
                        <span class="card-badge">
                          <span class="card-badge-status-dot success"></span>
                          <span class="card-badge-status-text">未运行</span>
                        </span>
                      </div>
                    </div> -->
                  </div>
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
    <device-point-modal v-model="devicePointVisible" @register-point="registerPoint" />
    <XModal v-model="dialogVisible" title="添加点位" height="200" width="500">
      <el-form ref="formRef" :model="formData" :rules="rules">
        <el-form-item label="采集频率(秒)" prop="frequency">
          <el-select
            placeholder="请选择采集频率(秒)"
            v-model="formData.frequency"
            style="width: 100%"
          >
            <el-option
              v-for="item in frequencyOptions"
              :key="item.value"
              :label="item.label"
              :value="(item.value as unknown as number)"
            />
          </el-select>
        </el-form-item>

        <el-form-item label="设备点位" prop="pointName">
          <el-input disabled placeholder="请输入" v-model="formData.pointName" style="width: 100%">
            <template #append>
              <ElButton>
                <Icon icon="ep:search" @click="handleDeviceClick" />
              </ElButton>
            </template>
          </el-input>
        </el-form-item>
      </el-form>
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
    <XModal v-model="deviceVisible" title="写入" height="200" width="500">
      <el-form ref="deviceFormRef" :model="deviceFormData" :rules="deviceRules">
        <el-form-item :label="writeLabel" prop="val">
          <el-input placeholder="请输入" v-model="deviceFormData.val" style="width: 100%" />
        </el-form-item>
      </el-form>
      <template #footer>
        <!-- 按钮：保存 -->
        <XButton
          type="primary"
          :title="t('action.save')"
          :loading="actionLoading"
          @click="submitDeviceForm()"
        />
        <!-- 按钮：关闭 -->
        <XButton
          :loading="actionLoading"
          :title="t('dialog.close')"
          @click="deviceVisible = false"
        />
      </template>
    </XModal>
  </div>
</template>
<script lang="ts" setup name="IotCollectorPoint">
import { useElementBounding } from '@vueuse/core'
import * as collectorApi from '@/api/iot/collector'
import { DictDataVO } from '@/api/system/dict/types'
import { getEnumListByType } from '@/api/enums'
import DevicePointModal from './components/DevicePointModal.vue'
import { cloneDeep } from 'lodash-es'
// import BadgeStatus from '@/components/BadgeStatus/index.vue'

const fullPage = ref(null)
const { y } = useElementBounding(fullPage)
const { t } = useI18n()
const message = useMessage()

const props = defineProps({
  data: {
    type: Object,
    default() {
      return {
        collectorId: null
      }
    }
  }
})

const queryParams = reactive({
  collectorId: null,
  totalResult: 0,
  pageNum: 1,
  pageSize: 20
})
const data = ref<collectorApi.CollectorPointVO[]>([])

const devicePointVisible = ref(false)
const actionLoading = ref(false)

const writeLabel = ref('')
const deviceVisible = ref(false)
const deviceFormData = reactive({
  pointId: null,
  val: ''
})
const deviceRules = reactive({
  val: required
})
const deviceFormRef = ref()

const dialogVisible = ref(false)
const formRoot = {
  frequency: null,
  pointName: '',
  pointId: null,
  deviceId: null
}
const formData = reactive(cloneDeep(formRoot))
const rules = reactive({
  frequency: required,
  pointName: required
})

const formRef = ref()

// 频率
const frequencyOptions = ref<DictDataVO[]>([])

// method
const handlAddPonit = () => {
  dialogVisible.value = true
}

const handleDeviceClick = async () => {
  devicePointVisible.value = true
}

const submitForm = async () => {
  const form = unref(formRef)
  if (!form) return
  const data = await form.validate()
  if (!data) {
    return
  }
  actionLoading.value = true
  try {
    await collectorApi.createCollectorPointApi(formData)
    dialogVisible.value = false
    await getCollectorPointPage()
    // getPointPage()
  } finally {
    actionLoading.value = false
  }
}

const registerPoint = async (value) => {
  Object.assign(formData, value, { collectorId: unref(queryParams).collectorId })
  devicePointVisible.value = false
}

const handleDelete = (id) => {
  message.confirm('确认要删除', t('common.reminder')).then(async () => {
    await collectorApi.deleteCollectorPointApi(id)
    getCollectorPointPage()
  })
}

const handleEditDevice = (item) => {
  const { name, id } = item
  writeLabel.value = name
  Object.assign(deviceFormData, { pointId: id })
  deviceVisible.value = true
}

const submitDeviceForm = async () => {
  const form = unref(deviceFormRef)
  if (!form) return
  const data = await form.validate()
  if (!data) {
    return
  }
  actionLoading.value = true
  try {
    collectorApi.writePointApi(deviceFormData)
    deviceVisible.value = false
    // getPointPage()
  } finally {
    actionLoading.value = false
  }
}

const getCollectorPointPage = async () => {
  const res = await collectorApi.getCollectorPointPageApi(queryParams)
  const { list, total } = res
  data.value = [...list]
  Object.assign(queryParams, { totalResult: total })
}

watch(
  () => props.data,
  (value) => {
    const { collectorId } = value
    if (!!collectorId) {
      if (collectorId === '*') {
        Object.assign(queryParams, { collectorId: null })
      } else {
        Object.assign(queryParams, { collectorId: collectorId })
        // Object.assign(formData, { collectorId: collectorId })
        // getChannelType(collectorId)
        getCollectorPointPage()
      }
    }
  },
  { immediate: true, deep: true }
)

// created
const getFrequencyList = async () => {
  const res = await getEnumListByType('FrequencyEnum')
  const result = res.map((r) => {
    return {
      value: r.value,
      label: r.value
    }
  })
  frequencyOptions.value.push(...result)
}

onMounted(async () => {
  await getFrequencyList()
})
</script>
<style scoped lang="scss">
.j-ellipsis {
  display: -webkit-box;
  -webkit-box-orient: vertical;
  word-break: break-all;
  overflow: hidden;
  vertical-align: bottom;
}
.full-page-warp {
  background: #fff;
  display: flex;
  .full-page-warp-content {
    width: 100%;
    .jtable-body {
      width: 100%;
      box-sizing: border-box;
      height: 100%;
      display: flex;
      flex-direction: column;
      .jtable-body-header {
        display: flex;
        justify-content: space-between;
        align-items: center;
      }
      .jtable-content {
        flex: 1;
        padding: 16px 0;
        .jtable-card {
          grid-template-columns: repeat(2, 1fr);
          display: grid;
          grid-gap: 26px;
          .jtable-item {
            display: flex;
            min-width: 0;
            .card {
              width: 100%;
              background-color: #fff;
              .checked-icon {
                position: absolute;
                right: -22px;
                bottom: -22px;
                z-index: 2;
                width: 44px;
                height: 44px;
                color: #fff;
                background-color: #2f54eb;
                transform: rotate(-45deg);

                > div {
                  position: relative;
                  height: 100%;
                  right: -2px;
                  bottom: -4px;
                  transform: rotate(45deg);

                  > span {
                    position: absolute;
                    // top: 6px;
                    // left: 6px;
                    font-size: 12px;
                  }
                }
              }

              .card-warp {
                position: relative;
                border: 1px solid #e6e6e6;

                &:hover {
                  // cursor: pointer;
                  box-shadow: 0 0 24px rgba(#000, 0.1);

                  .card-mask {
                    visibility: visible;
                  }
                }

                &.active {
                  position: relative;
                  border: 1px solid #2f54eb;
                }

                .card-content {
                  position: relative;
                  padding: 40px 12px 16px 30px;
                  overflow: hidden;

                  &::before {
                    position: absolute;
                    top: 0;
                    left: 30px + 10px;
                    display: block;
                    width: 15%;
                    min-width: 64px;
                    height: 2px;
                    // background-image: url('/images/rectangle.png');
                    background-repeat: no-repeat;
                    background-size: 100% 100%;
                    content: ' ';
                  }

                  .card-header {
                    // display: flex;
                    position: absolute;
                    left: 133px;
                    top: 20px;
                    // background: #5995f5;
                    // width: calc(100% - 100px);
                    width: calc(100% - 150px);
                    display: flex;
                    justify-content: space-between;
                    align-items: center;
                    .card-header-left {
                      flex: 1;
                    }
                    .card-box-action {
                      width: 50px;
                      display: flex;
                      justify-content: space-between;
                      align-items: center;
                      margin-top: -10px;
                      .add-btn {
                        height: 32px;
                        padding: 4px 15px;
                      }
                    }
                  }

                  .card-item {
                    cursor: pointer;
                  }
                  .card-item-avatar {
                    margin-right: 16px;
                  }

                  .card-item-body {
                    display: flex;
                    flex-direction: column;
                    flex-grow: 1;
                    width: 0;
                    .card-box-content {
                      font-size: 14px;
                      margin-top: 20px;
                      display: flex;
                      .card-box-content-left {
                        max-width: 220px;
                        border-right: 1px solid #e0e4e8;
                        height: 68px;
                        padding-right: 10px;
                        .card-box-content-left-1 {
                          display: flex;
                          justify-content: flex-start;
                          .card-box-content-left-1-title {
                            color: #000;
                            font-size: 20px;
                            opacity: 0.85;
                          }
                        }
                        a {
                          margin-left: 10px;
                        }
                      }
                      .card-box-content-right {
                        flex: 0.8;
                        margin-left: 20px;
                        .card-box-content-right-1 {
                          span {
                            margin: 0 5px 0 0;
                          }
                        }
                        .card-box-content-right-2 {
                          span {
                            margin: 0 5px 0 0;
                            padding: 3px 12px;
                            background: #f3f3f3;
                            color: #616161;
                          }
                        }
                      }
                    }
                  }

                  .card-state {
                    position: absolute;
                    top: 0px;
                    left: -12px;
                    display: flex;
                    justify-content: center;
                    width: 100px;
                    padding: 2px 0;
                    background-color: rgba(#5995f5, 0.15);
                    transform: skewX(-45deg);

                    &.success {
                      background-color: #f6ffed;
                    }

                    &.warning {
                      background-color: rgba(#ff9000, 0.1);
                    }

                    &.error {
                      background-color: rgba(#e50012, 0.1);
                    }

                    .card-state-content {
                      transform: skewX(45deg);
                      .card-badge {
                        box-sizing: border-box;
                        margin: 0;
                        padding: 0;
                        color: #000000d9;
                        font-size: 14px;
                        font-variant: tabular-nums;
                        line-height: 1.5715;
                        list-style: none;
                        font-feature-settings: 'tnum';
                        position: relative;
                        display: inline-block;

                        .card-badge-status-dot {
                          position: relative;
                          top: -1px;
                          display: inline-block;
                          width: 6px;
                          height: 6px;
                          vertical-align: middle;
                          border-radius: 50%;
                          &.success {
                            background: rgb(36, 178, 118);
                          }
                        }
                        .card-badge-status-text {
                          margin-left: 8px;
                          color: #000000d9;
                          font-size: 14px;
                        }
                      }
                    }
                  }

                  :deep(.card-item-content-title) {
                    cursor: pointer;
                    font-size: 16px;
                    font-weight: 700;
                    color: #315efb;
                    width: calc(100% - 100px);
                    overflow: hidden;
                    white-space: nowrap;
                    text-overflow: ellipsis;
                  }

                  :deep(.card-item-heard-name) {
                    font-weight: 700;
                    font-size: 16px;
                    margin-bottom: 12px;
                  }

                  :deep(.card-item-content-text) {
                    color: rgba(0, 0, 0, 0.75);
                    font-size: 12px;
                  }
                }

                .card-mask {
                  position: absolute;
                  top: 0;
                  left: 0;
                  z-index: 2;
                  display: flex;
                  align-items: center;
                  justify-content: center;
                  width: 100%;
                  height: 100%;
                  color: #fff;
                  background-color: rgba(#000, 0.5);
                  visibility: hidden;
                  cursor: pointer;
                  transition: all 0.3s;

                  .mask-content {
                    display: flex;
                    align-items: center;
                    justify-content: center;
                    width: 100%;
                    height: 100%;
                    padding: 0 !important;
                  }
                }
              }

              &.item-active {
                position: relative;
                color: #2f54eb;

                .checked-icon {
                  display: block;
                }

                .card-warp {
                  border: 1px solid #2f54eb;
                }
              }

              .card-tools {
                display: flex;
                margin-top: 8px;

                .card-button {
                  display: flex;
                  flex-grow: 1;

                  & > :deep(span, button) {
                    width: 100%;
                    border-radius: 0;
                  }

                  :deep(button) {
                    width: 100%;
                    border-radius: 0;
                    background: #f6f6f6;
                    border: 1px solid #e6e6e6;
                    color: #2f54eb;

                    &:hover {
                      background-color: #5c85ff;
                      border-color: #5c85ff;

                      span {
                        color: #fff !important;
                      }
                    }

                    &:active {
                      background-color: #2041d4;
                      border-color: #2041d4;

                      span {
                        color: #fff !important;
                      }
                    }
                  }

                  &:not(:last-child) {
                    margin-right: 8px;
                  }

                  &.delete {
                    flex-basis: 60px;
                    flex-grow: 0;

                    :deep(button) {
                      background: f6ffed;
                      border: 1px solid rgba(255, 77, 79, 0.2);

                      span {
                        color: #ff4d4f !important;
                      }

                      &:hover {
                        background-color: #ff7875;

                        span {
                          color: #fff !important;
                        }
                      }

                      &:active {
                        background-color: #d9363e;

                        span {
                          color: #fff !important;
                        }
                      }
                    }
                  }

                  :deep(button[disabled]) {
                    background: #000000d9;
                    border-color: #000000d9;

                    span {
                      color: #000000d9 !important;
                    }

                    &:hover {
                      background-color: #00000040;
                    }

                    &:active {
                      background-color: #00000040;
                    }
                  }

                  // :deep(.ant-tooltip-disabled-compatible-wrapper) {
                  //     width: 100%;
                  // }
                }
              }
            }
          }
        }
      }
    }
  }
}
</style>
