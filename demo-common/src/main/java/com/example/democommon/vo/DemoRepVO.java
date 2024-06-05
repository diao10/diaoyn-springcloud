package com.example.democommon.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @author diaoyn
 * @create 2024-03-29 17:17:10
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel("demo返回参数")
public class DemoRepVO {

    @ApiModelProperty(value = "用户名称")
    private String name;

    @ApiModelProperty(value = "描述")
    private String desc;

    @ApiModelProperty(value = "创建时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createDate;
}
