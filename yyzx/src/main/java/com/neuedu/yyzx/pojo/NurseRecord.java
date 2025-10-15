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
@EqualsAndHashCode
@ApiModel(value = "NurseRecord对象", description = "这是NurseRecord实体对象")
@TableName("nurserecord")
public class NurseRecord implements Serializable {
    private static final long serialVersionUID = 1L;

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
}
