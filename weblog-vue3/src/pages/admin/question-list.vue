<template>
    <div>
        <!-- 表头分页查询条件 -->
        <el-card shadow="never" class="mb-5">
            <div class="flex items-center">
                <el-text>问题标题</el-text>
                <div class="ml-3 w-52 mr-5"><el-input v-model="searchQuestionTitle" placeholder="请输入（模糊查询）" /></div>

                <el-text>体质分类</el-text>
                <div class="ml-3 w-30 mr-5">
                    <el-select v-model="searchConstitutionCategoryId" clearable placeholder="请选择体质分类">
                        <el-option 
                            v-for="item in constitutionCategoryOptions" 
                            :key="item.value" 
                            :label="item.label" 
                            :value="item.value" 
                        />
                    </el-select>
                </div>

                <el-text>创建日期</el-text>
                <div class="ml-3 w-30 mr-5">
                    <el-date-picker v-model="pickDate" type="daterange" range-separator="至" start-placeholder="开始时间"
                        end-placeholder="结束时间" :shortcuts="shortcuts" size="default" @change="datepickerChange" />
                </div>

                <el-button type="primary" class="ml-3" :icon="Search" @click="getTableData">查询</el-button>
                <el-button class="ml-3" :icon="RefreshRight" @click="reset">重置</el-button>
            </div>
        </el-card>
        
        <!--表格-->
        <el-card shadow="never">
            <!-- 新增按钮 -->
            <div class="mb-5">
                <el-button type="primary" @click="showQuestionPublishEditor">
                    <el-icon class="mr-1">
                        <EditPen />
                    </el-icon>
                    新增问题
                </el-button>
            </div>
            
            <!-- 发布问题 -->
            <el-dialog v-model="isQuestionPublishEditorShow" :show-close="false" width="50%">
                <template #header="{ close, titleId, titleClass }">
                    <div class="flex h-10 bg-white">
                        <h4 class="font-bold">发布问卷问题</h4>
                        <div class="ml-auto flex">
                            <el-button @click="isQuestionPublishEditorShow = false">取消</el-button>
                            <el-button type="primary" @click="publishQuestionSubmit">
                                <el-icon class="mr-1">
                                    <Promotion />
                                </el-icon>
                                发布
                            </el-button>
                        </div>
                    </div>
                </template>
                
                <el-form :model="form" ref="publishQuestionFormRef" label-position="top" size="large" :rules="rules">
                    <el-form-item label="问题标题" prop="title">
                        <el-input v-model="form.title" autocomplete="off" size="large" maxlength="40" show-word-limit
                            clearable placeholder="请输入问题标题" />
                    </el-form-item>
                    
                    <el-form-item label="问题类型" prop="questionType">
                        <el-select v-model="form.questionType" clearable placeholder="---请选择问题类型---" size="large">
                            <el-option 
                                v-for="item in questionTypeOptions" 
                                :key="item.value" 
                                :label="item.label" 
                                :value="item.value" 
                            />
                        </el-select>
                    </el-form-item>
                    
                    <el-form-item label="体质分类" prop="constitutionCategoryId">
                        <el-select v-model="form.constitutionCategoryId" clearable placeholder="---请选择体质分类---" size="large">
                            <el-option 
                                v-for="item in constitutionCategoryOptions" 
                                :key="item.value" 
                                :label="item.label" 
                                :value="item.value" 
                            />
                        </el-select>
                    </el-form-item>
                    
                    <el-form-item label="问题描述" prop="content">
                        <el-input 
                            v-model="form.content" 
                            type="textarea" 
                            :rows="3" 
                            placeholder="请输入问题描述（选填）"
                            maxlength="200"
                            show-word-limit
                        />
                    </el-form-item>
                                    
                    <el-form-item label="排序序号" prop="sortOrder">
                        <el-input-number v-model="form.sortOrder" :min="0" :max="999" />
                        <span class="ml-2 text-sm text-gray-500">数值越小越靠前</span>
                    </el-form-item>
                </el-form>
            </el-dialog>
            
            <!-- 编辑问题 -->
            <el-dialog v-model="isQuestionUpdateEditorShow" :show-close="false" width="50%"
                :close-on-press-escape="false">
                <template #header="{ close, titleId, titleClass }">
                    <div class="flex h-10 bg-white">
                        <h4 class="font-bold">编辑问卷问题</h4>
                        <div class="ml-auto flex">
                            <el-button @click="isQuestionUpdateEditorShow = false">取消</el-button>
                            <el-button type="primary" @click="updateSubmit">
                                <el-icon class="mr-1">
                                    <Promotion />
                                </el-icon>
                                保存
                            </el-button>
                        </div>
                    </div>
                </template>
                
                <el-form :model="updateForm" ref="updateQuestionFormRef" label-position="top" size="large"
                    :rules="rules">
                    <el-form-item label="问题标题" prop="title">
                        <el-input v-model="updateForm.title" autocomplete="off" size="large" maxlength="40"
                            show-word-limit clearable placeholder="请输入问题标题" />
                    </el-form-item>
                    
                    <el-form-item label="问题类型" prop="questionType">
                        <el-select v-model="updateForm.questionType" clearable placeholder="---请选择问题类型---" size="large">
                            <el-option 
                                v-for="item in questionTypeOptions" 
                                :key="item.value" 
                                :label="item.label" 
                                :value="item.value" 
                            />
                        </el-select>
                    </el-form-item>
                    
                    <el-form-item label="体质分类" prop="constitutionCategoryId">
                        <el-select v-model="updateForm.constitutionCategoryId" clearable placeholder="---请选择体质分类---" size="large">
                            <el-option 
                                v-for="item in constitutionCategoryOptions" 
                                :key="item.value" 
                                :label="item.label" 
                                :value="item.value" 
                            />
                        </el-select>
                    </el-form-item>
                    
                    <el-form-item label="问题描述" prop="content">
                        <el-input 
                            v-model="updateForm.content" 
                            type="textarea" 
                            :rows="3" 
                            placeholder="请输入问题描述（选填）"
                            maxlength="200"
                            show-word-limit
                        />
                    </el-form-item>                    
                    
                    <el-form-item label="排序序号" prop="sortOrder">
                        <el-input-number v-model="updateForm.sortOrder" :min="0" :max="999" />
                        <span class="ml-2 text-sm text-gray-500">数值越小越靠前</span>
                    </el-form-item>
                </el-form>
            </el-dialog>

            <!-- 分页列表 -->
            <el-table :data="tableData" border stripe style="width: 100%" v-loading="tableLoading">
                <el-table-column prop="id" label="ID" width="50" />
                <el-table-column prop="title" label="标题" width="180" />
                <el-table-column prop="questionType" label="问题类型" width="120">
                    <template #default="scope">
                        <el-tag :type="getQuestionTypeTagType(scope.row.questionType)">
                            {{ scope.row.questionType }}
                        </el-tag>
                    </template>
                </el-table-column>
                <el-table-column prop="constitutionCategoryName" label="体质分类" width="120">
                    <template #default="scope">
                        <!-- 这里显示后端传过来的体质分类字符串，如"平和质" -->
                        <el-tag :type="getConstitutionCategoryTagType(scope.row.constitutionCategoryName)">
                            {{ scope.row.constitutionCategoryName }}
                        </el-tag>
                    </template>
                </el-table-column>
                <el-table-column prop="sortOrder" label="排序" width="80" />
                <el-table-column prop="createTime" label="创建时间" width="180" />
                <el-table-column label="操作" width="180">
                    <template #default="scope">
                        <el-button size="small" @click="showQuestionUpdateEditor(scope.row)">
                            <el-icon class="mr-1">
                                <Edit />
                            </el-icon>
                            编辑
                        </el-button>
                        <el-button type="danger" size="small" @click="deleteQuestionSubmit(scope.row)">
                            <el-icon class="mr-1">
                                <Delete />
                            </el-icon>
                            删除
                        </el-button>
                    </template>
                </el-table-column>
            </el-table>
            
            <!-- 分页 -->
            <div class="mt-10 flex justify-center">
                <el-pagination v-model:current-page="current" v-model:page-size="size" :page-sizes="[10, 20, 50]"
                    :small="false" :background="true" layout="total, sizes, prev, pager, next, jumper" :total="total"
                    @size-change="handleSizeChange" @current-change="getTableData" />
            </div>
        </el-card>
    </div>
