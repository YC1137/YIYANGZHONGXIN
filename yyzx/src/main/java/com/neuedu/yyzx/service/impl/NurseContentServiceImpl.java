package com.neuedu.yyzx.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import com.neuedu.yyzx.dto.NurseItemDTO;
import com.neuedu.yyzx.mapper.NurseContentMapper;
import com.neuedu.yyzx.mapper.NurseLevelItemMapper;
import com.neuedu.yyzx.pojo.NurseContent;
import com.neuedu.yyzx.service.NurseContentService;
import com.neuedu.yyzx.utils.ResultVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class NurseContentServiceImpl extends ServiceImpl<NurseContentMapper, NurseContent> implements NurseContentService {
    @Autowired
    private NurseContentMapper nurseContentMapper;
    @Autowired
    private NurseLevelItemMapper nurseLevelItemMapper;

    @Override
    public ResultVo<List<NurseContent>> listNurseItemByLevel(Integer levelId) throws Exception {
        QueryWrapper qw = new QueryWrapper();
        qw.eq("level_id", levelId);
        qw.select("item_id");
        List<Integer> itemIds = nurseLevelItemMapper.selectObjs(qw);
        List<NurseContent> nurseContents = new ArrayList<>();
        //判断是否有记录
        if(itemIds.size()>0){
            //查询护理项目信息
            // 查询（根据ID 批量查询）
            nurseContents = nurseContentMapper.selectBatchIds(itemIds);
        }
        return ResultVo.ok(nurseContents);
    }

    @Override
    public ResultVo updateNurseItem(NurseContent nurseContent) throws Exception {
        //如果修改状态为停用，剔除护理基本护理项目列表中的对应记录，保证列表项目都是可用状态
        if(nurseContent.getStatus()==2){
            QueryWrapper qwCount = new QueryWrapper();
            qwCount.eq("item_id",nurseContent.getId());
            // 根据 Wrapper 条件，查询总记录数
            int count = nurseLevelItemMapper.selectCount(qwCount);
            if(count>0){
                QueryWrapper qw = new QueryWrapper();
                qw.eq("item_id",nurseContent.getId());
                int row = nurseLevelItemMapper.delete(qw);
                boolean temp = updateById(nurseContent);
                if(!(temp && row>0)){
                    throw new Exception("修改失败");
                }
                return ResultVo.ok("修改成功");
            }
        }
        updateById(nurseContent);
        return ResultVo.ok("更新成功");
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResultVo delNurseItem(Integer id) throws Exception {
        NurseContent nurseContent = new NurseContent();
        nurseContent.setIsDeleted(1);
        nurseContent.setId(id);
        //查询当前护理项目是否在护理级别护理项目列表中，如果在就需要进行剔除
        QueryWrapper qwCount = new QueryWrapper();
        qwCount.eq("item_id",id);
        int count = nurseLevelItemMapper.selectCount(qwCount);
        if(count>0){
            QueryWrapper qw = new QueryWrapper();
            qw.eq("item_id",id);
            int row = nurseLevelItemMapper.delete(qw);
            //更新的是nurseContent表
            boolean temp = updateById(nurseContent);
            if(!(temp && row>0)){
                throw new Exception("删除失败");
            }
            return ResultVo.ok("删除成功");
        }
        updateById(nurseContent);
        return ResultVo.ok("删除成功");
    }

    @Override
    public ResultVo<Page<NurseContent>> listNurseContent(NurseItemDTO nurseItemDTO) throws Exception {
        Page<NurseContent> page = new Page<>(nurseItemDTO.getPageNum(), 6);
        QueryWrapper qw = new QueryWrapper<>();
        if(nurseItemDTO.getName() != null && nurseItemDTO.getName() != ""){
            qw.like("nursing_name",nurseItemDTO.getName());
        }
        if(nurseItemDTO.getStatus() != null){
            qw.eq("status",nurseItemDTO.getStatus());
        }
        qw.eq("is_deleted",0);
        //select * from nurse_content where nursing_name like ? and status=? and is_deleted = 0
        page(page,qw);
        return ResultVo.ok(page);
    }

    @Override
    public ResultVo addNurseItem(NurseContent nurseContent) throws Exception {
        nurseContent.setIsDeleted(0);
        nurseContentMapper.insert(nurseContent);
        return ResultVo.ok("添加成功");
    }
}
