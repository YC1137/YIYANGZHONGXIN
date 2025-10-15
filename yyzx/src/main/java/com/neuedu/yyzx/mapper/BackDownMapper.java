package com.neuedu.yyzx.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import com.neuedu.yyzx.pojo.BackDown;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface BackDownMapper extends BaseMapper<BackDown> {
    Page<com.neuedu.yyzx.vo.BackDownVo> selectBackDownVo(@Param("page") Page<com.neuedu.yyzx.vo.BackDownVo> page,
                                                   @Param("userId") Integer userId);
}
