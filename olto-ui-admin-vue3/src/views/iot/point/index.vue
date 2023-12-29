<template>
  <div class="flex">
    <el-card class="w-1/4 user" :gutter="12" shadow="always">
      <template #header>
        <div class="card-header">
          <span>采集设备</span>
        </div>
      </template>
      <!-- 列表 -->
      <XTable @register="registerTable" @cell-click="cellClickEvent" />
    </el-card>
    <el-card class="w-3/4 user ml-3" :gutter="12" shadow="always">
      <template #header>
        <div class="card-header">
          <span>设备点位</span>
        </div>
      </template>
      <point-item v-if="data || data === undefined" :data="data" />
      <el-empty style="margin-top: 20%" v-else />
    </el-card>
  </div>
</template>
<script setup lang="ts" name="IotPoint">
import { VxeTableEvents } from 'vxe-table'
import { allSchemas } from './device.data'
import { getIotDevicePageApi } from '@/api/iot/device'
import { PointItem } from './PointItem'

const data = ref()
// table
const [registerTable] = useXTable({
  allSchemas: allSchemas,
  toolBar: true,
  getListApi: getIotDevicePageApi
})

const cellClickEvent: VxeTableEvents.CellClick = async ({ row }) => {
  const { id } = row
  setTimeout(() => {
    data.value = {
      deviceId: id
    }
  }, 100)
  // await fixItemGetList()
}
</script>
