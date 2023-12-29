import request from '@/config/axios'

export interface IotDeviceVO {
  id: number
  name: string
  code: string // 设备编号
  protocol: number | null // 协议
  connectMode?: string
  host?: string
  port?: number
  serialCom?: string
  baudRate?: number
  dataBits?: number
  parityBits?: number
  stopBits?: number
}
export interface SelectDic {
  value: string
  label: string
}

export interface IotDevicePageReqVO extends PageParam {
  name?: string
  protocol?: number | null
}

export const testApi = async () => {
  return await request.get({ url: '/iot/device/test' })
}

// 分页获取设备列表
export const getIotDevicePageApi = async (params: IotDevicePageReqVO) => {
  return await request.get({ url: '/iot/device/page', params })
}

//获取设备
export const getIotDeviceApi = async (id: number) => {
  return await request.get({ url: '/iot/device/get?id=' + id })
}

// 创建设备
export const createIotDeviceApi = async (data: IotDeviceVO) => {
  return await request.post({ url: '/iot/device/create', data })
}

// 修改设备
export const updateIotDeviceApi = async (data: IotDeviceVO) => {
  return await request.put({ url: '/iot/device/update', data })
}

// 删除设备
export const deleteIotDeviceApi = async (id: number) => {
  return await request.delete({ url: '/iot/device/delete?id=' + id })
}

export const geIotDeviceListApi = async () => {
  return await request.get({ url: '/iot/device/get-simple-list' })
}

// 获取串口
export const getSerialPorts = async () => {
  return await request.get({ url: '/iot/device/get-serial-ports' })
}

export const runIotDeviceApi = async (id: number) => {
  return await request.post({ url: '/iot/device/run/' + { id } })
}

export const getDeviceProtocolTypeApi = async (id: number) => {
  return await request.get({ url: '/iot/device/get-protocol-type?id=' + id })
}

export const testDeviceApi = async (data) => {
  return await request.post({ url: '/iot/device/test', data })
}
