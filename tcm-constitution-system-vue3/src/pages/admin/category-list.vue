<template>
    <div>
        <!-- 表头分页查询条件， shadow="never" 指定 card 卡片组件没有阴影 -->
        <el-card shadow="never" class="mb-5">
            <!-- flex 布局，内容垂直居中 -->
            <div class="flex items-center">
                <el-text>分类名称</el-text>
                <div class="ml-3 w-52 mr-5"><el-input v-model="searchCategoryName" placeholder="请输入（模糊查询）" /></div>

                <el-text>创建日期</el-text>
                <div class="ml-3 w-30 mr-5">
                    <!-- 日期选择组件（区间选择） -->
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
                <el-button type="primary" @click="addCategoryBtnClick">
                    <el-icon class="mr-1">
                        <Plus />
                    </el-icon>
                    新增
                </el-button>
            </div>
            
            <!-- 修改点1：修改新增分类对话框的表单字段 -->
            <FormDialog ref="formDialogRef" title="添加体质分类" destroyOnClose @submit="onSubmit">
                <el-form ref="formRef" :rules="rules" :model="form">
                    <el-form-item label="分类名称" prop="name" label-width="120px" size="large">
                        <el-input v-model="form.name" placeholder="请输入分类名称" maxlength="20" show-word-limit clearable />
                    </el-form-item>
                    <el-form-item label="分类描述" prop="description" label-width="120px" size="large">
                        <el-input v-model="form.description" placeholder="请输入分类描述" maxlength="100" show-word-limit clearable />
                    </el-form-item>
                    <el-form-item label="体质特点" prop="characteristics" label-width="120px" size="large">
                        <el-input v-model="form.characteristics" :rows="3" type="textarea" placeholder="请输入体质特点" maxlength="500" show-word-limit />
                    </el-form-item>
                    <el-form-item label="常见症状" prop="commonSymptoms" label-width="120px" size="large">
                        <el-input v-model="form.commonSymptoms" :rows="3" type="textarea" placeholder="请输入常见症状" maxlength="500" show-word-limit />
                    </el-form-item>
                    <el-form-item label="心理特点" prop="psychologicalTraits" label-width="120px" size="large">
                        <el-input v-model="form.psychologicalTraits" :rows="3" type="textarea" placeholder="请输入心理特点" maxlength="500" show-word-limit />
                    </el-form-item>
                    <el-form-item label="饮食建议" prop="dietAdvice" label-width="120px" size="large">
                        <el-input v-model="form.dietAdvice" :rows="3" type="textarea" placeholder="请输入饮食建议" maxlength="500" show-word-limit />
                    </el-form-item>
                    <el-form-item label="生活建议" prop="lifestyleAdvice" label-width="120px" size="large">
                        <el-input v-model="form.lifestyleAdvice" :rows="3" type="textarea" placeholder="请输入生活建议" maxlength="500" show-word-limit />
                    </el-form-item>
                    <el-form-item label="运动建议" prop="exerciseAdvice" label-width="120px" size="large">
                        <el-input v-model="form.exerciseAdvice" :rows="3" type="textarea" placeholder="请输入运动建议" maxlength="500" show-word-limit />
                    </el-form-item>
                    <el-form-item label="穴位建议" prop="acupointAdvice" label-width="120px" size="large">
                        <el-input v-model="form.acupointAdvice" :rows="3" type="textarea" placeholder="请输入穴位建议" maxlength="500" show-word-limit />
                    </el-form-item>
                </el-form>
            </FormDialog>
            
            <!-- 修改点2：修改编辑分类对话框的表单字段 -->
            <el-dialog v-model="isCategoryUpdateEditorShow" :show-close="false" :fullscreen="true"
                :close-on-press-escape="false">
                <template #header="{ close, titleId, titleClass }">
                    <!--固定组件，固定到顶部-->
                    <el-affix :offset="20" style="width: 100%;">
                        <!-- 指定 flex 布局， 高度为 10， 背景色为白色 -->
                        <div class="flex h-10 bg-white">
                            <!-- 字体加粗 -->
                            <h4 class="font-bold">编辑体质分类</h4>
                            <!-- 靠右对齐 -->
                            <div class="ml-auto flex">
                                <el-button @click="isCategoryUpdateEditorShow = false">取消</el-button>
                                <el-button type="primary" @click="updateSubmit">
                                    <el-icon class="mr-1">
                                        <Promotion />
                                    </el-icon>
                                    保存
                                </el-button>
                            </div>
                        </div>
                    </el-affix>
                </template>
                <!-- label-position="top" 用于指定 label 元素在上面 -->
                <el-form :model="updateCategoryForm" ref="updateCategoryFormRef" label-position="top" size="large"
                    :rules="rules">
                    <el-form-item label="分类名称" prop="name">
                        <el-input v-model="updateCategoryForm.name" autocomplete="off" size="large" maxlength="20"
                            show-word-limit clearable />
                    </el-form-item>
                    <el-form-item label="分类描述" prop="description">
                        <el-input v-model="updateCategoryForm.description" autocomplete="off" size="large" maxlength="100"
                            show-word-limit clearable />
                    </el-form-item>
                    <el-form-item label="体质特点" prop="characteristics">
                        <el-input v-model="updateCategoryForm.characteristics" :rows="3" type="textarea"
                            placeholder="请输入体质特点" maxlength="500" show-word-limit />
                    </el-form-item>
                    <el-form-item label="常见症状" prop="commonSymptoms">
                        <!-- :rows="3" 指定 textarea 默认显示 3 行 -->
                        <el-input v-model="updateCategoryForm.commonSymptoms" :rows="3" type="textarea" placeholder="请输入常见症状" maxlength="500" show-word-limit />
                    </el-form-item>
                    <el-form-item label="心理特点" prop="psychologicalTraits">
                        <el-input v-model="updateCategoryForm.psychologicalTraits" :rows="3" type="textarea"
                            placeholder="请输入心理特点" maxlength="500" show-word-limit />
                    </el-form-item>
                    <el-form-item label="饮食建议" prop="dietAdvice">
                        <el-input v-model="updateCategoryForm.dietAdvice" :rows="3" type="textarea"
                            placeholder="请输入饮食建议" maxlength="500" show-word-limit />
                    </el-form-item>
                    <el-form-item label="生活建议" prop="lifestyleAdvice">
                        <el-input v-model="updateCategoryForm.lifestyleAdvice" :rows="3" type="textarea"
                            placeholder="请输入生活建议" maxlength="500" show-word-limit />
                    </el-form-item>
                    <el-form-item label="运动建议" prop="exerciseAdvice">
                        <el-input v-model="updateCategoryForm.exerciseAdvice" :rows="3" type="textarea"
                            placeholder="请输入运动建议" maxlength="500" show-word-limit />
                    </el-form-item>
                    <el-form-item label="穴位建议" prop="acupointAdvice">
                        <el-input v-model="updateCategoryForm.acupointAdvice" :rows="3" type="textarea"
                            placeholder="请输入穴位建议" maxlength="500" show-word-limit />
                    </el-form-item>
                </el-form>
            </el-dialog>

            <!-- 修改点3：修改表格显示的列 -->
            <el-table :data="tableData" border stripe style="width: 100%" v-loading="tableLoading">
                <el-table-column prop="name" label="分类名称" width="180" />
                <el-table-column prop="description" label="分类描述" width="200" />
                <el-table-column prop="characteristics" label="体质特点" width="250" />
                <el-table-column prop="createTime" label="创建时间" width="180" />
                <el-table-column label="操作" width="250">
                    <template #default="scope">
                        <el-button size="small" @click="showCategoryUpdateEditor(scope.row)">
                            <el-icon class="mr-1">
                                <Edit />
                            </el-icon>
                            编辑
                        </el-button>
                        <el-button size="small" @click="goCategoryDetailPage(scope.row.id)">
                            <el-icon class="mr-1">
                                <View />
                            </el-icon>
                            预览
                        </el-button>
                        <el-button type="danger" size="small" @click="deleteCategorySubmit(scope.row)">
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
import { Search, RefreshRight } from '@element-plus/icons-vue';
import { ref, reactive, onMounted } from 'vue'
import { useRouter } from 'vue-router'  // 修改点4：添加路由导入
import moment from 'moment';
import { getCategoryPageList, addCategory, deleteCategory, updateCategory, getCategoryDetail } from '@/api/admin/category.js';  // 修改点5：添加 getCategoryDetail 导入
import { showMessage, showModel } from '@/composables/util';
import FormDialog from '@/components/FormDialog.vue';

