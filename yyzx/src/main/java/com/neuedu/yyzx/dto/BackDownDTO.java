package com.neuedu.yyzx.dto ;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode
@ApiModel(value = "BackDownDTO-退住查询条件", description = "这是BackDownDTO对象")
public class BackDownDTO {
    @ApiModelProperty(value = "页码")
    private Integer pageNum;
    @ApiModelProperty(value = "用户编号")
    private Integer userId;
}
