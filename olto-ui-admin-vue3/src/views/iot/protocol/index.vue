<template>
  <ContentWrap>
    <!-- 列表 -->
    <XTable @register="registerTable">
      <!-- 操作：新增 -->
      <template #toolbar_buttons>
        <XButton
          type="primary"
          preIcon="ep:zoom-in"
          :title="t('action.add')"
          @click="handleCreate()"
        />
      </template>
      <template #actionbtns_default="{ row }">
        <!-- 操作：删除 -->
        <XTextButton preIcon="ep:edit" :title="t('action.edit')" @click="handleUpdate(row.id)" />
        <XTextButton preIcon="ep:delete" :title="t('action.del')" @click="deleteData(row.id)" />
        <!-- 操作：详情 -->
      </template>
    </XTable>
  </ContentWrap>
  <XModal v-model="dialogVisible" :title="dialogTitle">
    <Form
      v-if="['create', 'update'].includes(actionType)"
      :schema="allSchemas.formSchema"
      :rules="rules"
      ref="formRef"
    />

    <!-- 操作按钮 -->
    <template #footer>
      <!-- 按钮：保存 -->
      <XButton
        v-if="['create', 'update'].includes(actionType)"
        type="primary"
        :title="t('action.save')"
        :loading="loading"
        @click="submitForm()"
      />
      <!-- 按钮：关闭 -->
      <XButton :loading="loading" :title="t('dialog.close')" @click="dialogVisible = false" />
    </template>
  </XModal>
</template>
<script setup lang="ts" name="IotProtocol">
import { rules, allSchemas } from './protocol.data'
// 列表相关
import * as protocolApi from '@/api/iot/protocol'
import type { FormExpose } from '@/components/Form'

const { t } = useI18n() // 国际化
const message = useMessage() // 消息弹窗

// 列表相关的变量
const [registerTable, { reload, deleteData }] = useXTable({
  allSchemas: allSchemas,
  getListApi: protocolApi.getProtocolPageApi,
  deleteApi: protocolApi.deleteProtocolApi
})

// ========== CRUD 相关 ==========
const loading = ref(false) // 遮罩层
const actionType = ref('') // 操作按钮的类型
const dialogTitle = ref('edit') // 弹出层标题
const dialogVisible = ref(false) // 是否显示弹出层
const formRef = ref<FormExpose>() // 表单 Ref
// const detailData = ref() // 详情 Ref

// 设置标题
const setDialogTile = (type: string) => {
  dialogTitle.value = t('action.' + type)
  actionType.value = type
  dialogVisible.value = true
}

// 新增操作
const handleCreate = () => {
  setDialogTile('create')
}

const handleUpdate = async (rowId: number) => {
  setDialogTile('update')
  await nextTick()
  // 设置数据
  const res = await protocolApi.getProtocolApi(rowId)
  unref(formRef)?.setValues(res)
}
// 保存

const submitForm = async () => {
  const elForm = unref(formRef)?.getElFormRef()
  if (!elForm) return
  elForm.validate(async (valid) => {
    if (valid) {
      loading.value = true
      try {
        const data = unref(formRef)?.formModel as protocolApi.IotProtocolVO
        if (actionType.value === 'create') {
          await protocolApi.createProtocolApi(data)
          message.success(t('common.createSuccess'))
        } else {
          await protocolApi.updateProtocolApi(data)
          message.success(t('common.updateSuccess'))
        }
        dialogVisible.value = false
      } finally {
        await reload()
        loading.value = false
      }
    }
  })
}
</script>
