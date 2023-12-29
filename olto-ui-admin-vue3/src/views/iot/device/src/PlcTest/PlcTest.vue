<template>
  <div>
    <el-drawer v-bind="getBindValue">
      <div>
        <vxe-table
          ref="dragTableRef"
          border
          max-height="600"
          :data="data"
          stripe
          class="xtable-scrollbar"
          :column-config="{ resizable: true }"
        >
          <vxe-column title="设备名称">
            {{ props.deviceObj?.name }}
          </vxe-column>
          <vxe-column title="设备地址" field="address" />
          <vxe-column title="设备指令">
            <template #default="{ row }">
              <el-select v-model="row.cmd">
                <el-option
                  v-for="item in cmdOptions"
                  :key="item.value"
                  :value="item.value"
                  :label="item.label"
                />
              </el-select>
            </template>
          </vxe-column>
          <vxe-column title="值类型">
            <template #default="{ row }">
              <ElInput v-model="row['writeValue']">
                <template #prepend>
                  <ElSelect v-model="row['type']" style="width: 86px">
                    <el-option
                      v-for="item in TypeOptions"
                      :key="item.value"
                      :value="item.value"
                      :label="item.label"
                    />
                  </ElSelect>
                </template>
              </ElInput>
            </template>
          </vxe-column>
          <vxe-column title="起始地址" field="start">
            <template #default="{ row }">
              <el-input-number :min="0" v-model="row.start" />
            </template>
          </vxe-column>
          <vxe-column title="操作" width="100" show-overflow>
            <template #default="{ row }">
              <vxe-button content="发送" status="primary" @click="handleSend(row)" />
            </template>
          </vxe-column>
        </vxe-table>
        <div style="padding: 5px 0px">
          <vxe-button content="清除" status="primary" @click="() => (debugInfo.length = 0)" />
        </div>
        <div style="height: 400px; border: 1px solid #e3e3e3; overflow-y: auto">
          <template v-for="(debug, key) in debugInfo" :key="key">
            <div>
              <el-row class="u-debug-desc">
                <el-col span="8" style="text-align: left">
                  <span v-if="debug['status']" style="color: green"> [状态]&nbsp;成功 </span>
                  <span v-else style="color: #d4380d"> [状态]&nbsp;{{ debug['message'] }} </span>
                  &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                  <span> <label>值 =</label>&nbsp;{{ debug['data'] }} </span>
                </el-col>
                <el-col span="16" style="text-align: center">
                  <span style="color: #cc8500"> [时间]&nbsp;{{ debug['now'] }} </span>
                </el-col>
                <!-- <el-col span="8" style="text-align: center">
                  <span style="color: #cc8500">
                    [指令]&nbsp;{{ debug['data']['model']['cmd'] }}
                  </span>
                  &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                  <span>
                    [耗时]
                    <label>{{ debug['data']['respTime'] - debug['data']['reqTime'] }}ms</label>
                  </span>
                </el-col>
                <el-col span="8" style="text-align: right">
                  <span>
                    [设备编号]
                    <label>{{ debug['data']['model']['deviceSn'] }}</label>
                    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; [设备地址]
                    <label>{{ debug['data']['model']['childSn'] }}</label>
                  </span>
                </el-col> -->
              </el-row>
              <!-- <div class="u-debug-desc">
                <label>[{{ getTime(debug['data']['reqTime']) }}][请求报文]</label>
                <span class="u-debug-msg">{{ debug['data']['reqMsg'] }}</span>
              </div>
              <div class="u-debug-desc">
                <label>[{{ getTime(debug['data']['respTime']) }}][响应报文]</label>
                <span class="u-debug-msg">{{ debug['data']['respMsg'] }}</span>
              </div> -->
            </div>
          </template>
        </div>
      </div>
    </el-drawer>
  </div>
</template>
<script setup lang="ts">
import { propTypes } from '@/utils/propTypes'
// import { cloneDeep } from 'lodash-es'
import { VxeTableInstance } from 'vxe-table'
import { testDeviceApi } from '@/api/iot/device'

const props = defineProps({
  modelValue: propTypes.bool.def(false),
  title: propTypes.string.def('调试'),
  appendToBody: propTypes.bool.def(true),
  size: propTypes.string.def('100%'),
  direction: propTypes.string.def('rtl'),
  deviceObj: propTypes.object.def({
    id: null,
    name: ''
  })
})

const getBindValue = computed(() => {
  const attrs = useAttrs()
  const obj = { ...attrs, ...props }
  return obj
})

const data = ref([
  {
    address: null,
    cmd: null,
    writeValue: null,
    type: null,
    start: null
  }
])

const dragTableRef = ref<VxeTableInstance>()

const cmdOptions = reactive([
  { label: '读01', value: '01' },
  { label: '读02', value: '02' },
  { label: '读03', value: '03' },
  { label: '读04', value: '04' },
  { label: '写05', value: '05' },
  { label: '写06', value: '06' },
  { label: '写10', value: '10' },
  { label: '写0F', value: '0F' }
])

const TypeOptions = reactive([
  { label: 'short', value: '2' },
  { label: 'int', value: '3' },
  { label: 'long', value: '4' },
  { label: 'float', value: '5' },
  { label: 'double', value: '6' },
  { label: 'string', value: '9' },
  { label: 'boolean', value: '7' }
])

type debugInfo = {
  data: string
  now: Date
}
const debugInfo = ref<debugInfo[]>([])
const handleSend = async (formData) => {
  const data = Object.assign(formData, props.deviceObj)
  await testDeviceApi(data)
}

// 组织数据刷新
useEmitt({
  name: 'messageEmitt',
  callback: async (value) => {
    const { data, now, messageType } = value
    if (messageType === 'modbusTcp') {
      debugInfo.value.push({ data: data, now: now })
    }
  }
})
</script>
<style scoped></style>
