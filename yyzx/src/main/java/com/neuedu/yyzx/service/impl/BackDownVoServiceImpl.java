package com.neuedu.yyzx.service.impl;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;


import com.neuedu.yyzx.dto.BackDownDTO;
import com.neuedu.yyzx.mapper.BackDownMapper;
import com.neuedu.yyzx.pojo.BackDown;
import com.neuedu.yyzx.service.BackDownService;
import com.neuedu.yyzx.utils.ResultVo;
import com.neuedu.yyzx.vo.BackDownVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class BackDownVoServiceImpl extends ServiceImpl<BackDownMapper, BackDown> implements BackDownService {
    @Autowired
    private BackDownMapper backDownMapper;

    @Override
    public ResultVo<Page<BackDownVo>> selectBackDownVo(BackDownDTO backDownDTO) throws Exception {
        Page<BackDownVo> page = new Page<>(backDownDTO.getPageNum(), 6);
        backDownMapper.selectBackDownVo(page, backDownDTO.getUserId());
        return ResultVo.ok(page);
    }

    @Override
    public ResultVo examineBackDown(BackDown backDown) throws Exception {
        UpdateWrapper<BackDown> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("id", backDown.getId());
        //状态
        updateWrapper.set("auditstatus",1);
        //时间
        updateWrapper.set("audittime",backDown.getAudittime());
        //人
        updateWrapper.set("auditperson",backDown.getAuditperson());
        int row = backDownMapper.update(backDown,updateWrapper);
        if(!(row>0)){
            throw new Exception("审核失败");
        }
        return ResultVo.ok("审核成功");
    }

    @Override
    public ResultVo delBackDown(Integer id) throws Exception {
        BackDown backDown = new BackDown();
        backDown.setId(id);
        backDown.setIsDeleted(1);
        int row = backDownMapper.updateById(backDown);
        if(!(row>0)){
            throw new Exception("删除失败");
        }
        return ResultVo.ok("删除成功");
    }
}
