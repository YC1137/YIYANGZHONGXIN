package com.neuedu.yyzx.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import com.neuedu.yyzx.pojo.NurseRecord;
import com.neuedu.yyzx.vo.NurseRecordVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface NurseRecordMapper extends BaseMapper<NurseRecord> {
    Page<NurseRecordVo> selectNurseRecordVo(@Param("page") Page<NurseRecordVo> page,
                                            @Param("customerId") Integer customerId);
}
