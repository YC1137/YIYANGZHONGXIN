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
@ApiModel(value = "NurseLevel对象", description = "这是NurseLevel实体对象")
@TableName("nurse_level")
@EqualsAndHashCode
public class NurseLevel implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键")
    @TableId(value = "id",type= IdType.AUTO)
    private Integer id;
    @ApiModelProperty(value = "护理等级名称")
    private String levelName;
    @ApiModelProperty(value = "护理等级状态 1启用 2禁用")
    private Integer levelStatus;
    @ApiModelProperty(value = "逻辑删除标记")
    private Integer isDeleted;
}
