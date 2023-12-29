// gys
import request from '@/config/axios'

export interface CollectorOptionVO {
  id: string
  name: string
}

export interface CollectorPageReqVO extends PageParam {
  name?: string
  code?: string
}

export interface CollectorVO {
  id?: number
  name: string
  collectAction: string
  description: string
}

export interface CollectorPointPageReqVO extends PageParam {
  collectorId?: number | null
  deviceId?: number
}

export interface CollectorPointVO {
  id: number
  collectorId: number
  deviceId: number
  deviceName: string
  pointId: number
  name: string
  frequency: number
  modbusType?: Number
  property: string
  fieldType?: number
  address?: string
  num?: number
  message?: string
}

export const getCollectorOptionApi = async () => {
  return await request.get({ url: '/iot/collector/option' })
}

// 查询
export const getCollectorPageApi = async (params: CollectorPageReqVO) => {
  return await request.get({ url: '/iot/collector/page', params })
}

export const getCollectororListApi = async () => {
  return await request.get({ url: '/iot/collector/get-simple-list' })
}

//　新增客户
export const createCollectorApi = async (data: CollectorVO) => {
  return request.post({ url: '/iot/collector/create', data })
}

export const updateCollectorApi = async (data: CollectorVO) => {
  return await request.put({ url: '/iot/collector/update', data })
}

//
export const getCollectorApi = async (id: number) => {
  return await request.get({ url: '/iot/collector/get?id=' + id })
}

//删除
export const deleteCollectorApi = async (id: number) => {
  return await request.delete({ url: '/iot/collector/delete?id=' + id })
}

// export const getCollectorProtocolTypeApi = async (id: number) => {
//   return await request.get({
//     url: '/iot/collector/get-protocol-type?id=' + id
//   })
// }

/// point
export const getCollectorPointPageApi = async (params: CollectorPointPageReqVO) => {
  return await request.get({ url: '/iot/collector/page-point', params })
}

export const createCollectorPointApi = async (data) => {
  return await request.post({ url: '/iot/collector/create-point', data })
}

export const deleteCollectorPointApi = async (id: number) => {
  return await request.delete({ url: '/iot/collector/delete-point?id=' + id })
}

export const runCollectorApi = async (id: number) => {
  return await request.post({ url: '/iot/collector/run/' + id })
}

export const stopCollectorApi = async (id: number) => {
  return await request.post({ url: '/iot/collector/consume/' + id })
}