</template>

<script setup>
import { Search, RefreshRight, EditPen, Promotion, Delete, Edit } from '@element-plus/icons-vue';
import { ref, reactive } from 'vue'
import moment from 'moment';
import { deleteQuestion, getQuestionPageList, publishQuestion, getQuestionDetail, updateQuestion } from '@/api/admin/question.js';
import { showMessage, showModel } from '@/composables/util';

// 问题类型映射
const questionTypeOptions = [
    { label: '单选题', value: 1 },
    { label: '多选题', value: 2 },
    { label: '判断题', value: 3 },
    { label: '简答题', value: 4 },
    { label: '评分题', value: 5 }
]

// 体质分类选项
const constitutionCategoryOptions = [
    { label: '平和质', value: 1 },
    { label: '气虚质', value: 2 },
    { label: '阳虚质', value: 3 },
    { label: '阴虚质', value: 4 },
    { label: '痰湿质', value: 5 },
    { label: '湿热质', value: 6 },
    { label: '血瘀质', value: 7 },
    { label: '气郁质', value: 8 },
    { label: '特禀质', value: 9 },
    { label: '通用问题', value: 0 }
]

// 字符串到数字的映射（用于转换后端传来的字符串）
const CONSTITUTION_CATEGORY_STRING_TO_NUMBER_MAP = {
    '平和质': 1,
    '气虚质': 2,
    '阳虚质': 3,
    '阴虚质': 4,
    '痰湿质': 5,
    '湿热质': 6,
    '血瘀质': 7,
    '气郁质': 8,
    '特禀质': 9,
    '通用问题': 0
}

