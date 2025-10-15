package com.neuedu.yyzx.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
;
;
import com.neuedu.yyzx.dto.BackDownDTO;
import com.neuedu.yyzx.pojo.BackDown;
import com.neuedu.yyzx.utils.ResultVo;
import com.neuedu.yyzx.vo.BackDownVo;

public interface BackDownService extends IService<BackDown> {
    ResultVo<Page<BackDownVo>> selectBackDownVo(BackDownDTO backDownDTO) throws Exception;

    ResultVo examineBackDown(BackDown backDown) throws Exception;

    ResultVo delBackDown(Integer id) throws Exception;
}
