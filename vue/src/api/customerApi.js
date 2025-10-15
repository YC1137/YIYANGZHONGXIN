    // 引入封装好的axios  @等价于/src
    import http from '@/request/request.js';
    //添加客户
    function addCustomer(data){
        return http.post('/customer/addCustomer',data)
    }
    //查询系统客户信息
    function getCustomerList(data) {
        return http.get('/customer/findAllCustomerPage', { params: data });
    }

    //更新用户
    function updateCustomer(data){
        return http.post('/customer/updateCustomer',data)
    }
    //删除用户
    function delCustomer(data){
        return http.get('/customer/delCustomer',{params:data})
    }
    //查询系统用户信息
    function getKhxxList(data) {
        return http.get('/customer/findAllCustomerPage', { params: data }); // ✅ 使用存在的接口
    }

    function findAllCustomer(data){
        return http.get('/customer/findAllCustomerPage',{params:data})
    }

    function editCustomer(data){
        return http.get('/customer/editKhxx',{params:data})
    }
    export{
        addCustomer,
        getCustomerList,
        updateCustomer,
        delCustomer,
        getKhxxList,
        findAllCustomer,
        editCustomer
    }