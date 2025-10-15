package com.neuedu.yyzx.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import com.neuedu.yyzx.dto.NurseItemDTO;
import com.neuedu.yyzx.pojo.NurseContent;
import com.neuedu.yyzx.service.NurseContentService;
import com.neuedu.yyzx.utils.ResultVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/nurseContent")
@CrossOrigin
@Api(tags = "护理项目管理")
public class NurseContentController {

    @Autowired
    private NurseContentService nurseContentService;

    @ApiOperation("查询护理项目列表")
    @GetMapping("/findNurseItemPage")
    public ResultVo<Page<NurseContent>> listNurseContent(NurseItemDTO nurseItemDTO)throws Exception{
        return nurseContentService.listNurseContent(nurseItemDTO);
    }

    @ApiOperation("添加护理项目")
    @PostMapping("/addNurseItem")
    public ResultVo addNurseItem(NurseContent nurseContent)throws Exception{
        return nurseContentService.addNurseItem(nurseContent);
    }

    @ApiOperation("修改护理项目")
    @PostMapping("/updateNurseItem")
    public ResultVo updateNurseItem(NurseContent nurseContent)throws Exception{
        return nurseContentService.updateNurseItem(nurseContent);
    }

    @ApiOperation("删除护理项目")
    @GetMapping("/delNurseItem")
    public ResultVo delNurseItem(Integer id)throws Exception{
        return nurseContentService.delNurseItem(id);
    }

}
