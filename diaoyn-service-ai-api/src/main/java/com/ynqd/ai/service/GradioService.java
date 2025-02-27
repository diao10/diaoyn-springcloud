package com.ynqd.ai.service;

import com.ynqd.ai.vo.rep.GeneralVoiceRepVo;

import java.util.List;

/**
 * @author diaoyn
 * @ClassName GradioService
 * @Date 2025/2/17 9:31
 */
public interface GradioService {


    /**
     * 生成语音
     *
     * @param referencePath 真人语音参考地址
     * @param referenceText 与真人参考因对对应的参考文本
     * @param textList      文本内容列表
     * @return List<String>
     */
    List<GeneralVoiceRepVo> generalVoice(String referencePath, String referenceText, List<String> textList);

    /**
     * 生成语音
     *
     * @param referencePath 真人语音参考地址
     * @param referenceText 与真人参考因对对应的参考文本
     * @param text          文本内容
     * @return List<String>
     */
    List<GeneralVoiceRepVo> generalVoice(String referencePath, String referenceText, String text);


}
