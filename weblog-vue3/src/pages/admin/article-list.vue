<template>
    <div>
        <!-- 表头分页查询条件， shadow="never" 指定 card 卡片组件没有阴影 -->
        <el-card shadow="never" class="mb-5">
            <!-- flex 布局，内容垂直居中 -->
            <div class="flex items-center">
                <el-text>文章标题</el-text>
                <div class="ml-3 w-52 mr-5"><el-input v-model="searchArticleTitle" placeholder="请输入（模糊查询）" /></div>

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
                <el-button type="primary" @click="isArticlePublishEditorShow = true">
                    <el-icon class="mr-1">
                        <EditPen />
                    </el-icon>
                    写文章
                </el-button>
            </div>
            <!-- 写博客 -->
        <el-dialog v-model="isArticlePublishEditorShow" :show-close="false" :fullscreen="true">
            <template #header="{ close, titleId, titleClass }">
                <!--固定组件，固定到顶部-->
                <el-affix :offset="20" style="width: 100%;">
                    <!-- 指定 flex 布局， 高度为 10， 背景色为白色 -->
                    <div class="flex h-10 bg-white">
                        <!-- 字体加粗 -->
                        <h4 class="font-bold">写文章</h4>
                        <!-- 靠右对齐 -->
                        <div class="ml-auto flex">
                            <el-button @click="isArticlePublishEditorShow = false">取消</el-button>
                            <el-button type="primary" @click="publishArticleSubmit">
                                <el-icon class="mr-1">
                                    <Promotion />
                                </el-icon>
                                发布
                            </el-button>
                        </div>
                    </div>
                </el-affix>
            </template>
            <!-- label-position="top" 用于指定 label 元素在上面 -->
            <el-form :model="form" ref="publishArticleFormRef" label-position="top" size="large" :rules="rules">
                <el-form-item label="标题" prop="title">
                    <el-input v-model="form.title" autocomplete="off" size="large" maxlength="40" show-word-limit
                        clearable />
                </el-form-item>
                <el-form-item label="内容" prop="content">
                    <!-- Markdown 编辑器 -->
                     <MdEditor v-model="form.content" @onUploadImg="onUploadImg" editorId="publishArticleEditor" />
                </el-form-item>
                <el-form-item label="封面" prop="cover">
                        <el-upload class="avatar-uploader" :on-change="handleCoverChange" action="#" :auto-upload="false" :show-file-list="false">
                            <img v-if="form.cover" :src="form.cover" class="avatar" />
                            <el-icon v-else class="avatar-uploader-icon">
                                <Plus />
                            </el-icon>
                        </el-upload>
                </el-form-item>
                <el-form-item label="摘要" prop="summary">
                    <!-- :rows="3" 指定 textarea 默认显示 3 行 -->
                    <el-input v-model="form.summary" :rows="3" type="textarea" placeholder="请输入文章摘要" />
                </el-form-item>
                <el-form-item label="分类" prop="categoryId">
                    <el-select v-model="form.categoryId" clearable placeholder="---请选择---" size="large">
                       <el-option v-for="item in categories" :key="item.value" :label="item.label" :value="item.value" />
                    </el-select>
                </el-form-item>
                <el-form-item label="标签" prop="tags">
                    <!-- 标签选择 -->
                    <el-select v-model="form.tags" multiple filterable remote reserve-keyword placeholder="---请输文章标签---"
                        remote-show-suffix :remote-method="remoteMethod" allow-create default-first-option
                        :loading="tagSelectLoading" size="large">
                        <el-option v-for="item in tags" :key="item.value" :label="item.label" :value="item.value" />
                    </el-select>
                </el-form-item>
            </el-form>
        </el-dialog>
        <!-- 编辑博客 -->
        <el-dialog v-model="isArticleUpdateEditorShow" :show-close="false" :fullscreen="true" :close-on-press-escape="false">
            <template #header="{ close, titleId, titleClass }">
                <!--固定组件，固定到顶部-->
                <el-affix :offset="20" style="width: 100%;">
                    <!-- 指定 flex 布局， 高度为 10， 背景色为白色 -->
                    <div class="flex h-10 bg-white">
                        <!-- 字体加粗 -->
                        <h4 class="font-bold">编辑文章</h4>
                        <!-- 靠右对齐 -->
                        <div class="ml-auto flex">
                            <el-button @click="isArticleUpdateEditorShow = false">取消</el-button>
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
            <el-form :model="updateArticleForm" ref="updateArticleFormRef" label-position="top" size="large" :rules="rules">
                <el-form-item label="标题" prop="title">
                    <el-input v-model="updateArticleForm.title" autocomplete="off" size="large" maxlength="40" show-word-limit
                        clearable />
                </el-form-item>
                <el-form-item label="内容" prop="content">
                    <!-- Markdown 编辑器 -->
                     <MdEditor v-model="updateArticleForm.content" @onUploadImg="onUploadImg" editorId="updateArticleEditor" />
                </el-form-item>
                <el-form-item label="封面" prop="cover">
                        <el-upload class="avatar-uploader" :on-change="handleUpdateCoverChange" action="#" :auto-upload="false" :show-file-list="false">
                            <img v-if="updateArticleForm.cover" :src="updateArticleForm.cover" class="avatar" />
                            <el-icon v-else class="avatar-uploader-icon">
                                <Plus />
                            </el-icon>
                        </el-upload>
                </el-form-item>
                <el-form-item label="摘要" prop="summary">
                    <!-- :rows="3" 指定 textarea 默认显示 3 行 -->
                    <el-input v-model="updateArticleForm.summary" :rows="3" type="textarea" placeholder="请输入文章摘要" />
                </el-form-item>
                <el-form-item label="分类" prop="categoryId">
                    <el-select v-model="updateArticleForm.categoryId" clearable placeholder="---请选择---" size="large">
                       <el-option v-for="item in categories" :key="item.value" :label="item.label" :value="item.value" />
                    </el-select>
                </el-form-item>
                <el-form-item label="标签" prop="tags">
                    <!-- 标签选择 -->
                    <el-select v-model="updateArticleForm.tags" multiple filterable remote reserve-keyword placeholder="---请输文章标签---"
                        remote-show-suffix :remote-method="remoteMethod" allow-create default-first-option
                        :loading="tagSelectLoading" size="large">
                        <el-option v-for="item in tags" :key="item.value" :label="item.label" :value="item.value" />
                    </el-select>
                </el-form-item>
            </el-form>
        </el-dialog>
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
                <el-table-column prop="id" label="ID" width="50" />
                <el-table-column prop="title" label="标题" width="180" />
                <el-table-column prop="cover" label="封面" width="180" >
                    <template #default="scope">
                        <el-image style="width: 50px;" :src="scope.row.cover" />
                    </template>
                </el-table-column>
                <el-table-column prop="createTime" label="发布时间" width="180" />
                <el-table-column label="操作" >
                    <template #default="scope">
                        <el-button size="small" @click="showArticleUpdateEditor(scope.row)">
                            <el-icon class="mr-1">
                                <Edit />
                            </el-icon>
                            编辑
                        </el-button>
                        <el-button size="small" @click="goArticleDetailPage(scope.row.id)">
                            <el-icon class="mr-1">
                                <View />
                            </el-icon>
                            预览
                        </el-button>
                        <el-button type="danger" size="small" @click="deleteArticleSubmit(scope.row)">
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

