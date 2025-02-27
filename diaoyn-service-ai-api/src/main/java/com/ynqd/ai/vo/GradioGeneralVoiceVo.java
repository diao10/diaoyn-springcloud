package com.ynqd.ai.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author diaoyn
 * @ClassName GradioGeneralVoiceVo
 * @Date 2025/2/19 14:26
 */
@Data
@ApiModel(description = "生成语音请求Vo")
public class GradioGeneralVoiceVo implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "真人语音参考地址", example = "C:\\Users\\EDY\\Desktop\\钉钉文件\\打开环境舒适设置1716878743139.wav")
    private String referencePath;

    @ApiModelProperty(value = "与真人参考因对对应的参考文本", example = "打开环境舒适设置")
    private String referenceText;

    @ApiModelProperty(value = "文本内容", example = "[\"关闭环境舒适设置\", \"结束环境舒适设置\"]", dataType = "array")
    private List<String> textList;
}
