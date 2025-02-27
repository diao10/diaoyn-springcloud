package com.ynqd.ai.service;

import com.alibaba.fastjson.JSONObject;
import com.ynqd.ai.vo.AnythingChatVo;
import com.ynqd.ai.vo.AnythingSystemVo;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyEmitter;

/**
 * @author diaoyn
 * @ClassName AnythingllmService
 * @Date 2024/12/3 10:39
 */
public interface AnythingllmService {

    JSONObject auth();

    JSONObject system();

    JSONObject systemUpdateOllama(AnythingSystemVo vo);

    JSONObject documentUpload(MultipartFile file);

    JSONObject chat(AnythingChatVo vo);

    ResponseBodyEmitter streamChat(AnythingChatVo vo);

}
