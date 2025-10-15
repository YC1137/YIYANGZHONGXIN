package com.neuedu.yyzx.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;


@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="CustomerDTO-客户查询条件", description="")
public class CustomerDTO {
        @ApiModelProperty(value = "系统角色 1：管理员；2：健康管家")
        private Integer roleId;
        @ApiModelProperty(value = "页码")
        private Integer pageSize;
        @ApiModelProperty(value = "客户真实姓名")
        private String customerName;
        @ApiModelProperty(value = "客户类型")
        private Integer manType;

        @ApiModelProperty(value = "关联的管家用户ID")
        private Integer userId;

        @ApiModelProperty(value = "当前页码")
        private Integer currentPag;

}
