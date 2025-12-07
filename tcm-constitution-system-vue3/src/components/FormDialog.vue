<template>
    <!--添加分类对话框模板-->
    <el-dialog v-model="dialogVisible" title="添加文章分类" width="40%" :draggable="true" :close-on-click-modal="false"
        :close-on-press-escape="false">
        <!--插槽-->
        <slot></slot>
        <template #footer>
            <span class="dialog-footer">
                <el-button @click="dialogVisible = false">取消</el-button>
                <el-button type="primary" @click="submit" :loading="btnloading">
                {{ confirmText }}
                </el-button>
            </span>
            
        </template>
    </el-dialog>
</template>

<script setup>
import { ref } from 'vue'

const dialogVisible = ref(false)

// 打开
const open = () => dialogVisible.value = true
// 关闭
const close = () => dialogVisible.value = false
//确定按钮加载
const btnloading = ref(false)
//显示loading
const showBtnLoading = () => btnloading.value = true
//隐藏loading
const closeBtnLoading = () => btnloading.value = false
//对外暴露属性
const props = defineProps({
    title: String, //字段类型
    width: {
        type: String,
        default: '40%' //默认值
    },
    destroyOnclose: {
        type: Boolean,
        default: false
    },
    confirmText: {
        type: String,
        default: '提交'
    }
})
//对外暴露一个submit 方法
const emit = defineEmits(['submit'])
const submit = () => emit('submit')
// 暴露给父组件
defineExpose({
    open,
    close,
    showBtnLoading,
    closeBtnLoading
})

</script>