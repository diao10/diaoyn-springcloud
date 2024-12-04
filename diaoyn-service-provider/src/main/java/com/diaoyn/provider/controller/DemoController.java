package com.diaoyn.provider.controller;

import cn.hutool.core.date.DateUtil;
import com.diaoyn.common.handler.ApiModelPropertyPropertyBuilderJson;
import com.diaoyn.common.vo.DemoRepVO;
import com.diaoyn.common.vo.DemoVO;
import com.diaoyn.common.vo.ResponseVO;
import com.diaoyn.provider.aspect.annotation.AutoLog;
import com.diaoyn.provider.service.DemoService;
import com.diaoyn.provider.vo.rep.CommonRepVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyEmitter;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import javax.annotation.Resource;
import java.util.concurrent.CompletableFuture;

/**
 * @author diaoyn
 * @create 2024-03-29 15:00:41
 */
@RestController
@RequestMapping
@Api(tags = "demo服务端接口")
public class DemoController {

    @Resource
    DemoService demoService;

    @ApiOperation("测试用户")
    @PostMapping("/demo")
    @AutoLog(value = "/demo", name = "测试用户")
    public ResponseVO<DemoRepVO> demo(@RequestBody @Validated DemoVO vo) {
        return demoService.demo(vo);
    }


    @ApiOperation("超过3秒")
    @PostMapping("/sleep")
    public ResponseVO<DemoRepVO> sleep(@RequestBody @Validated DemoVO vo) {
        try {
            Thread.sleep(4000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        return demoService.demo(vo);
    }

    @ApiOperation("连续获取服务器时间")
    @GetMapping("/getTime/{clientId}")
    public ResponseBodyEmitter getTime(@PathVariable String clientId) {
        ResponseBodyEmitter emitter = demoService.getConn(clientId);
        CompletableFuture.runAsync(() -> {
            try {
                demoService.send(clientId);
            } catch (Exception e) {
                emitter.completeWithError(e);
                throw new RuntimeException("推送数据异常");
            }
        });
         return emitter;
    }

    @ApiOperation("适合服务推送")
    @GetMapping("/sendTime")
    public SseEmitter sendTime() {
        SseEmitter emitter = new SseEmitter();
        for (int i = 0; i < 20; i++) {
            try {
                emitter.send(DateUtil.now());
            } catch (Exception e) {
                emitter.completeWithError(e);
                throw new RuntimeException("推送数据异常");
            }

        }
        emitter.complete();
        return emitter;
    }

    /**
     * @see ApiModelPropertyPropertyBuilderJson
     */
    @ApiOperation(value = "展示swagger的response,隐藏自己写的请求头", response = CommonRepVo.class)
    @GetMapping("/showResponse")
    public ResponseVO<?> showResponse(@ApiParam(hidden = true) @RequestHeader("userId") String userId) {
        return null;
    }

}
