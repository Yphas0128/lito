// gys
import request from '@/config/axios'

export interface PointOptionVO {
  collectorId: string
  name: string
}

export interface PointPageReqVO extends PageParam {
  deviceId: number | null
}

export interface PointVO {
  id?: number
  name: string
  deviceId: number | null
  frequency: number | null
  modbusType?: number | null
  property: string
  fieldType?: number | null
  address?: string
  num?: number | null
  message?: string
  status: number
}

// 查询
export const getPointPageApi = async (params: PointPageReqVO) => {
  return await request.get({ url: '/iot/point/page', params })
}

export const getPointorListApi = async () => {
  return await request.get({ url: '/iot/point/get-simple-list' })
}

// 新增
export const createPointApi = async (data: PointVO) => {
  return await request.post({ url: '/iot/point/create', data })
}

export const updatePointApi = async (data: PointVO) => {
  return await request.put({ url: '/iot/point/update', data })
}

//
export const getPointApi = async (id: number) => {
  return await request.get({ url: '/iot/point/get?id=' + id })
}

// 删除
export const deletePointApi = async (id: number) => {
  return await request.delete({ url: '/iot/point/delete?id=' + id })
}

// 写入
export const writePointApi = async (data) => {
  return await request.post({ url: '/iot/point/write', data })
}
