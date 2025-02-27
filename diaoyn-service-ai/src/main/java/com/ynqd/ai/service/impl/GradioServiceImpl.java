package com.ynqd.ai.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.date.StopWatch;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.http.HttpGlobalConfig;
import cn.hutool.http.HttpUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.ynqd.ai.config.GradioConfig;
import com.ynqd.ai.service.GradioService;
import com.ynqd.ai.vo.GradioQueueVo;
import com.ynqd.ai.vo.rep.GeneralVoiceRepVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;

/**
 * @author diaoyn
 * @ClassName GradioServiceImpl
 * @Date 2025/2/17 9:31
 */
@Service
@Lazy
@Slf4j
public class GradioServiceImpl implements GradioService {

    private static final String UPLOAD_API = "/upload";
    private static final String UPLOAD_PROGRESS_API = "/upload_progress";
    private static final String QUEUE_JOIN_API = "/queue/join";
    private static final String QUEUE_DATA_API = "/queue/data";

    private final GradioConfig gradioConfig;

    @Autowired
    public GradioServiceImpl(@Lazy GradioConfig gradioConfig) {
        this.gradioConfig = gradioConfig;
        HttpGlobalConfig.setTimeout(5 * 60 * 1000);
    }


    @Override
    public List<GeneralVoiceRepVo> generalVoice(String referencePath, String referenceText,
                                                List<String> textList) {
        List<GeneralVoiceRepVo> repVoList = new ArrayList<>();
        CollUtil.removeBlank(textList);
        if (StrUtil.isEmpty(referencePath) || StrUtil.isEmpty(referenceText) || CollUtil.isEmpty(textList)) {
            return repVoList;
        }
        log.info("-----------------------调取gradio生成语音开始--------------------");
        StopWatch watch = new StopWatch();
        watch.start();
        String uploadId = IdUtil.objectId();
        long fileSize = FileUtil.size(FileUtil.file(referencePath));
        JSONArray uploadResult = uploadReference(uploadId, referencePath);
        this.uploadProgressReference(uploadId);

        for (String content : textList) {
            String sessionHash = IdUtil.objectId();
            JSONObject joinResult = joinVoice(uploadResult.getString(0), fileSize, referenceText, content, sessionHash);
            if (!joinResult.containsKey("event_id")) {
                throw new RuntimeException("合成失败 :" + joinResult.getString("detail"));
            }
            String url = joinVoiceProgress(sessionHash, joinResult.getString("event_id"));
            String tmpFilePath =
                    FileUtil.getTmpDirPath() + "voice/" + content + IdUtil.simpleUUID() + "." + FileUtil.getSuffix(url);
            HttpUtil.downloadFile(url, tmpFilePath);
            GeneralVoiceRepVo repVo = new GeneralVoiceRepVo();
            repVo.setContent(content);
            repVo.setPath(tmpFilePath);
            repVoList.add(repVo);
        }
        watch.stop();
        log.info("-----------------------调取gradio生成语音结束,耗时：" + watch.getTotalTimeSeconds() + "--------------------");

        return repVoList;
    }

    @Override
    public List<GeneralVoiceRepVo> generalVoice(String referencePath, String referenceText, String text) {
        List<String> textList = new ArrayList<>();
        textList.add(text);
        return generalVoice(referencePath, referenceText, textList);
    }

    /**
     * 上传参考音频
     *
     * @param uploadId      上传id
     * @param referencePath 真人语音参考地址
     * @return JSONArray
     */
    private JSONArray uploadReference(String uploadId, String referencePath) {
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("files", FileUtil.file(referencePath));
        String result = HttpUtil.post(gradioConfig.getUrl() + UPLOAD_API + "?upload_id=" + uploadId, paramMap);
        log.info("---------------调取gradio上传参考音频结果--------------------" + result);
        return JSONArray.parseArray(result);
    }


    /**
     * 查询上传进度
     *
     * @param uploadId 上传id
     */
    private void uploadProgressReference(String uploadId) {
        String result = HttpUtil.get(gradioConfig.getUrl() + UPLOAD_PROGRESS_API + "?upload_id=" + uploadId);
        log.info("---------------调取gradio上传参考音频ing--------------------" + result);
    }

    /**
     * 合成音频
     *
     * @param path          参考音频地址
     * @param fileSize      参考音频大小
     * @param referenceText 参考文本
     * @param corpusContent 语料内容
     * @param sessionHash   会话id
     * @return String
     */
    private JSONObject joinVoice(String path, Long fileSize, String referenceText, String corpusContent,
                                 String sessionHash) {
        JSONObject meta = new JSONObject();
        meta.put("_type", "gradio.FileData");
        GradioQueueVo.DataVo dataVo = new GradioQueueVo.DataVo();
        dataVo.setPath(path);
        dataVo.setMeta(meta);
        dataVo.setMimeType("audio/wav");
        dataVo.setOrigName(FileUtil.getName(path));
        dataVo.setSize(fileSize);
        List<Object> data = new ArrayList<>();
        data.add(dataVo);
        data.add(referenceText);
        data.add(corpusContent);
        //是否移除静音
        data.add(false);
        //交叉淡出时长 (秒)
        data.add(0.15);
        //降噪步数
        data.add(32);
        //语速
        data.add(0.8);

        GradioQueueVo vo = new GradioQueueVo();
        vo.setData(data);
        vo.setSessionHash(sessionHash);
        String result = HttpUtil.post(gradioConfig.getUrl() + QUEUE_JOIN_API, JSON.toJSONString(vo));
        log.info("---------------调取gradio合成语音结果--------------------" + result);
        return JSON.parseObject(result);
    }


    /**
     * 查询合成进度
     *
     * @param sessionHash 会话id
     * @return String
     */
    private String joinVoiceProgress(String sessionHash, String eventId) {
        AtomicReference<String> downloadUrl = new AtomicReference<>("");
        String result = HttpUtil.get(gradioConfig.getUrl() + QUEUE_DATA_API + "?session_hash=" + sessionHash);
        log.info("---------------调取gradio合成语音ing--------------------" + result);
        for (String data : StrUtil.splitTrim(result, "\n")) {
            JSONObject jsonObject = JSON.parseObject(data.substring(5));
            if ("process_completed".equals(jsonObject.getString("msg"))
                    && eventId.equals(jsonObject.getString("event_id"))) {
                if (!jsonObject.getBoolean("success")) {
                    log.error("合成语音失败：{}", jsonObject.getString("title"));
                    break;
                }
                downloadUrl.set(jsonObject.getJSONObject("output").getJSONArray("data").getJSONObject(0)
                        .getString("url"));
            }
        }
        return downloadUrl.get();
    }
}



