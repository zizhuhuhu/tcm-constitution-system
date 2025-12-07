<template>
    <div v-if="categories && categories.length > 0" class="w-full p-5 mb-3 bg-white border border-gray-200 rounded-lg dark:bg-gray-800 dark:border-gray-700">
        <!-- 分类标题 -->
        <h2 class="mb-2 font-bold text-gray-900 uppercase dark:text-white">分类</h2>
        <!-- 分类列表 -->
        <div 
            class="text-sm font-medium text-gray-600 bg-white rounded-lg dark:bg-gray-700 dark:border-gray-600 dark:text-white">
            <a href="#" v-for="(category, index) in categories" :key="index"
                class="flex items-center w-full px-4 py-2 rounded-lg border-gray-200 cursor-pointer hover:bg-gray-100 hover:text-blue-700 dark:border-gray-600 dark:hover:bg-gray-600 dark:hover:text-white dark:focus:ring-gray-500 dark:focus:text-white">
                <svg class="w-3.5 h-3.5 mr-1.5 dark:text-white" aria-hidden="true" xmlns="http://www.w3.org/2000/svg"
                    fill="none" viewBox="0 0 21 18">
                    <path stroke="currentColor" stroke-linecap="round" stroke-linejoin="round" stroke-width="2"
                        d="M2.539 17h12.476l4-9H5m-2.461 9a1 1 0 0 1-.914-1.406L5 8m-2.461 9H2a1 1 0 0 1-1-1V2a1 1 0 0 1 1-1h5.443a1 1 0 0 1 .8.4l2.7 3.6H16a1 1 0 0 1 1 1v2H5" />
                </svg>
                {{ category.name }}
            </a>
        </div>
    </div>
</template>

<script setup>
import { getCategoryList } from '@/api/frontend/category'
import { ref } from 'vue'

// 所有分类
const categories = ref([])
getCategoryList().then((res) => {
    if (res.success) {
        categories.value = res.data
    }
})
</script>

