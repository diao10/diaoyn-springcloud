package com.diaoyn.provider.vo.rep;

import com.alibaba.fastjson.annotation.JSONField;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author diaoyn
 * @ClassName MessageVo
 * @Date 2024/9/24 13:57
 */
@Data
@ApiModel(description = "socketio--公共类")
public class MessageVo<T> implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("消息类型")
    @JSONField(name = "message_id")
    private String messageId;

    @ApiModelProperty("消息数据")
    @JSONField(name = "message_data")
    private T messageData;

}
