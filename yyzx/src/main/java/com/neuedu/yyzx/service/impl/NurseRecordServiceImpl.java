package com.neuedu.yyzx.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import com.neuedu.yyzx.dto.NurseRecordDTO;
import com.neuedu.yyzx.mapper.CustomerNurseItemMapper;
import com.neuedu.yyzx.mapper.NurseRecordMapper;
import com.neuedu.yyzx.pojo.CustomerNurseItem;
import com.neuedu.yyzx.pojo.NurseRecord;
import com.neuedu.yyzx.service.NurseRecordService;
import com.neuedu.yyzx.utils.ResultVo;
import com.neuedu.yyzx.vo.NurseRecordVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class NurseRecordServiceImpl extends ServiceImpl<NurseRecordMapper, NurseRecord> implements NurseRecordService {
    @Autowired
    private NurseRecordMapper nurseRecordMapper;
    @Autowired
    private CustomerNurseItemMapper customerNurseItemMapper;

    @Override
    public ResultVo<Page<NurseRecordVo>> selectNurseRecordVo(NurseRecordDTO nurseRecordDTO) throws Exception {
        Page<NurseRecordVo> page = new Page<>(nurseRecordDTO.getPageNum(),6);
        nurseRecordMapper.selectNurseRecordVo(page,nurseRecordDTO.getCustomerId());
        return ResultVo.ok(page);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public ResultVo addNurseRecord(NurseRecord nurseRecord) throws Exception {
        //生成护理记录
        nurseRecord.setIsDeleted(0);
        boolean temp = save(nurseRecord);
        //查询护理项目余量
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("item_id",nurseRecord.getItemId());
        queryWrapper.eq("customer_id",nurseRecord.getCustomerId());
        queryWrapper.eq("is_deleted",0);
        CustomerNurseItem customerNurseItem = customerNurseItemMapper.selectOne(queryWrapper);
        //修改护理项目余量
        UpdateWrapper updateWrapper = new UpdateWrapper();
        updateWrapper.set("nursing_count",customerNurseItem.getNurseNumber()-nurseRecord.getNursingCount());
        queryWrapper.eq("item_id",nurseRecord.getItemId());
        queryWrapper.eq("customer_id",nurseRecord.getCustomerId());
        queryWrapper.eq("is_deleted",0);
        int row = nurseRecordMapper.update(null,updateWrapper);
        if(!(temp && row>0)){
            throw new Exception("修改失败");
        }
        return ResultVo.ok("更新成功");
    }
}