// 体质分类标签样式（根据字符串判断）
const getConstitutionCategoryTagType = (constitutionCategoryName) => {
    // 判断字符串内容
    const types = {
        '平和质': 'success',
        '气虚质': 'warning',
        '阳虚质': '',
        '阴虚质': 'primary',
        '痰湿质': '',
        '湿热质': 'danger',
        '血瘀质': 'danger',
        '气郁质': '',
        '特禀质': 'warning',
        '通用问题': 'info'
    }
    return types[constitutionCategoryName] || 'default'
}

// 问题类型标签样式
const getQuestionTypeTagType = (questionType) => {
    if (typeof questionType === 'number') {
        const types = {
            1: 'primary',    // 单选题
            2: 'success',    // 多选题
            3: 'warning',    // 判断题
            4: 'info',       // 简答题
            5: 'danger'      // 评分题
        }
        return types[questionType] || 'default'
    }
    
    const types = {
        '单选题': 'primary',
        '多选题': 'success',
        '判断题': 'warning',
        '简答题': 'info',
        '评分题': 'danger'
    }
    return types[questionType] || 'default'
}

// 是否显示问题发布对话框
const isQuestionPublishEditorShow = ref(false)

// 搜索条件
const searchQuestionTitle = ref('')
const searchConstitutionCategoryId = ref(null)

// 表单对象 - 使用 constitutionCategoryId 字段
const form = reactive({
    id: null,
    title: '',
    questionType: null,
    constitutionCategoryId: null, // 使用 constitutionCategoryId
    content: '',
    sortOrder: 0
})

// 表单验证规则
const rules = {
    title: [
        { required: true, message: '请输入问题标题', trigger: 'blur' },
        { min: 1, max: 40, message: '标题长度在1-40个字符之间', trigger: 'blur' }
    ],
    questionType: [
        { required: true, message: '请选择问题类型', trigger: 'change' }
    ],
    constitutionCategoryId: [
        { required: true, message: '请选择体质分类', trigger: 'change' }
    ]
}

