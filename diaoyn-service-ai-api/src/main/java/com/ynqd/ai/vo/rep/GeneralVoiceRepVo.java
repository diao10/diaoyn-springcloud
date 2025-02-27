package com.ynqd.ai.vo.rep;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author diaoyn
 * @ClassName GeneralVoiceRepVo
 * @Date 2025/2/19 16:49
 */
@Data
@ApiModel(description = "生成语音返回Vo")
public class GeneralVoiceRepVo {

    @ApiModelProperty(value = "语料文本内容")
    private String content;

    @ApiModelProperty(value = "语音对应路径")
    private String path;

}
