package com.neuedu.yyzx.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode
@ApiModel(value = "CustomerNurseItemDTO-客户护理项目查询条件", description = "这是CustomerNurseItemDTO对象")
public class CustomerNurseItemDTO {
    @ApiModelProperty(value = "页码")
    private Integer pageNum;
    @ApiModelProperty(value = "客户编号")
    private Integer customerId;
}