const shortcuts = [
    {
        text: '最近一周',
        value: () => {
            const end = new Date()
            const start = new Date()
            start.setTime(start.getTime() - 3600 * 1000 * 24 * 7)
            return [start, end]
        },
    },
    {
        text: '最近一个月',
        value: () => {
            const end = new Date()
            const start = new Date()
            start.setTime(start.getTime() - 3600 * 1000 * 24 * 30)
            return [start, end]
        },
    },
    {
        text: '最近三个月',
        value: () => {
            const end = new Date()
            const start = new Date()
            start.setTime(start.getTime() - 3600 * 1000 * 24 * 90)
            return [start, end]
        },
    },
]

const publishQuestionFormRef = ref(null)

// 显示发布问题编辑器
const showQuestionPublishEditor = () => {
    resetForm()
    isQuestionPublishEditorShow.value = true
}

// 重置表单
const resetForm = () => {
    form.id = null
    form.title = ''
    form.questionType = null
    form.constitutionCategoryId = null
    form.content = ''
    form.sortOrder = 0
}

// 发布问题提交事件
const publishQuestionSubmit = () => {
    publishQuestionFormRef.value.validate((valid) => {
        if (valid) {
            // 准备提交数据 - 确保所有数字字段都正确转换
            const submitData = {
                ...form,
                questionType: Number(form.questionType),
                constitutionCategoryId: Number(form.constitutionCategoryId), // 使用正确的字段名
                sortOrder: Number(form.sortOrder)
            }
            
            console.log('发布提交数据:', JSON.stringify(submitData))
            
            publishQuestion(submitData).then((res) => {
                if (res.success) {
                    showMessage('发布成功')
                    isQuestionPublishEditorShow.value = false
                    resetForm()
                    getTableData()
                } else {
                    let message = res.message
                    showMessage(message, 'error')
                    return
                }
            }).catch(error => {
                showMessage('发布失败：' + error.message, 'error')
            })
        } else {
            console.log('表单验证失败')
        }
    })
}

// 日期
const pickDate = ref('')
// 查询条件：开始结束时间
const startDate = ref(null)
const endDate = ref(null)

// 重置查询条件
const reset = () => {
    searchQuestionTitle.value = ''
    searchConstitutionCategoryId.value = null
    pickDate.value = ''
    startDate.value = null
    endDate.value = null
    getTableData()
}

// 监听日期选择器变化
const datepickerChange = (e) => {
    if (e) {
        startDate.value = moment(e[0]).format('YYYY-MM-DD')
        endDate.value = moment(e[1]).format('YYYY-MM-DD')
    } else {
        startDate.value = null
        endDate.value = null
    }
}

// 表格数据
const tableData = ref([])
// 当前页码，给了一个默认值1
const current = ref(1)
// 总数据量，给了一个默认值0
const total = ref(0)
// 每页显示的数据量， 给了一个默认值10
const size = ref(10)
// 表格加载Loading
const tableLoading = ref(false)

// 获取问题分页数据
function getTableData() {
    tableLoading.value = true
    // 搜索条件中的constitutionCategoryId是数字，直接传给后端
    getQuestionPageList({
        current: current.value,
        size: size.value,
        startDate: startDate.value,
        endDate: endDate.value,
        title: searchQuestionTitle.value,
        constitutionCategoryId: searchConstitutionCategoryId.value // 使用正确的字段名
    }).then((res) => {
        if (res.success) {
            tableData.value = res.data.records || res.data
            current.value = res.current || res.data.current
            size.value = res.size || res.data.size
            total.value = res.total || res.data.total
            
            // 打印第一个数据，查看数据类型
            if (tableData.value.length > 0) {
                console.log('从后端获取的问题数据:', tableData.value[0])
            }
        }
    }).finally(() => tableLoading.value = false)
}

getTableData()

// 每页展示数量变更事件
const handleSizeChange = (chooseSize) => {
    size.value = chooseSize
    getTableData()
}

// 删除问题提交事件
const deleteQuestionSubmit = (row) => {
    showModel('是否确定要删除该问题? ').then(() => {
        deleteQuestion(row.id).then((res) => {
            if (res.success) {
                showMessage('删除成功')
                getTableData()
            } else {
                let message = res.message
                showMessage(message, 'error')
            }
        })
    }).catch(() => {
        console.log('取消了')
    })
}

