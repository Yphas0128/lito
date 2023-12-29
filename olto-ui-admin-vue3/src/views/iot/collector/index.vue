<template>
  <ContentWrap>
    <div class="a-container">
      <div class="left">
        <Tree @change="changeTree" />
      </div>
      <div class="right" v-loading="loading">
        <Point v-if="data || data === undefined" :data="data" />
        <el-empty style="margin-top: 20%" v-else />
      </div>
    </div>
  </ContentWrap>
</template>
<script setup lang="ts" name="IotCollector">
import { Tree } from './Tree'
import { Point } from './Point'
const data = ref()
const loading = ref(false)
const changeTree = (id) => {
  loading.value = true
  setTimeout(() => {
    data.value = {
      collectorId: id
    }
    loading.value = false
  }, 100)
}
</script>
<style lang="scss" scoped>
.a-container {
  margin: 0px;
  background: white;
  padding: 14px;
  display: flex;
  min-height: calc(100vh - 180px);
  width: 100%;
  .left {
    width: 370px;
    border-right: 1px #eeeeee solid;
    margin: 10px 10px 0 0;
  }
  .right {
    flex: 1;
    padding: 10px;
  }
}
</style>
