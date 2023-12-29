import request from '@/config/axios'

export interface IotProtocolVO {
  id: number
  name: string
  protocolType: number
  componentName: string
}

export interface ProtocolPageReqVO extends PageParam {
  name?: string
  protocolType?: number
}

// 查询列表
export const getProtocolPageApi = async (params: ProtocolPageReqVO) => {
  return await request.get({ url: '/iot/protocol/page', params })
}

//　新增
export const createProtocolApi = async (data: IotProtocolVO) => {
  return request.post({ url: '/iot/protocol/create', data })
}

export const updateProtocolApi = (data: IotProtocolVO) => {
  return request.put({ url: '/iot/protocol/update', data })
}

//1
export const getProtocolApi = async (id: number) => {
  return request.get({ url: '/iot/protocol/get?id=' + id })
}

//删除客户
export const deleteProtocolApi = (id: number) => {
  return request.delete({ url: '/iot/protocol/delete?id=' + id })
}

export const getProtocolListApi = async () => {
  return await request.get({ url: '/iot/protocol/get-simple-list' })
}
