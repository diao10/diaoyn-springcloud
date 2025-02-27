package com.ynqd.ai.controller;

import com.alibaba.fastjson.JSONObject;
import com.diaoyn.common.aspect.annotation.AutoLog;
import com.diaoyn.common.vo.ResponseVO;
import com.ynqd.ai.service.OllamaService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;


/**
 * @author diaoyn
 * @since 2024-11-11 14:37:48
 */
@RestController
@RequestMapping("/ai/ollama")
@Api(tags = "ai-ollama接口")
public class OllamaController {

    @Resource
    private OllamaService ollamaService;

    @AutoLog(value = "列出可用的模型")
    @GetMapping("/tags")
    @ApiOperation("列出可用的模型")
    public ResponseVO<JSONObject> tags() {
        return ResponseVO.success(ollamaService.tags());
    }

}

