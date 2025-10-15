package com.neuedu.yyzx.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@ApiModel(value = "NurseRecordDTO-护理记录查询条件", description = "这是NurseRecordDTO对象")
@EqualsAndHashCode
public class NurseRecordDTO {
    @ApiModelProperty(value = "页码")
    private Integer pageNum;
    @ApiModelProperty(value = "客户编号")
    private Integer customerId;
}
