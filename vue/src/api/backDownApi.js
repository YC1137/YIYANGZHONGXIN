// backdownApi.js
import http from '@/request/request.js';
// 添加退住申请
function addBackdown(data) {
    return http.post('/backDown/addBackDown', data);  // ✅ 路径改为 addBackDown
}

// 审批退住申请
function examineBackdown(data) {
    return http.post('/backDown/examineBackDown', data);  // ✅ 路径改为 examineBackDown
}

// 删除/撤销退住申请
function delBackdown(data) {
    return http.get('/backDown/delBackDown', { params: data });  // ✅ 路径改为 delBackDown
}

// 查询退住申请记录（分页）
function queryBackdownVo(data) {
    return http.get('/backDown/listBackDown', { params: data });  // ✅ 路径改为 listBackDown
}


export {
    addBackdown,
    examineBackdown,
    delBackdown,
    queryBackdownVo
};