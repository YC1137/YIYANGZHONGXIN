package com.neuedu.yyzx.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

import com.neuedu.yyzx.pojo.NurseLevel;
import com.neuedu.yyzx.pojo.NurseLevelItem;
import com.neuedu.yyzx.service.NurseLevelItemService;
import com.neuedu.yyzx.service.NurseLevelService;
import com.neuedu.yyzx.utils.ResultVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/nurseLevelItem")
@CrossOrigin
@Api(tags = "护理等级项目管理")
public class NurseLevelItemController {
    @Autowired
    private NurseLevelItemService nurseLevelItemService;
    @Autowired
    private NurseLevelService nurseLevelService;

    @ApiOperation("添加护理等级项目")
    @PostMapping("/addNurseLevel")
    public ResultVo addNurseLevel(NurseLevel nurseLevel) throws Exception {
        return nurseLevelItemService.addNurseLevel(nurseLevel);
    }


    @ApiOperation("更新护理等级项目")
    @PostMapping("/updateNurseLevel")
    public ResultVo updateNurseLevel(NurseLevel nurseLevel) throws Exception {
        return nurseLevelItemService.updateNurseLevel(nurseLevel);
    }

    @ApiOperation("删除护理等级项目")
    @GetMapping("/removeNurseLevel")
    public ResultVo delNurseLevel(Integer id) throws Exception {
        return nurseLevelItemService.delNurseLevel(id);
    }

    @ApiOperation("查询护理等级项目")
    @GetMapping("/listNurseLevel")
    public ResultVo listNurseLevel(NurseLevel nurseLevel) throws Exception {
        QueryWrapper qw = new QueryWrapper<>();
        if(nurseLevelService.list() != null){
            qw.eq("level_status",nurseLevel.getLevelStatus());
        }
        List<NurseLevel> resultList = nurseLevelService.list(qw);
        System.out.println(resultList);
        return ResultVo.ok(nurseLevelService.list(qw));
    }

    @ApiOperation("护理项目的配置")
    @PostMapping("/addItemToLevel")
    public ResultVo addItemToLevel(NurseLevelItem nurseLevelItem) throws Exception {
        return nurseLevelItemService.addItemToLevel(nurseLevelItem);
    }

    @ApiOperation("删除护理级别中的护理项目")
    @GetMapping("/removeNurseLevelItem")
    public ResultVo removeNurseLevelItem(Integer levelId, Integer itemId) throws Exception {
        return nurseLevelItemService.removeNurseLevelItem(levelId, itemId);
    }

}
