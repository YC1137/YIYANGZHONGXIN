package com.neuedu.yyzx.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import com.neuedu.yyzx.dto.CustomerNurseItemDTO;
import com.neuedu.yyzx.pojo.CustomerNurseItem;
import com.neuedu.yyzx.service.CustomerNurseItemVoService;
import com.neuedu.yyzx.utils.ResultVo;
import com.neuedu.yyzx.vo.CustomerNurseItemVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/customerNurseItem")
@CrossOrigin
@Api(tags = "服务关注管理")
public class CustomerNurseItemController {
    @Autowired
    private CustomerNurseItemVoService customerNurseItemVoService;

    @GetMapping("/listCustomerItem")
    @ApiOperation("查询客户护理项目")
    public ResultVo<Page<CustomerNurseItemVo>> listCustomerNurseItem(CustomerNurseItemDTO customerNurseItemDTO) throws Exception{
        return customerNurseItemVoService.selectCustomerNurseItemVo(customerNurseItemDTO);
    }

    @PostMapping("/addItemToCustomer")
    @ApiOperation("为顾客单个/多个批量添加项目")
    public ResultVo addItemToCustomer(@RequestBody List<CustomerNurseItem> customerNurseItem) throws Exception{
        return customerNurseItemVoService.addItemToCustomer(customerNurseItem);
    }

    @PostMapping("/removeCustomerLevelAndItem")
    @ApiOperation("移除客户护理级别级联移除用户当前级别的护理项目")
    public ResultVo removeItemFromCustomer(Integer levelId, Integer customerId) throws Exception{
        return customerNurseItemVoService.removeItemFromCustomer(levelId, customerId);
    }

    @PostMapping("/enewNurseItem")
    @ApiOperation("客户续费")
    public ResultVo enewNurseItem(CustomerNurseItem customerNurseItem) throws Exception{
        return customerNurseItemVoService.enewNurseItem(customerNurseItem);
    }

    @GetMapping("/isIncludesItemCustomer")
    @ApiOperation("客户是否包含该服务")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "customerId",value = "客户编号",required = true,dataType = "Integer"),
            @ApiImplicitParam(name = "itemId",value = "护理项目编号",required = true,dataType = "Integer")
    })
    public ResultVo isIncludesItemCustomer(Integer customerId, Integer itemId) throws Exception{
        return customerNurseItemVoService.isIncludesItemCustomer(customerId, itemId);
    }

    @GetMapping("/removeCustomerItem")
    @ApiOperation("移除客户护理项目")
    public ResultVo removeCustomerItem(Integer id) throws Exception{
        return customerNurseItemVoService.removeCustomerItem(id);
    }
}
