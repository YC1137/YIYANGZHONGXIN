// 引入封装好的axios  @等价于/src
import http from '@/request/request.js';
//查询外出记录
function queryOutwardVo(data){
    return http.post('/outward/queryOutwardVo',data)
}
//添加外出审批
function addOutward(data){
    return http.post('/outward/addOutward',data)

}
//审批外出申请
function examineOutward(data){
    return http.post('/outward/examineOutward',data)
}
//撤回外出申请
function delOutward(data){
    return http.post('/outward/delOutward',data)
}
//登记回院时间
function updateBackTime(data){
    return http.post('/outward/updateBackTime',data)
}
export{
    addOutward,
    examineOutward,
    delOutward,
	updateBackTime,
	queryOutwardVo
}