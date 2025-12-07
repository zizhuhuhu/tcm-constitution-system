<script setup>
import { RouterView } from 'vue-router';
import zhCn from 'element-plus/dist/locale/zh-cn.mjs';
const locale = zhCn
</script>

<template>
  <!--设置语言为中文-->
  <el-config-provider :locale="locale">
 <RouterView></RouterView>
  </el-config-provider>
 
</template>
<style scoped>


</style>