import { Search, RefreshRight, FullScreen } from '@element-plus/icons-vue';
import { ref,reactive } from 'vue'
import moment from 'moment';
import { deleteArticle, getArticlePageList, publishArticle, getArticleDetail, updateArticle } from '@/api/admin/article.js';
import { getCategorySelectList } from '@/api/admin/category';
import { searchTags, getTagSelectList } from '@/api/admin/tag';
import { showMessage, showModel } from '@/composables/util';
import FormDialog from '@/components/FormDialog.vue';
import { MdEditor } from 'md-editor-v3';
import 'md-editor-v3/lib/style.css';
import { uploadFile } from '@/api/admin/file.js';
import { useRouter } from 'vue-router';

//是否显示文章发布对话框
const isArticlePublishEditorShow = ref(false)
//表单对象
const form = reactive({
    id: null,
    title: '',
    content: '请输入文章内容',
    cover: '',
    summary: '',
    categoryId: null,
    tags: []
})
//表单验证规则
const rules = {
    title: [{ required: true, message: '请输入文章标题', trigger: 'blur' },
        {min: 1, max: 40, message: '标题长度在1-40个字符之间', trigger: 'blur'}
    ],
    content: [{ required: true }],
    cover: [{ required: true }],
    categoryId: [{ required: true, message: '请选择文章分类', trigger: 'blur' }],
    tags: [{ required: true, message: '请选择文章标签', trigger: 'blur' }]
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
const publishArticleFormRef = ref(null)
// 发布文章提交事件
const publishArticleSubmit = () => {
    console.log('提交md内容：' + form.content)
    //校验表单
    publishArticleFormRef.value.validate((valid) => {
        if(valid) {
            //调用发布文章接口
            publishArticle(form).then((res) => {
                if(res.success == true) {
                    showMessage('发布成功')
                    //隐藏发布文章对话框
                    isArticlePublishEditorShow.value = false
                    //将表单对象置空
                    form.content = ''
                    form.title = ''
                    form.cover = ''
                    form.summary = ''
                    form.categoryId = null
                    form.tags = []
                    //重新请求分页接口，渲染数据
                    getTableData()
                } else {
                    //获取服务端返回的错误消息
                    let message = res.message
                    //提示错误消息
                    showMessage(message, 'error')
                    return
                }
            })
        } else {
            console.log('表单验证失败')
        }
    })
}
const handleCoverChange = (file) => {
    console.log(file)
    //表单对象
    let formData = new FormData()
    //添加file字段，值为上传的文件对象
    formData.append('file', file.raw)
    uploadFile(formData).then((e) => {
        //相应参数失败
        if(e.success == false) {
            let message = e.message
            showMessage(message, 'error')
            return
        } else {
            form.cover = e.data.url
            showMessage('封面上传成功')
        }
    })
}
// 编辑器图片上传
const onUploadImg = async (files, callback) => {
    const res = await Promise.all(
        files.map((file) => {
            return new Promise((rev, rej) => {
                console.log('==> 编辑器开始上传文件...')
                let formData = new FormData()
                formData.append("file", file);
                uploadFile(formData).then((res) => {
                    console.log(res)
                    console.log('访问路径：' + res.data.url)
                    // 调用 callback 函数，回显上传图片
                    callback([res.data.url]);
                })
            });
        })
    );
}
//文章分类
const categories = ref([])
getCategorySelectList().then((res) => {
    console.log("获取文章分类列表")
    categories.value = res.data
})
//模糊搜素的文章标题
const searchArticleTitle = ref('')
//日期
const pickDate = ref('')
//查询条件：开始结束时间
const startDate = reactive({})
const endDate = reactive({})
//重置查询条件
const reset = () => {
    searchArticleTitle.value = ''
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
//标签select Loading 状态，默认不显示
const tagSelectLoading = ref(false)
//标签列表
const tags = ref([])
//渲染标签数据
getTagSelectList().then((res) => {
    console.log("获取标签列表")
    tags.value = res.data
})
//根据用户输入的标签名称，远程模糊查询
const remoteMethod = (query) => {
    console.log("远程搜索标签：" + tags.value)
    //如果用户的查询关键词不为空
    if(query) {
        //显示loading
        tagSelectLoading.value = true
        //调用模糊查询接口
        searchTags(query).then((res) => {
            if(res.success == true) {
                //设置到tags变量
                tags.value = res.data
            }
        }).finally(() => tagSelectLoading.value = false)
    } else {
        tags.value = []
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
//获取分类分页数据
function getTableData() {
    //显示表格loading
    tableLoading.value = true
    //调用后台分页接口，并传入所需参数
    getArticlePageList({
        current: current.value,
        size: size.value,
        startDate: startDate.value,
        endDate: endDate.value,
        title: searchArticleTitle.value
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
//每页展示数量变更事件
const handleSizeChange = (chooseSize) => {
    console.log('选择的页码' + chooseSize)
    size.value = chooseSize
    getTableData()
}
    //删除文章提交事件
 const deleteArticleSubmit = (row) => {
        console.log(row.id)
        showModel('是否确定要删除该文章? ').then(() => {
            console.log('确定要删除文章')
            deleteArticle(row.id).then((res) => {
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
    //编辑文章表单引用
const updateArticleFormRef = ref(null)
//是否显示文章编辑对话框
const isArticleUpdateEditorShow = ref(false)
//编辑文章表单对象
const updateArticleForm = reactive({
    id: null,
    title: '',
    content: '请输入文章内容',
    cover: '',
    summary: '',
    categoryId: null,
    tags: []
})
//编辑文章：上传文章封面图片
const handleUpdateCoverChange = (file) => {
    //表单对象
    let formData = new FormData()
    //添加file字段，值为上传的文件对象
    formData.append('file', file.raw)
    uploadFile(formData).then((e) => {
        //相应参数失败
        if(e.success == false) {
            let message = e.message
            showMessage(message, 'error')
            return
        } else {
            //成功则将图片地址赋值给表单对象，并提示成功
            updateArticleForm.cover = e.data.url
            showMessage('封面上传成功')
        }
    })
}
        // 编辑文章按钮点击事件
        const showArticleUpdateEditor = (row) => {
            // 显示编辑文章对话框
            isArticleUpdateEditorShow.value = true
            // 拿到文章 ID
            let articleId = row.id
            getArticleDetail(articleId).then((res) => {
                if (res.success) {
                    // 设置表单数据
                    updateArticleForm.id = res.data.id
                    updateArticleForm.title = res.data.title
                    updateArticleForm.cover = res.data.cover
                    updateArticleForm.content = res.data.content
                    updateArticleForm.categoryId = res.data.categoryId
                    updateArticleForm.tags = res.data.tagIds
                    updateArticleForm.summary = res.data.summary
                }
            })
        }
// 保存文章
const updateSubmit = () => {
    updateArticleFormRef.value.validate((valid) => {
        // 校验表单
        if (!valid) {
            return false
        }

        // 请求更新文章接口
        updateArticle(updateArticleForm).then((res) => {
            if (res.success == false) {
                // 获取服务端返回的错误消息
                let message = res.message
                // 提示错误消息
                showMessage(message, 'error')
                return
            }

            showMessage('保存成功')
            // 隐藏编辑框
            isArticleUpdateEditorShow.value = false
            // 重新请求分页接口，渲染列表数据
            getTableData()
        })
    })
}

const router = useRouter() 
//跳转文章详情页
const goArticleDetailPage = (articleId) => {
    router.push({ path: '/article/' + articleId })
}


</script>
<style scoped>

        /* 封面图片样式 */
        .avatar-uploader .avatar {
            width: 200px;
            height: 100px;
            display: block;
        }

        .el-icon.avatar-uploader-icon {
            font-size: 28px;
            color: #8c939d;
            width: 200px;
            height: 100px;
            text-align: center;
        }
</style>
<style>
.md-editor-footer {
    height: 40px;
}
</style>