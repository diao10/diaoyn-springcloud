package com.ynqd.ai.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @author diaoyn
 * @ClassName AnythingChatVo
 * @Date 2024/12/3 13:49
 */
@Data
@ApiModel(description = "聊天请求Vo")
public class AnythingChatVo implements Serializable {

    private static final long serialVersionUID = 1L;


    @ApiModelProperty(value = "输入信息")
    private String message;

    @ApiModelProperty(value = "")
    private String model;

    @ApiModelProperty(value = "")
    private String sessionId;

    @ApiModelProperty(value = "")
    private List<AttachmentVo> attachments = new ArrayList<>();


    @Data
    public static class AttachmentVo {
        @ApiModelProperty(value = "名称")
        private String name;

        @ApiModelProperty(value = "格式")
        private String mime;

        @ApiModelProperty(value = "base64编码")
        private String contentString;
    }
}
