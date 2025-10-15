package com.neuedu.yyzx.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

@Data
@ApiModel(value = "NurseContent对象", description = "这是NurseContent实体对象")
@EqualsAndHashCode
@TableName("nursecontent")
public class NurseContent implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键")
    @TableId(value = "id",type= IdType.AUTO)
    private Integer id;

    @ApiModelProperty(value = "编号")
    private String serialNumber;
    @ApiModelProperty(value = "名称")
    private String nursingName;
    @ApiModelProperty(value = "价格")
    private String servicePrice;
    @ApiModelProperty(value = "描述")
    private String message;
    @ApiModelProperty(value = "状态 1启用 2停用")
    private Integer status;
    @ApiModelProperty(value = "执行周期")
    private String executionCycle;
    @ApiModelProperty(value = "执行次数")
    private String executionTimes;
    @ApiModelProperty(value = "逻辑删除标记")
    private Integer isDeleted;
}
