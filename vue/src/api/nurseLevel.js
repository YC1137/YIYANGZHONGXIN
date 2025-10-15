// 假设 http 是已经配置好的请求实例
import http from '@/request/request.js';

// 获取所有护理等级
function getAllNurseLevels(data) {
    return http.get('/nurseLevelItem/listNurseLevel', {params: data});
}

// 添加护理等级项目
function addNurseLevel(data) {
    return http.post('/nurseLevelItem/addNurseLevel', data);
}

// 更新护理等级项目
function updateNurseLevel(data) {
    return http.post('/nurseLevelItem/updateNurseLevel', data);
}

// 删除护理等级项目
function delNurseLevel(id) {
    return http.get('/nurseLevelItem/removeNurseLevel', {params: {id}});
}

// 护理项目的配置
function addItemToLevel(data) {
    return http.post('/nurseLevelItem/addItemToLevel', data);
}

// 删除护理级别中的护理项目
function removeNurseLevelItem(levelId, itemId) {
    return http.get('/nurseLevelItem/removeNurseLevelItem', {params: {levelId, itemId}});
}

export {
    getAllNurseLevels,
    addNurseLevel,
    updateNurseLevel,
    delNurseLevel,
    addItemToLevel,
    removeNurseLevelItem,
};