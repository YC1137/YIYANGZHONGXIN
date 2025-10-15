// src/api/serviceApi.js
import request from '@/request/request'

export function getServiceListByUser(params) {
    return request({
        url: '/service/listByUser',
        method: 'get',
        params
    })
}

export function addServiceForUser(data) {
    return request({
        url: '/service/addForUser',
        method: 'post',
        data
    })
}

export function removeServiceForUser(data) {
    return request({
        url: '/service/removeForUser',
        method: 'post',
        data
    })
}
