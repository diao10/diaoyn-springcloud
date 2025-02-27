package com.ynqd.ai.controller;

import com.diaoyn.common.aspect.annotation.AutoLog;
import com.diaoyn.common.vo.ResponseVO;
import com.ynqd.ai.service.GradioService;
import com.ynqd.ai.vo.GradioGeneralVoiceVo;
import com.ynqd.ai.vo.rep.GeneralVoiceRepVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;


/**
 * @author diaoyn
 * @since 2024-11-11 14:37:48
 */
@RestController
@RequestMapping("/ai/gradio")
@Api(tags = "ai-gradio语音接口")
public class GradioController {

    @Resource
    private GradioService gradioService;


    @AutoLog(value = "生成语音文件")
    @PostMapping("/general")
    @ApiOperation("生成语音文件")
    public ResponseVO<List<GeneralVoiceRepVo>> general(@RequestBody GradioGeneralVoiceVo vo) {
        return ResponseVO.success(gradioService.generalVoice(vo.getReferencePath(), vo.getReferenceText(),
                vo.getTextList()));
    }
}

