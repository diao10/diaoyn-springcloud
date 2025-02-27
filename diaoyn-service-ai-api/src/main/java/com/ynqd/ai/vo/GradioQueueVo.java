package com.ynqd.ai.vo;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author diaoyn
 * @ClassName GradioQueueVo
 * @Date 2025/2/17 9:38
 */
@Data
@ApiModel(description = "语音生成请求Vo")
public class GradioQueueVo implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("data")
    @JSONField(name = "data")
    @JsonProperty("data")
    private List<Object> data;

    @ApiModelProperty("session_hash")
    @JSONField(name = "session_hash")
    @JsonProperty("session_hash")
    private String sessionHash;

    @ApiModelProperty("fn_index")
    @JSONField(name = "fn_index")
    @JsonProperty("fn_index")
    private int fnIndex = 0;

    @ApiModelProperty("trigger_id")
    @JSONField(name = "trigger_id")
    @JsonProperty("trigger_id")
    private int triggerId = 17;

    @ApiModelProperty("event_data")
    @JSONField(name = "event_data")
    @JsonProperty("event_data")
    private String eventData;

    @Data
    public static class DataVo {

        @ApiModelProperty("path")
        @JSONField(name = "path")
        @JsonProperty("path")
        private String path;

        @ApiModelProperty("size")
        @JSONField(name = "size")
        @JsonProperty("size")
        private Long size;

        @ApiModelProperty("meta")
        @JSONField(name = "meta")
        @JsonProperty("meta")
        private JSONObject meta;

        @ApiModelProperty("mime_type")
        @JSONField(name = "mime_type")
        @JsonProperty("mime_type")
        private String mimeType;

        @ApiModelProperty("orig_name")
        @JSONField(name = "orig_name")
        @JsonProperty("orig_name")
        private String origName;


    }
}
