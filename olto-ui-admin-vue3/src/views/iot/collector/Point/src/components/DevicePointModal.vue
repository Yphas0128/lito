<template>
  <vxe-modal
    v-bind="getBindValue"
    destroy-on-close
    show-zoom
    resize
    transfer
    :class="[`${prefixCls}-container`]"
  >
    <div class="flex">
      <el-card class="w-1/3" :gutter="12" shadow="always">
        <template #header>
          <div class="card-header">
            <span>设备</span>
          </div>
        </template>
        <XTable @register="registerTable" @cell-click="handleCellClick" />
      </el-card>
      <el-card class="w-2/3 ml-2" :gutter="12" shadow="always">
        <template #header>
          <div class="card-header">
            <span>点位</span>
          </div>
        </template>
        <XTable @register="registerPointTable" v-if="!!pageParams.deviceId" />
      </el-card>
    </div>
    <template #footer>
      <div class="vxe-modal-header-set">
        <div class="header-btn-list">
          <div class="btn-wrap-set">
            <vxe-button class="btn" status="primary" content="确认" @click="btnConfirm()" />
          </div>
        </div>
      </div>
    </template>
  </vxe-modal>
</template>
<script setup lang="ts" name="LocationModal">
import { propTypes } from '@/utils/propTypes'
import { useDesign } from '@/hooks/web/useDesign'
import { VxeTableEvents } from 'vxe-table'
import { allSchemas } from './device.data'
import { allSchemas as allPointSchemas } from './point.data'
// 列表相关
import { getIotDevicePageApi } from '@/api/iot/device'
import { PointVO, getPointPageApi } from '@/api/iot/point'

const message = useMessage()
const { getPrefixCls } = useDesign()
const prefixCls = getPrefixCls('content-Location-modal-wrap')

const emit = defineEmits(['registerPoint'])

const props = defineProps({
  id: propTypes.string.def('model_1'),
  modelValue: propTypes.bool.def(false),
  fullscreen: propTypes.bool.def(false),
  loading: propTypes.bool.def(false),
  title: propTypes.string.def('选择点位'),
  width: propTypes.string.def('60%'),
  height: propTypes.string.def('70%'),
  minWidth: propTypes.string.def('460'),
  minHeight: propTypes.string.def('700'),
  showFooter: propTypes.bool.def(true)
})

const getBindValue = computed(() => {
  const attrs = useAttrs()
  const obj = { ...attrs, ...props }
  return obj
})

const pageParams = reactive({
  pageNo: 1,
  pageSize: 10,
  totalResult: 300,
  deviceId: null
})

//库位
const tableList = ref<PointVO[]>([])

const [registerTable] = useXTable({
  allSchemas: allSchemas,
  toolBar: true,
  height: 400,
  getListApi: getIotDevicePageApi
})

const [registerPointTable, { getRadioRecord }] = useXTable({
  allSchemas: allPointSchemas,
  toolBar: true,
  params: pageParams,
  height: 400,
  getListApi: getPointPageApi
})

const handleCellClick: VxeTableEvents.CellClick = async ({ row }) => {
  const { id } = row
  pageParams.deviceId = id
  await getPointPageTable()
}

const getPointPageTable = async () => {
  const res = await getPointPageApi(pageParams)
  const { list, total } = res
  tableList.value = list
  pageParams.totalResult = total
}

// const pageChange = async ({ currentPage, pageSize }) => {
//   pageParams.pageNo = currentPage
//   pageParams.pageSize = pageSize
//   await getPointPageTable()
// }

const btnConfirm = () => {
  const row = getRadioRecord()
  if (row === null) message.error('请先选中!')
  else {
    emit('registerPoint', {
      deviceId: unref(pageParams).deviceId,
      pointId: row['id'],
      pointName: row['name']
    })
  }
}
</script>
