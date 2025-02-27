package com.ynqd.ai.vo;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import java.io.Serializable;

/**
 * @author diaoyn
 * @ClassName AnythingSystemVo
 * @Date 2025/2/27 15:38
 */
@Data
@ApiModel(description = "修改ollama模型Vo")
public class AnythingSystemVo implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "设置LLMProvider", example = "ollama", required = true)
    @JSONField(name = "LLMProvider")
    @JsonProperty("lLMProvider")
    @NotEmpty(message = "设置LLMProvider不能为空")
    private String lLMProvider;

    @ApiModelProperty(value = "ollama地址", example = "http://192.168.0.235:11434", required = true)
    @JSONField(name = "OllamaLLMBasePath")
    @JsonProperty("ollamaLLMBasePath")
    @NotEmpty(message = "ollama地址不能为空")
    private String ollamaLLMBasePath;

    @ApiModelProperty(value = "模型名称", example = "deepseek-r1:14b", required = true)
    @JSONField(name = "OllamaLLMModelPref")
    @JsonProperty("ollamaLLMModelPref")
    @NotEmpty(message = "模型名称不能为空")
    private String ollamaLLMModelPref;

    @ApiModelProperty(value = "上下文响应的最大token", example = "4096", required = true)
    @JSONField(name = "OllamaLLMTokenLimit")
    @JsonProperty("ollamaLLMTokenLimit")
    @NotEmpty(message = "上下文响应的最大token不能为空")
    private String ollamaLLMTokenLimit;

    @ApiModelProperty(value = "ollama保持时长(s)", example = "300", required = true)
    @JSONField(name = "OllamaLLMKeepAliveSeconds")
    @JsonProperty("ollamaLLMKeepAliveSeconds")
    @NotEmpty(message = "ollama保持时长不能为空")
    private String ollamaLLMKeepAliveSeconds;

    @ApiModelProperty(value = "性能模式", example = "base", required = true)
    @JSONField(name = "OllamaLLMPerformanceMode")
    @JsonProperty("ollamaLLMPerformanceMode")
    @NotEmpty(message = "性能模式不能为空")
    private String ollamaLLMPerformanceMode;
}
