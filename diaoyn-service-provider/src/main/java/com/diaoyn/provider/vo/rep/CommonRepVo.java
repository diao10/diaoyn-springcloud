package com.diaoyn.provider.vo.rep;

import com.alibaba.fastjson.annotation.JSONField;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.List;

/**
 * 仅作为展示swagger模型使用，不作为实际返回值
 *
 * @author diaoyn
 * @ClassName CanDisconnectVo
 * @Date 2024/9/24 15:39
 */

@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel(description = "CommonRepVo返回Vo")
public class CommonRepVo extends MessageVo<CommonRepVo.RepVo> implements Serializable {

    @Data
    public static class RepVo implements Serializable {
        private static final long serialVersionUID = 1L;

        @ApiModelProperty("items列表")
        @JSONField(name = "items")
        private List<ItemsVo> items;

        @Data
        public static class ItemsVo implements Serializable {
            @ApiModelProperty("错误码")
            @JSONField(name = "item_code")
            private String itemCode;

            @ApiModelProperty("信息")
            @JSONField(name = "item_message")
            private String itemMessage;
        }

    }


}
