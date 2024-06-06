package com.example.common.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.groups.Default;

/**
 * @author diaoyn
 * @create 2024-03-29 15:14:34
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel("demo请求参数")
public class DemoVO {

    @NotBlank(message = "用户名称不能为空", groups = Default.class)
    @ApiModelProperty(value = "用户名称", example = "admin", required = true)
    private String userName;

    @NotBlank(message = "用户密码不能为空", groups = Default.class)
    @ApiModelProperty(value = "用户密码", example = "password", required = true)
    private String password;

    @ApiModelProperty(value = "来源", example = "server")
    private String source;

}
