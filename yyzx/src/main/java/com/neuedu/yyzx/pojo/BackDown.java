package com.neuedu.yyzx.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.Date;

@Data
@ApiModel(value = "BackDown对象", description = "这是BackDown实体对象")
@EqualsAndHashCode
@TableName("backdown")
public class BackDown implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键")
    @TableId(value = "id",type= IdType.AUTO)
    private Integer id;

    @ApiModelProperty(value = "备注")
    private String remarks;
    @ApiModelProperty(value = "逻辑删除标记")
    private Integer isDeleted;
    @ApiModelProperty(value = "客户ID")
    private Integer customerId;
    @ApiModelProperty(value = "退住时间")
    private Date retreattime;
    @ApiModelProperty(value = "退住方式 0正常退住 1死亡退住 2保留床位")
    private Integer retreattype;
    @ApiModelProperty(value = "退住事由")
    private String retreatreason;
    @ApiModelProperty(value = "审核状态 0提交1同意2拒绝")
    private Integer auditstatus;
    @ApiModelProperty(value = "审核人")
    private String auditperson;
    @ApiModelProperty(value = "审核时间")
    private Date audittime;
}