const router = useRouter()  // 修改点6：初始化路由

//每页展示数量变更事件
const handleSizeChange = (chooseSize) => {
    size.value = chooseSize
    getTableData()
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

//分页查询的分类名称
const searchCategoryName = ref('')
//日期
const pickDate = ref('')
//查询条件：开始结束时间
const startDate = ref(null)
const endDate = ref(null)

//重置查询条件
const reset = () => {
    searchCategoryName.value = ''
    pickDate.value = ''
    startDate.value = null
    endDate.value = null
    getTableData()
}

//监听日期选择器变化，赋值给开始结束时间
const datepickerChange = (e) => {
    if (e && e.length === 2) {
        startDate.value = moment(e[0]).format('YYYY-MM-DD')
        endDate.value = moment(e[1]).format('YYYY-MM-DD')
        console.log("开始时间：" + startDate.value + " 结束时间：" + endDate.value)
    } else {
        startDate.value = null
        endDate.value = null
    }
}

//表格数据
const tableData = ref([])
//当前页码，给了一个默认值1
const current = ref(1)
//总数据量，给了一个默认值0
const total = ref(0)
//每页显示的数据量， 给了一个默认值10
const size = ref(10)
//表格加载Loading
const tableLoading = ref(false)

// 修改点7：更新获取分类分页数据的函数
function getTableData() {
    //显示表格loading
    tableLoading.value = true
    //调用后台分页接口，并传入所需参数
    getCategoryPageList({
        current: current.value,
        size: size.value,
        startDate: startDate.value,
        endDate: endDate.value,
        name: searchCategoryName.value
    }).then((res) => {
        if (res.success == true) {
            tableData.value = res.data
            current.value = res.current
            size.value = res.size
            total.value = res.total
        }
    }).catch(() => {
        showMessage('获取分类列表失败', 'error')
    }).finally(() => {
        tableLoading.value = false
    })
}

onMounted(() => {
    getTableData()
})

const isCategoryUpdateEditorShow = ref(false)
const formDialogRef = ref(null)
const updateCategoryFormRef = ref(null)

//新增分类按钮点击事件
const addCategoryBtnClick = () => {
    // 清空表单数据
    Object.keys(form).forEach(key => {
        form[key] = ''
    })
    formDialogRef.value.open()
}

// 表单引用
const formRef = ref(null)

// 修改点8：更新新增分类表单对象，添加新字段
const form = reactive({
    name: '',
    description: '',
    characteristics: '',
    commonSymptoms: '',
    psychologicalTraits: '',
    dietAdvice: '',
    lifestyleAdvice: '',
    exerciseAdvice: '',
    acupointAdvice: ''
})

// 修改点9：更新编辑分类表单对象
const updateCategoryForm = reactive({
    id: null,
    name: '',
    description: '',
    characteristics: '',
    commonSymptoms: '',
    psychologicalTraits: '',
    dietAdvice: '',
    lifestyleAdvice: '',
    exerciseAdvice: '',
    acupointAdvice: ''
})

// 修改点10：更新规则校验
const rules = {
    name: [
        {
            required: true,
            message: '分类名称不能为空',
            trigger: 'blur'
        },
        {
            min: 1,
            max: 20,
            message: '分类名称长度在1-20个字符之间',
            trigger: 'blur'
        }
    ],
    description: [
        {
            max: 100,
            message: '分类描述不能超过100个字符',
            trigger: 'blur'
        }
    ],
    characteristics: [
        {
            max: 500,
            message: '体质特点不能超过500个字符',
            trigger: 'blur'
        }
    ],
    commonSymptoms: [
        {
            max: 500,
            message: '常见症状不能超过500个字符',
            trigger: 'blur'
        }
    ],
    psychologicalTraits: [
        {
            max: 500,
            message: '心理特点不能超过500个字符',
            trigger: 'blur'
        }
    ],
    dietAdvice: [
        {
            max: 500,
            message: '饮食建议不能超过500个字符',
            trigger: 'blur'
        }
    ],
    lifestyleAdvice: [
        {
            max: 500,
            message: '生活建议不能超过500个字符',
            trigger: 'blur'
        }
    ],
    exerciseAdvice: [
        {
            max: 500,
            message: '运动建议不能超过500个字符',
            trigger: 'blur'
        }
    ],
    acupointAdvice: [
        {
            max: 500,
            message: '穴位建议不能超过500个字符',
            trigger: 'blur'
        }
    ]
}

// 修改点11：更新新增分类提交函数
const onSubmit = () => {
    formRef.value.validate((valid) => {
        if (!valid) {
            console.log('表单验证不通过')
            return false
        }
        formDialogRef.value.showBtnLoading()
        
        addCategory(form).then((res) => {
            if (res.success == true) {
                showMessage('添加分类成功！')
                formDialogRef.value.close()
                getTableData()
            } else {
                let message = res.message
                showMessage(message, 'error')
            }
        }).catch(() => {
            showMessage('添加分类失败', 'error')
        }).finally(() => {
            formDialogRef.value.closeBtnLoading()
        })
    })
}

// 修改点12：更新编辑分类按钮点击事件
const showCategoryUpdateEditor = (row) => {
    isCategoryUpdateEditorShow.value = true
    getCategoryDetail(row.id).then((res) => {
        if (res.success) {
            Object.assign(updateCategoryForm, res.data)
        } else {
            showMessage('获取分类详情失败', 'error')
            isCategoryUpdateEditorShow.value = false
        }
    }).catch(() => {
        showMessage('获取分类详情失败', 'error')
        isCategoryUpdateEditorShow.value = false
    })
}

// 修改点13：添加更新分类提交函数
const updateSubmit = () => {
    updateCategoryFormRef.value.validate((valid) => {
        if (!valid) {
            console.log('表单验证不通过')
            return false
        }
        
        updateCategory(updateCategoryForm).then((res) => {
            if (res.success == true) {
                showMessage('更新分类成功！')
                isCategoryUpdateEditorShow.value = false
                getTableData()
            } else {
                let message = res.message
                showMessage(message, 'error')
            }
        }).catch(() => {
            showMessage('更新分类失败', 'error')
        })
    })
}

// 修改点14：添加预览分类详情函数
const goCategoryDetailPage = (id) => {
    router.push(`/admin/category/detail/${id}`)
}

const deleteCategorySubmit = (row) => {
    showModel('是否确定要删除该分类? ').then(() => {
        deleteCategory(row.id).then((res) => {
            if (res.success == true) {
                showMessage('删除成功')
                getTableData()
            } else {
                let message = res.message
                showMessage(message, 'error')
            }
        }).catch(() => {
            showMessage('删除失败', 'error')
        })
    }).catch(() => {
        console.log('取消了')
    })
}
</script>