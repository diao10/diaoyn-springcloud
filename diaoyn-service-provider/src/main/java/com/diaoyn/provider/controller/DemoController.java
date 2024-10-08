package com.diaoyn.provider.controller;

import com.diaoyn.common.handle.ApiModelPropertyPropertyBuilderJson;
import com.diaoyn.common.vo.DemoRepVO;
import com.diaoyn.common.vo.DemoVO;
import com.diaoyn.common.vo.ResponseVO;
import com.diaoyn.provider.aspect.AutoLog;
import com.diaoyn.provider.service.DemoService;
import com.diaoyn.provider.vo.rep.CommonRepVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
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
    @GetMapping("/getTime")
    public SseEmitter getTime(String clientId) {
        SseEmitter emitter = demoService.getConn(clientId);
        CompletableFuture.runAsync(() -> {
            try {
                demoService.send(clientId);
            } catch (Exception e) {
                throw new RuntimeException("推送数据异常");
            }
        });
        return emitter;
    }

    /**
     * @see ApiModelPropertyPropertyBuilderJson
     */
    @ApiOperation(value = "仅展示swagger的response", response = CommonRepVo.class)
    @GetMapping("/showResponse")
    public ResponseVO<?> showResponse() {
        return null;
    }

}
