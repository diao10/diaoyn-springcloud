package com.ynqd.ai.controller;

import com.alibaba.fastjson.JSONObject;
import com.diaoyn.common.aspect.annotation.AutoLog;
import com.diaoyn.common.vo.ResponseVO;
import com.ynqd.ai.service.AnythingllmService;
import com.ynqd.ai.vo.AnythingChatVo;
import com.ynqd.ai.vo.AnythingSystemVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyEmitter;

import javax.annotation.Resource;


/**
 * @author diaoyn
 * @since 2024-11-11 14:37:48
 */
@RestController
@RequestMapping("/ai/anythingllm")
@Api(tags = "ai-anythingllm聊天接口")
public class AnythingllmController {

    @Resource
    private AnythingllmService anythingService;

    @AutoLog(value = "验证api")
    @GetMapping("/auth")
    @ApiOperation("验证api")
    public ResponseVO<JSONObject> auth() {
        return ResponseVO.success(anythingService.auth());
    }


    @AutoLog(value = "查看所有的系统设置")
    @GetMapping("/system")
    @ApiOperation("查看所有的系统设置")
    public ResponseVO<JSONObject> system() {
        return ResponseVO.success(anythingService.system());
    }


    @AutoLog(value = "修改llm的Ollama模型")
    @PostMapping("/system/update/ollama")
    @ApiOperation("修改llm的Ollama模型")
    public ResponseVO<JSONObject> systemUpdate(@RequestBody AnythingSystemVo vo) {
        return ResponseVO.success(anythingService.systemUpdateOllama(vo));
    }

    @AutoLog(value = "聊天")
    @PostMapping("/doc/chat")
    @ApiOperation("doc工作空间-聊天")
    public ResponseVO<JSONObject> chat(@RequestBody AnythingChatVo vo) {
        return ResponseVO.success(anythingService.chat(vo));
    }


    @AutoLog(value = "流式聊天")
    @PostMapping("/doc/stream-chat")
    @ApiOperation("doc工作空间-流式聊天")
    public ResponseBodyEmitter streamChat(@RequestBody AnythingChatVo vo) {
        return anythingService.streamChat(vo);
    }


    @AutoLog(value = "上传文档并解析")
    @PostMapping("/doc/upload")
    @ApiOperation("doc工作空间-上传文档并解析")
    public ResponseVO<JSONObject> documentUpload(@RequestBody MultipartFile file) {
        return ResponseVO.success(anythingService.documentUpload(file));
    }

}

