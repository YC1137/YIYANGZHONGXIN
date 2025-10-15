package com.neuedu.yyzx.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode
@ApiModel(value = "NurseItemDTO-护理项目查询条件", description = "这是NurseItemDTO对象")
public class NurseItemDTO {
    @ApiModelProperty(value = "页码")
    private Integer pageNum;
    @ApiModelProperty(value = "名称")
    private String name;
    @ApiModelProperty(value = "状态 1启用 2停用")
    private Integer status;
}
