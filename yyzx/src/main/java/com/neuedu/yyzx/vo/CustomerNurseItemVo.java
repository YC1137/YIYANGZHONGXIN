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
@ApiModel(value = "CustomerNurseItemVo对象", description = "这是CustomerNurseItem视图对象")
public class CustomerNurseItemVo implements Serializable {

    @ApiModelProperty(value = "主键")
    @TableId(value = "id",type= IdType.AUTO)
    private Integer id;

    @ApiModelProperty(value = "护理项目编号")
    private Integer itemId;
    @ApiModelProperty(value = "客户编号")
    private Integer customerId;
    @ApiModelProperty(value = "护理等级编号")
    private Integer levelId;
    @ApiModelProperty(value = "购买数量")
    private Integer nurseNumber;
    @ApiModelProperty(value = "逻辑删除标记")
    private Integer isDeleted;
    @ApiModelProperty(value = "服务购买时间")
    private Date buyTime;
    @ApiModelProperty(value = "服务到期时间")
    private Date maturityTime;

    //Customer
    @ApiModelProperty(value = "客户姓名")
    private String customerName;

    //NurseContent
    @ApiModelProperty(value = "编号")
    private String serialNumber;
    @ApiModelProperty(value = "名称")
    private String nursingName;
    @ApiModelProperty(value = "价格")
    private String servicePrice;
}
