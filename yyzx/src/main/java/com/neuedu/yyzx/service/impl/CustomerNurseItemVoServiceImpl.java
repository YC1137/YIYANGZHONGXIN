package com.neuedu.yyzx.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;


import com.neuedu.yyzx.dto.CustomerNurseItemDTO;
import com.neuedu.yyzx.mapper.CustomerMapper;
import com.neuedu.yyzx.mapper.CustomerNurseItemMapper;
import com.neuedu.yyzx.pojo.Customer;
import com.neuedu.yyzx.pojo.CustomerNurseItem;
import com.neuedu.yyzx.service.CustomerNurseItemVoService;
import com.neuedu.yyzx.utils.ResultVo;
import com.neuedu.yyzx.vo.CustomerNurseItemVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerNurseItemVoServiceImpl extends ServiceImpl<CustomerNurseItemMapper, CustomerNurseItem> implements CustomerNurseItemVoService {

    @Autowired
    private CustomerMapper customerMapper;

    @Autowired
    private CustomerNurseItemMapper customerNurseItemMapper;

    @Override
    public ResultVo<Page<CustomerNurseItemVo>> selectCustomerNurseItemVo(CustomerNurseItemDTO customerNurseItemDTO)throws Exception{
        Page<CustomerNurseItemVo> page = new Page<>(customerNurseItemDTO.getPageNum(),6);
        customerNurseItemMapper.selectCustomerNurseItemVo(page,customerNurseItemDTO.getCustomerId());
        return ResultVo.ok(page);
    }

    @Override
    public ResultVo addItemToCustomer(List<CustomerNurseItem> customerNurseItem) throws Exception {
       if(customerNurseItem.size()>0){
          //判断选择的是级别中的护理项目，还是单独购买的护理项目
           if(customerNurseItem.get(0).getLevelId()!=null){
               //购买的级别的护理项目
               Customer customer = new Customer();
               customer.setId(customerNurseItem.get(0).getCustomerId());
               customer.setLevelId(customerNurseItem.get(0).getLevelId());
               int row1 = customerMapper.updateById(customer);
               boolean flag = saveBatch(customerNurseItem);
               if(!(flag && row1>0))throw new RuntimeException("批量保存失败");
           }else{
               //单独购买的护理项目
               saveBatch(customerNurseItem);
           }
           return ResultVo.ok("护理项目添加成功");
       }
       return ResultVo.fail("请选择要添加的护理项目");
    }

    @Override
    public ResultVo removeItemFromCustomer(Integer levelId, Integer customerId) throws Exception {
        //更新客户级别为null
//        Customer customer = new Customer();
//        customer.setId(customerId);
//        customer.setLevelId(null);
//        //update customer set level_id=null where id=?
//        int row1 = customerMapper.updateById(customer);

        UpdateWrapper updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("id",customerId);
        updateWrapper.set("level_id",null);
        int row1 = customerMapper.update(null,updateWrapper);

        //删除客户当前级别的所有护理项目
        UpdateWrapper updateWrapper1 = new UpdateWrapper<>();
        updateWrapper1.eq("customer_id",customerId);
        updateWrapper1.eq("level_id",levelId);
        updateWrapper1.set("is_deleted",1);
        int row2 = customerNurseItemMapper.delete(updateWrapper1);

        if(!(row1>0 && row2>0)){
            throw new Exception("删除失败");
        }
        return ResultVo.ok("删除成功");
    }

    @Override
    public ResultVo isIncludesItemCustomer(Integer customerId, Integer itemId) throws Exception {
        QueryWrapper queryWrapper = new QueryWrapper();

        queryWrapper.eq("customer_id", customerId);
        queryWrapper.eq("item_id", itemId);
        queryWrapper.eq("is_deleted",0);
        int row1 = customerMapper.selectCount(queryWrapper);
        if(row1>0){
            return ResultVo.fail("已存在");
        }
        return ResultVo.ok("配置了该项目");
    }

    @Override
    public ResultVo enewNurseItem(CustomerNurseItem customerNurseItem) throws Exception {
        customerNurseItemMapper.updateById(customerNurseItem);
        return ResultVo.ok("续费成功");

    }

    @Override
    public ResultVo removeCustomerItem(Integer id) throws Exception {
        CustomerNurseItem customerNurseItem = new CustomerNurseItem();
        customerNurseItem.setCustomerId(id);
        customerNurseItem.setIsDeleted(1);
        int row1 = customerNurseItemMapper.deleteById(customerNurseItem);
        if(!(row1 > 0)){
            throw new Exception("删除失败");
        }
        return ResultVo.ok("删除成功");
    }
}