package com.neuedu.yyzx.controller;



import com.neuedu.yyzx.dto.NurseRecordDTO;
import com.neuedu.yyzx.pojo.NurseRecord;
import com.neuedu.yyzx.service.NurseRecordService;
import com.neuedu.yyzx.utils.ResultVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/nurseRecord")
@CrossOrigin
@Api(tags = "护理记录管理")
public class NurseRecordController {
    @Autowired
    private NurseRecordService nurseRecordService;

    @ApiOperation("查询护理记录")
    @GetMapping("/listNurseRecord")
    public ResultVo listNurseRecord(NurseRecordDTO nurseRecordDTO) throws Exception{
        return nurseRecordService.selectNurseRecordVo(nurseRecordDTO);
    }

    @ApiOperation("添加护理记录")
    @PostMapping("/addNurseRecord")
    public ResultVo addNurseRecord(NurseRecord nurseRecord) throws Exception{
        return nurseRecordService.addNurseRecord(nurseRecord);
    }

    @ApiOperation("删除护理记录")
    @GetMapping("/removeNurseRecord")
    public ResultVo removeNurseRecord(Integer id) throws Exception{
        NurseRecord nurseRecord = new NurseRecord();
        nurseRecord.setId(id);
        nurseRecord.setIsDeleted(1);
        nurseRecordService.updateById(nurseRecord);
        return ResultVo.ok("删除成功");
    }

//    public ResultVo queryOutwardVo(OutwardDTO outwardDTO) throws Exception{
//        return outwardService.selectOutwardVo(outwardDTO);
//    }
}