// 编辑问题表单引用
const updateQuestionFormRef = ref(null)

// 是否显示问题编辑对话框
const isQuestionUpdateEditorShow = ref(false)

// 编辑问题表单对象 - 使用 constitutionCategoryId 字段
const updateForm = reactive({
    id: null,
    title: '',
    questionType: null,
    constitutionCategoryId: null, // 使用 constitutionCategoryId
    content: '',
    sortOrder: 0
})

// 编辑问题按钮点击事件
const showQuestionUpdateEditor = (row) => {
    isQuestionUpdateEditorShow.value = true
    let questionId = row.id
    getQuestionDetail(questionId).then((res) => {
        if (res.success) {
            console.log('获取到的详情数据:', res.data)
            
            // 立即设置ID
            updateForm.id = row.id
            
            // 设置其他表单数据
            updateForm.title = res.data.title || ''
            updateForm.content = res.data.content || ''
            updateForm.sortOrder = Number(res.data.sortOrder) || 0
            
            // 处理questionType字段
            let questionTypeValue = res.data.questionType
            if (typeof questionTypeValue === 'string') {
                const questionTypeStringToNumberMap = {
                    '单选题': 1,
                    '多选题': 2,
                    '判断题': 3,
                    '简答题': 4,
                    '评分题': 5
                }
                updateForm.questionType = questionTypeStringToNumberMap[questionTypeValue] || null
            } else {
                updateForm.questionType = Number(questionTypeValue)
            }
            
            // 处理constitutionCategoryId字段 - 关键转换
            // 注意：后端可能返回 constitutionCategoryName 字符串，但我们需要的是 constitutionCategoryId 数字
            let constitutionCategoryName = res.data.constitutionCategoryName
            console.log('constitutionCategoryName:', constitutionCategoryName)
            
            if (constitutionCategoryName && typeof constitutionCategoryName === 'string') {
                // 根据体质分类名称转换为对应的数字ID
                updateForm.constitutionCategoryId = CONSTITUTION_CATEGORY_STRING_TO_NUMBER_MAP[constitutionCategoryName] || null
            } else if (res.data.constitutionCategoryId !== undefined) {
                // 如果已经有constitutionCategoryId字段，直接使用
                updateForm.constitutionCategoryId = Number(res.data.constitutionCategoryId)
            } else {
                // 如果都没有，设置为null
                updateForm.constitutionCategoryId = null
            }
            
            console.log('转换后form数据:', updateForm)
            console.log('constitutionCategoryId:', updateForm.constitutionCategoryId)
        }
    }).catch(error => {
        console.error('获取问题详情失败:', error)
    })
}

// 保存问题
const updateSubmit = () => {
    updateQuestionFormRef.value.validate((valid) => {
        if (!valid) {
            console.log('表单验证失败')
            return false
        }
        
        // 检查ID是否存在
        if (!updateForm.id) {
            showMessage('问题ID不能为空', 'error')
            return false
        }
        
        // 检查constitutionCategoryId是否存在
        if (updateForm.constitutionCategoryId === null || updateForm.constitutionCategoryId === undefined) {
            showMessage('体质分类不能为空', 'error')
            return false
        }

        // 准备提交数据 - 确保所有字段都是正确的类型
        const submitData = {
            id: Number(updateForm.id),
            title: updateForm.title,
            questionType: Number(updateForm.questionType),
            constitutionCategoryId: Number(updateForm.constitutionCategoryId), // 使用正确的字段名
            content: updateForm.content || '',
            sortOrder: Number(updateForm.sortOrder) || 0
        }

        console.log('更新提交数据:', JSON.stringify(submitData))

        updateQuestion(submitData).then((res) => {
            if (!res.success) {
                let message = res.message
                showMessage(message, 'error')
                return
            }

            showMessage('保存成功')
            isQuestionUpdateEditorShow.value = false
            getTableData()
        }).catch(error => {
            console.error('更新失败:', error)
            showMessage('更新失败: ' + error.message, 'error')
        })
    })
}
</script>

<style scoped>
.options-container {
    max-width: 800px;
}

.option-item {
    background-color: #f9f9f9;
}
</style>