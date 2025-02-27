package com.ynqd.ai.service.impl;

import cn.hutool.core.codec.Base64;
import cn.hutool.core.date.StopWatch;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.http.Header;
import cn.hutool.http.HttpGlobalConfig;
import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.ynqd.ai.config.AnythingllmConfig;
import com.ynqd.ai.enums.ChatModeEnum;
import com.ynqd.ai.service.AnythingllmService;
import com.ynqd.ai.vo.AnythingChatVo;
import com.ynqd.ai.vo.AnythingSystemVo;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyEmitter;

import java.io.File;
import java.io.IOException;


/**
 * @author diaoyn
 * @ClassName AnythingllmServiceImpl
 * @Date 2024/12/3 10:39
 */
@Slf4j
@Service
public class AnythingllmServiceImpl implements AnythingllmService {

    private final static String AUTH = "/v1/auth";

    private final static String SYSTEM = "/v1/system";
    private final static String SYSTEM_UPDATE_ENV = "/v1/system/update-env";


    private final static String WORKSPACE = "/v1/workspace";
    private final static String CHAT = "/chat";
    private final static String STREAM_CHAT = "/stream-chat";

    private final static String DOCUMENT = "/v1/document";
    private final static String DOCUMENT_UPLOAD = DOCUMENT + "/upload";


    private final AnythingllmConfig anythingllmConfig;

    @Autowired
    public AnythingllmServiceImpl(@Lazy AnythingllmConfig anythingllmConfig) {
        this.anythingllmConfig = anythingllmConfig;
        HttpGlobalConfig.setTimeout(5 * 60 * 1000);
    }


    @Override
    public JSONObject auth() {
        return sendGet(anythingllmConfig.getUrl() + AUTH);
    }

    @Override
    public JSONObject system() {
        return sendGet(anythingllmConfig.getUrl() + SYSTEM);
    }

    @Override
    public JSONObject systemUpdateOllama(AnythingSystemVo vo) {
        return sendPost(anythingllmConfig.getUrl() + SYSTEM_UPDATE_ENV, JSON.toJSONString(vo));
    }

    @Override
    @SneakyThrows
    public JSONObject documentUpload(MultipartFile file) {
        String tmpFilePath = FileUtil.normalize(FileUtil.getTmpDirPath() + StrUtil.SLASH + file.getOriginalFilename());
        File tmpFile = FileUtil.file(tmpFilePath);
        file.transferTo(tmpFile);
        String baseData = Base64.encode(tmpFile);
        AnythingChatVo.AttachmentVo attachmentVo = new AnythingChatVo.AttachmentVo();
        attachmentVo.setName(file.getOriginalFilename());
        attachmentVo.setMime("application/msword");
        attachmentVo.setContentString("data:application/msword;base64," + baseData);
        AnythingChatVo vo = new AnythingChatVo();
        vo.setModel(ChatModeEnum.chat.name());
        vo.setMessage("解析文档里的测试点");
        vo.getAttachments().add(attachmentVo);
        JSONObject result = this.chat(vo);
        return result;
    }

    @Override
    public JSONObject chat(AnythingChatVo vo) {
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        JSONObject result =
                sendPost(anythingllmConfig.getUrl() + WORKSPACE + "/" + anythingllmConfig.getSlug() + CHAT, vo);
        stopWatch.stop();
        log.info("===========chat time:{}s", stopWatch.getTotalTimeSeconds());
        return result;
    }

    @Override
    public ResponseBodyEmitter streamChat(AnythingChatVo vo) {
        vo.setModel(ChatModeEnum.chat.name());
        ResponseBodyEmitter emitter = new ResponseBodyEmitter();
        HttpRequest request =
                HttpUtil.createPost(anythingllmConfig.getUrl() + WORKSPACE + "/" + anythingllmConfig.getSlug() + STREAM_CHAT);
        request.header(Header.AUTHORIZATION, getAuthorization());
        String body = JSONUtil.toJsonStr(vo);
        request.body(body).then(response -> {
            try {
                emitter.send(response.body());
            } catch (IOException e) {
                emitter.completeWithError(e);
                throw new RuntimeException(e);
            }
        });
        emitter.complete();
        return emitter;
    }

    public String getAuthorization() {
        return "Bearer " + anythingllmConfig.getToken();
    }

    public JSONObject sendGet(String url) {
        HttpRequest request = HttpUtil.createGet(url);
        request.header(Header.AUTHORIZATION, getAuthorization());
        String body = request.execute().body();
        return JSONObject.parseObject(body);
    }

    public <T> JSONObject sendPost(String url, T t) {
        HttpRequest request = HttpUtil.createPost(url);
        request.header(Header.AUTHORIZATION, getAuthorization());
        System.out.println("JSON.toJSONString(t) = " + JSON.toJSONString(t));
        request.body(JSONUtil.toJsonStr(t));
        String body = request.execute().body();
        return JSONObject.parseObject(body);
    }


}
