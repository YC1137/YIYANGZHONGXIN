package com.neuedu.yyzx.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.neuedu.yyzx.dto.CustomerDTO;
import com.neuedu.yyzx.dto.UserDTO;
import com.neuedu.yyzx.pojo.Customer;
import com.neuedu.yyzx.pojo.User;
import com.neuedu.yyzx.service.CustomerService;
import com.neuedu.yyzx.service.UserService;
import com.neuedu.yyzx.utils.ResultVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/customer")
@CrossOrigin
@Api(tags = "客户管理")
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    @ApiOperation("入住登记")
    @PostMapping("/addCustomer")
    public ResultVo addCustomer(Customer customer) throws  Exception{
        customer.setIsDeleted(0);
        customerService.save(customer);
        return ResultVo.ok("添加成功");
    }

    @ApiOperation("查询全部客户-分页")
    @GetMapping("/findAllCustomerPage")
    public ResultVo<Page<Customer>> findAllCustomerPage(CustomerDTO customerDTO) throws Exception {
        System.out.println("customerDTO.getManType() = " + customerDTO.getManType());
        System.out.println("customerDTO.getUserId() = " + customerDTO.getUserId());

        Page<Customer> page = new Page<>(customerDTO.getPageSize(), 6);
        QueryWrapper<Customer> qw = new QueryWrapper<>();

        if (customerDTO.getCustomerName() != null && !customerDTO.getCustomerName().isEmpty()) {
            qw.like("customer_name", customerDTO.getCustomerName());
        }


        if ("3".equals(String.valueOf(customerDTO.getManType()))) {
            qw.and(wrapper -> wrapper.isNull("user_id").or().eq("user_id", -1));
        } else if (customerDTO.getUserId() != null) {
            qw.eq("user_id", customerDTO.getUserId());
        }

        customerService.page(page, qw);
        return ResultVo.ok(page);
    }

    @ApiOperation("删除客户")
    @GetMapping("/delCustomer")
    public ResultVo delCustomer(Integer id) throws  Exception{
        customerService.removeById(id);
        return ResultVo.ok("删除成功");
    }

    @ApiOperation("修改客户")
    @PostMapping("/updateCustomer")
    public ResultVo updateUser(Customer customer) throws  Exception{
        UpdateWrapper<Customer> updateWrapper = new UpdateWrapper<Customer>();
        updateWrapper.eq("id",customer.getId());
        customerService.update(customer,updateWrapper);
        return ResultVo.ok("修改成功");
    }
    @ApiOperation("修改客户的管家ID")
    @GetMapping("/editKhxx")
    public ResultVo editKhxx(Integer id, Integer userId) {
        Customer customer = customerService.getById(id);
        if (customer == null) {
            return ResultVo.fail("客户不存在");
        }
        customer.setUserId(userId);
        customerService.updateById(customer);
        return ResultVo.ok("修改成功");
    }

}
