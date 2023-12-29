<template>
  <el-badge :value="props.text" class="" />
</template>

<script setup lang="ts">
const props = defineProps({
  text: {
    type: String
  }
})
</script>
<style>
.item {
  margin-top: 10px;
  margin-right: 40px;
}
</style>
