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
@ApiModel(value = "NurseRecodeVo对象", description = "这是护理记录视图对象")
public class  NurseRecordVo implements Serializable {

    @ApiModelProperty(value = "主键")
    @TableId(value = "id",type= IdType.AUTO)
    private Integer id;

    @ApiModelProperty(value = "逻辑删除标记")
    private Integer isDeleted;
    @ApiModelProperty(value = "客户ID")
    private Integer customerId;
    @ApiModelProperty(value = "护理项目ID")
    private Integer itemId;
    @ApiModelProperty(value = "护理时间")
    private Date nursingTime;
    @ApiModelProperty(value = "护理内容")
    private String nursingContent;
    @ApiModelProperty(value = "护理次数")
    private Integer nursingCount;
    @ApiModelProperty(value = "护理人员ID")
    private Integer userId;

    //NurseContent
    @ApiModelProperty(value = "编号")
    private String serialNumber;
    @ApiModelProperty(value = "名称")
    private String nursingName;
}
