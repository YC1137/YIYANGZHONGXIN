package com.neuedu.yyzx.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.neuedu.yyzx.pojo.NurseLevel;
import com.neuedu.yyzx.pojo.NurseLevelItem;
import com.neuedu.yyzx.utils.ResultVo;


public interface NurseLevelItemService extends IService<NurseLevelItem> {
    ResultVo addItemToLevel(NurseLevelItem nurseLevelItem);

    ResultVo removeNurseLevelItem(Integer levelId,Integer id);

    ResultVo addNurseLevel(NurseLevel nurseLevel);

    ResultVo updateNurseLevel(NurseLevel nurseLevel);

    ResultVo delNurseLevel(Integer id);
}
