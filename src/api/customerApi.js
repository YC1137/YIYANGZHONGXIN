import http from '@/request/request.js'

// 查询所有客户信息（分页）
export function findAllCustomer(params) {
  return http({
    url: '/customer/list',
    method: 'get',
    params
  })
}

// 添加客户
export function addCustomer(data) {
  return http({
    url: '/customer/add',
    method: 'post',
    data
  })
}

// 修改客户信息
export function updateCustomer(data) {
  return http({
    url: '/customer/update',
    method: 'put',
    data
  })
}

// 删除客户
export function delCustomer(params) {
  return http({
    url: '/customer/delete',
    method: 'delete',
    params
  })
}
