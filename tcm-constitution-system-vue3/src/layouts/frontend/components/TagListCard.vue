<template>
    <div v-if="tags && tags.length > 0" class="w-full p-5 mb-3 bg-white border border-gray-200 rounded-lg dark:bg-gray-800 dark:border-gray-700">
        <!-- 标签标题 -->
        <h2 class="mb-2 font-bold text-gray-900 uppercase dark:text-white">标签</h2>
        <!-- 标签列表 -->
        <span v-for="(tag, index) in tags" :key="index"
            class="inline-block mb-1 cursor-pointer bg-green-100 text-green-800 text-xs font-medium mr-2 px-2.5 py-0.5 rounded hover:bg-green-200 hover:text-green-900 dark:bg-green-900 dark:text-green-300">
            {{ tag.name }}
        </span>
    </div>
</template>

<script setup>
import { getTagList } from '@/api/frontend/tag'
import { ref } from 'vue'

// 所有标签
const tags = ref([])
getTagList().then((res) => {
    if (res.success) {
        tags.value = res.data
    }
})
</script>

