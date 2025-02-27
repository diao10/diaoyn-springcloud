package com.ynqd.ai.service.impl;

import cn.hutool.http.HttpGlobalConfig;
import cn.hutool.http.HttpUtil;
import com.alibaba.fastjson.JSONObject;
import com.ynqd.ai.config.OllamaConfig;
import com.ynqd.ai.service.OllamaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

/**
 * @author diaoyn
 * @ClassName OllamaServiceImpl
 * @Date 2025/2/27 15:29
 */
@Service
public class OllamaServiceImpl implements OllamaService {

    private final OllamaConfig ollamaConfig;

    private final static String TAGS = "/api/tags";


    @Autowired
    public OllamaServiceImpl(@Lazy OllamaConfig ollamaConfig) {
        this.ollamaConfig = ollamaConfig;
        HttpGlobalConfig.setTimeout(5 * 60 * 1000);
    }

    @Override
    public JSONObject tags() {
        String result = HttpUtil.get(ollamaConfig.getUrl() + TAGS);
        return JSONObject.parseObject(result);
    }
}
