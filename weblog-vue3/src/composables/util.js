import { ElMessageBox } from "element-plus"
import 'element-plus/es/components/message-box/style/css' 
import nprogress from "nprogress"


//显示页面加载Loading
export function showPageLoading() {
  nprogress.start()
}
//隐藏页面加载Loading
export function hidePageLoading() {
  nprogress.done()
}
export function showMessage(message = '提示内容', type = 'success', customClass = '') {
  // 显示消息提示
  return ElMessage({
    type: type, //默认是成功消息提示
    customClass, // 自定义样式类
    message: message,
  })
}
//弹出确认框
export function showModel(content = '提示内容', type = 'warning', title = ''){
  return ElMessageBox.confirm(
    content,
    title,
    {
      confirmButtonText: '确定',
      cancelButtonText: '取消',
      type,
    }
  )
}