package com.neuedu.yyzx.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

import com.neuedu.yyzx.dto.NurseItemDTO;
import com.neuedu.yyzx.pojo.NurseContent;
import com.neuedu.yyzx.utils.ResultVo;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface NurseContentService extends IService<NurseContent> {
    ResultVo<List<NurseContent>> listNurseItemByLevel(Integer levelId)throws Exception;

    ResultVo updateNurseItem(NurseContent nurseContent)throws Exception;

    ResultVo delNurseItem(Integer id)throws Exception;

    ResultVo<Page<NurseContent>> listNurseContent(NurseItemDTO nurseItemDTO)throws Exception;

    ResultVo addNurseItem(NurseContent nurseContent)throws Exception;
}
