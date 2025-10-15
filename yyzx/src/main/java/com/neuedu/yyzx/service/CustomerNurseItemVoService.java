package com.neuedu.yyzx.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.neuedu.yyzx.dto.CustomerNurseItemDTO;
import com.neuedu.yyzx.pojo.CustomerNurseItem;
import com.neuedu.yyzx.utils.ResultVo;
import com.neuedu.yyzx.vo.CustomerNurseItemVo;


import java.util.List;

public interface CustomerNurseItemVoService extends IService<CustomerNurseItem>{
    ResultVo<Page<CustomerNurseItemVo>> selectCustomerNurseItemVo(CustomerNurseItemDTO customerNurseItemDTO)throws Exception;

    ResultVo addItemToCustomer(List<CustomerNurseItem> customerNurseItem)throws Exception;

    ResultVo removeItemFromCustomer(Integer levelId,Integer customerId)throws Exception;

    ResultVo isIncludesItemCustomer(Integer customerId, Integer itemId)throws Exception;

    ResultVo enewNurseItem(CustomerNurseItem customerNurseItem)throws Exception;

    ResultVo removeCustomerItem(Integer id)throws Exception;
}
