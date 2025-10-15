package com.neuedu.yyzx.vo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.Date;

@Data
@EqualsAndHashCode
@ApiModel(value = "BackDownVo对象", description = "这是退住视图对象")
public class BackDownVo implements Serializable {

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

    //bedDetail
    @ApiModelProperty(value = "床位详情")
    private String bedDetails;
    @ApiModelProperty(value = "床位ID")
    private Integer bedId;

    //customer
    @ApiModelProperty(value = "客户姓名")
    private String customerName;
    @ApiModelProperty(value = "入住时间")
    private java.sql.Date checkInDate;
}
