// 假设 http 是已经配置好的请求实例
import http from '@/request/request.js';

// 查询护理项目列表（分页）
function findNurseItemPage(data) {
    return http.get('/nurseContent/findNurseItemPage', { params: data });
}

// 添加护理项目
function addNurseItem(data) {
    return http.post('/nurseContent/addNurseItem', data);
}

// 修改护理项目
function updateNurseItem(data) {
    return http.post('/nurseContent/updateNurseItem', data);
}

// 删除护理项目
function delNurseItem(id) {
    return http.get('/nurseContent/delNurseItem', { params: { id } });
}

export {
    findNurseItemPage,
    addNurseItem,
    updateNurseItem,
    delNurseItem,
};