import type { VxeCrudSchema } from '@/hooks/web/useVxeCrudSchemas'

// CrudSchema
const crudSchemas = reactive<VxeCrudSchema>({
  primaryKey: 'id',
  primaryType: 'seq',
  action: false,
  columns: [
    {
      title: '设备编号',
      field: 'code'
    },
    {
      title: '设备名称',
      field: 'name'
    }
  ]
})
export const { allSchemas } = useVxeCrudSchemas(crudSchemas)
