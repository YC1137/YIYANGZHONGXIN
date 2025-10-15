package com.neuedu.yyzx.service.impl;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.neuedu.yyzx.mapper.NurseLevelItemMapper;
import com.neuedu.yyzx.mapper.NurseLevelMapper;
import com.neuedu.yyzx.pojo.NurseLevel;
import com.neuedu.yyzx.pojo.NurseLevelItem;
import com.neuedu.yyzx.service.NurseLevelItemService;
import com.neuedu.yyzx.utils.ResultVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class NurseLevelItemServiceImpl extends ServiceImpl<NurseLevelItemMapper, NurseLevelItem> implements NurseLevelItemService {
    @Autowired
    NurseLevelItemMapper nurseLevelItemMapper;

    @Autowired
    NurseLevelMapper nurseLevelMapper;

    @Override
    public ResultVo addItemToLevel(NurseLevelItem nurseLevelItem) {
        QueryWrapper qw = new QueryWrapper<>();
        qw.eq("level_id",nurseLevelItem.getLevelId());
        qw.eq("item_id",nurseLevelItem.getItemId());
        int row = nurseLevelItemMapper.selectCount(qw);
        if(row>0){
            return ResultVo.fail("该护理等级项目已存在");
        }
        nurseLevelItemMapper.insert(nurseLevelItem);
        return ResultVo.ok("添加成功");
    }

    @Override
    public ResultVo removeNurseLevelItem(Integer levelId, Integer itemId) {
        QueryWrapper qw = new QueryWrapper<>();
        qw.eq("level_id",levelId);
        qw.eq("item_id",itemId);
        int row = nurseLevelItemMapper.delete(qw);
        if(row==0){
            return ResultVo.fail("删除失败");
        }
        return ResultVo.ok("删除成功");
    }

    @Override
    public ResultVo addNurseLevel(NurseLevel nurseLevel) {
        nurseLevelMapper.insert(nurseLevel);
        return ResultVo.ok("添加成功");
    }

    @Override
    public ResultVo updateNurseLevel(NurseLevel nurseLevel) {
        nurseLevelMapper.updateById(nurseLevel);
        return ResultVo.ok("更新成功");
    }

    @Override
    public ResultVo delNurseLevel(Integer id) {
        nurseLevelMapper.deleteById(id);
        return ResultVo.ok("删除成功");
    }



}
