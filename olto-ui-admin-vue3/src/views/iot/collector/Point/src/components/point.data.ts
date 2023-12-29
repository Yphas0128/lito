import type { VxeCrudSchema } from '@/hooks/web/useVxeCrudSchemas'

// CrudSchema
const crudSchemas = reactive<VxeCrudSchema>({
  primaryKey: 'id',
  primaryType: 'radio',
  action: false,
  columns: [
    {
      title: '点位名称',
      field: 'name'
    },
    {
      title: '点位属性',
      field: 'property'
    }
  ]
})
export const { allSchemas } = useVxeCrudSchemas(crudSchemas)
