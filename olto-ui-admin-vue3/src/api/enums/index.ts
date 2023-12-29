import request from '@/config/axios'

export const getEnumsByType = async (enumName: string) => {
  return await request.get({ url: '/enums/selectEnumsByType/' + enumName })
}

export const getEnumListByType = async (enumName: string) => {
  return await request.get({ url: '/enums/getByType/' + enumName })
}
