import http from '@/request/request.js';

// 给客户添加护理项目（确认设置护理级别及项目时使用）
function addItemToCustomer(data) {
    return http.post('/customerNurseItem/addItemToCustomer', data);
}

// 移除客户的护理级别以及对应的护理项目
function removeCustomerLevelAndItem(params) {
    return http.get('/customerNurseItem/removeCustomerLevelAndItem', { params });
}

export {
    addItemToCustomer,
    removeCustomerLevelAndItem
};