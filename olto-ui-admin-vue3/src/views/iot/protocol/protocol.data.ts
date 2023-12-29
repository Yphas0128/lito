import type { VxeCrudSchema } from '@/hooks/web/useVxeCrudSchemas'

export const rules = reactive({
  name: [required],
  protocolType: [required],
  componentName: [required]
})

// CrudSchema
const crudSchemas = reactive<VxeCrudSchema>({
  primaryKey: 'id',
  primaryType: 'seq',
  action: true,
  columns: [
    {
      title: '协议名称',
      field: 'name',
      isSearch: true
    },
    {
      title: '协议类型',
      field: 'protocolType',
      dictType: DICT_TYPE.IOT_PROTOCOL_TYPE,
      dictClass: 'number',
      isSearch: true
    },
    {
      title: '组件名称',
      field: 'componentName'
    }
  ]
})
export const { allSchemas } = useVxeCrudSchemas(crudSchemas)
