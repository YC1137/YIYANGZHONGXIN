package com.neuedu.yyzx.service.impl;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.neuedu.yyzx.dto.OutwardDTO;
import com.neuedu.yyzx.mapper.CustomerMapper;
import com.neuedu.yyzx.mapper.OutwardMapper;
import com.neuedu.yyzx.pojo.Customer;
import com.neuedu.yyzx.pojo.Outward;

import com.neuedu.yyzx.service.OutwardService;
import com.neuedu.yyzx.utils.ResultVo;
import com.neuedu.yyzx.vo.OutwardVo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;


@Service
public class OutwardServiceImpl extends ServiceImpl<OutwardMapper, Outward> implements OutwardService {
    @Resource
    private OutwardMapper outwardMapper;
    @Transactional(rollbackFor = Exception.class)
    /**
     * 审批登记（护工申请）
     */
    @Override
    public ResultVo examineOutward(Outward outward) throws Exception {
        UpdateWrapper<Outward> updateWrapper = new UpdateWrapper<Outward>();
        updateWrapper.eq("id",outward.getId());
        updateWrapper.set("auditstatus",outward.getAuditstatus());
        outwardMapper.update(outward,updateWrapper);
        return ResultVo.ok("审批成功");
    }

    /**
     *
     * @param outwardDTO
     * @return
     * @throws Exception
     */
    @Override
    public ResultVo<Page<OutwardVo>> queryOutwardVo(OutwardDTO outwardDTO) throws Exception {
        Page<OutwardVo> page=new Page<>(outwardDTO.getPageSize(),6);
        outwardMapper.selectOutwardVo(page,outwardDTO.getUserId());
        return ResultVo.ok(page);
    }

}
