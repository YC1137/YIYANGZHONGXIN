// 引入封装好的axios实例
import http from '@/request/request.js';

// 查询护理记录列表（分页 + 条件）
function listNurseRecordsVo(data) {
    return http.get('/nurseRecord/listNurseRecord', { params: data });
}

// 删除护理记录（逻辑删除）
function removeCustomerRecord(data) {
    return http.get('/nurseRecord/removeNurseRecord', { params: data });
}

// 添加护理记录
function addNurseRecord(data) {
    return http.post('/nurseRecord/addNurseRecord', data);
}

export {
    listNurseRecordsVo,
    removeCustomerRecord,
    addNurseRecord
};
