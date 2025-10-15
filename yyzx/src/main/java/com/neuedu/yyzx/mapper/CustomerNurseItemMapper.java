package com.neuedu.yyzx.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import com.neuedu.yyzx.pojo.CustomerNurseItem;
import com.neuedu.yyzx.vo.CustomerNurseItemVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface CustomerNurseItemMapper extends BaseMapper<CustomerNurseItem> {
    Page<CustomerNurseItemVo> selectCustomerNurseItemVo(@Param("page") Page<CustomerNurseItemVo> page,
                                                        @Param("customerId") Integer customerId) throws Exception;
}
