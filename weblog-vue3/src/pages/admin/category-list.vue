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
            <FormDialog ref="formDialogRef" title="添加文章分类" destroyOnClose @submit="onSubmit">
                <el-form ref="formRef" :rules="rules" :model="form">
                <el-form-item label="分类名称" prop="name" label-width="80px" size="large">
                    <el-input v-model="form.name" placeholder="请输入分类名称" maxlength="20px" show-word-limit clearable></el-input>
                </el-form-item>
                </el-form>
            </FormDialog>
            <!-- <el-dialog v-model="dialogVisible" title="添加文章分类" width="40%" :draggable ="true" :close-on-click-modal="false" :close-on-press-escape="false">
        <el-form ref="formRef" :rules="rules" :model="form">
                    <el-form-item label="分类名称" prop="name" label-width="80px">
                        输入框组件 
                        <el-input size="large" v-model="form.name" placeholder="请输入分类名称" maxlength="20" show-word-limit clearable/>
                    </el-form-item>
                </el-form>
        <template #footer>
            <span class="dialog-footer">
                <el-button @click="dialogVisible = false">取消</el-button>
                <el-button type="primary" @click="onSubmit">
                    提交
                </el-button>
            </span>
        </template>
    </el-dialog> -->

            <!-- 分页列表 -->
            <el-table :data="tableData" border stripe style="width: 100%" v-loading="tableLoading">
                <el-table-column prop="name" label="分类名称" width="180" />
                <el-table-column prop="createTime" label="创建时间" width="180" />
                <el-table-column label="操作" >
                    <template #default="scope">
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
                :small="false" :background="true" layout="total, sizes, prev, pager, next, jumper"
                :total="total" @size-change="handleSizeChange" @current-change=getTableData />
            </div>
        </el-card>
    </div>
</template>

<script setup>

import { Search, RefreshRight } from '@element-plus/icons-vue';
import { ref,reactive } from 'vue'
import moment from 'moment';
import { getCategoryPageList, addCategory, deleteCategory } from '@/api/admin/category.js';
import { showMessage, showModel } from '@/composables/util';
import FormDialog from '@/components/FormDialog.vue';

//每页展示数量变更事件
const handleSizeChange = (chooseSize) => {
    size.value = chooseSize
    getTableData
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
const startDate = reactive({})
const endDate = reactive({})
//重置查询条件
const reset = () => {
    searchCategoryName.value = ''
    pickDate.value = ''
    startDate.value = null
    endDate.value = null
}
//监听日期选择器变化，赋值给开始结束时间
const datepickerChange = (e) => {
    startDate.value = moment(e[0]).format('YYYY-MM-DD')
    endDate.value = moment(e[1]).format('YYYY-MM-DD')
    console.log("开始时间：" + startDate.value + " 结束时间：" + endDate.value)
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
//获取分类分页数据
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
        if(res.success == true) {
            tableData.value = res.data
            current.value = res.current
            size.value = res.size
            total.value = res.total
        }
    }).finally(() => tableLoading.value = false) //隐藏表格loading
}
getTableData()
// 对话框是否显示

    //const dialogVisible = ref(false)
    const formDialogRef = ref(null)
    //新增分类按钮点击事件
    const addCategoryBtnClick = () => {
        formDialogRef.value.open()
    }


    // 表单引用
    const formRef = ref(null)

    // 修改用户密码表单对象
    const form = reactive({
        name: ''
    })

    // 规则校验
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
        ]
    }

    const onSubmit = () => {
        // 先验证 form 表单字段
        formRef.value.validate((valid) => {
            if (!valid) {
                console.log('表单验证不通过')
                return false
            }
            //显示提交按钮loading
            formDialogRef.value.showBtnLoading()
            //请求添加分类接口
            addCategory(form).then((res) => {
                if (res.success == true) {
                    showMessage('添加分类成功！')
                    //将表单中分类名称置空
                    form.name = ''
                    //关闭对话框
                    formDialogRef.value.close()
                    //重新请求分页接口，渲染数据
                    getTableData()
                } else {
                    //获取失败，提示错误信息
                    let message = res.message
                    showMessage(message, 'error')
                }
            }).finally(() => formDialogRef.value.closeBtnLoading())//隐藏提交按钮Loading

        })
    }
    const deleteCategorySubmit = (row) => {
        console.log(row.id)
        showModel('是否确定要删除该分类? ').then(() => {
            console.log('确定要删除')
            deleteCategory(row.id).then((res) => {
                if(res.success == true) {
                    showMessage('删除成功')
                    //重新请求分页接口，渲染数据
                    getTableData()
                } else {
                    //获取服务端返回的错误消息
                    let message = res.message
                    //提示错误消息
                    showMessage(message, 'error')
                }
            })
        }).catch(() => {
            console.log('取消了')
        })
    }
</script>