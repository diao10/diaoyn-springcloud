package com.diaoyn.provider.controller;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.io.IoUtil;
import cn.hutool.core.util.URLUtil;
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
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyEmitter;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.util.concurrent.CompletableFuture;

/**
 * @author diaoyn
 * @create 2024-03-29 15:00:41
 */
@RestController
@RequestMapping
@Api(tags = "demo服务端接口")
@Slf4j
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

    @ApiOperation("创建Emitter")
    @GetMapping("/getConn/{clientId}")
    public ResponseVO<Void> getConn(@PathVariable String clientId) {
        demoService.getConn(clientId);
        return ResponseVO.success();
    }

    @ApiOperation("连续获取服务器时间")
    @GetMapping("/getTime/{clientId}")
    public ResponseBodyEmitter getTime(@PathVariable String clientId) {
        ResponseBodyEmitter emitter = demoService.getConn(clientId);
        CompletableFuture.runAsync(() -> {
            try {
                for (int i = 0; i < 20; i++) {
                    emitter.send(DateUtil.now(), MediaType.APPLICATION_JSON);
                    Thread.sleep(500);
                }
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


    @GetMapping("/export")
    @ApiOperation("导出文件")
    @SneakyThrows
    public void export(HttpServletResponse response) throws IOException {
        File file = FileUtil.file("C:\\Users\\EDY\\Desktop\\voice\\ordSpeechSounds\\开启自动落锁1720927751603.wav");
        download(file, response);
    }


    /**
     * 下载文件
     *
     * @param file     要下载的文件
     * @param response 响应
     * @author xuyuxiang
     * @date 2020/8/5 21:46
     */
    public static void download(File file, HttpServletResponse response) {
        download(file.getName(), FileUtil.readBytes(file), response);
    }

    /**
     * 下载文件
     *
     * @author xuyuxiang
     * @date 2022/7/31 10:57
     */
    public static void download(String fileName, byte[] fileBytes, HttpServletResponse response) {
        try {
            response.setHeader("Content-Disposition", "attachment;filename=" + URLUtil.encode(fileName));
            response.addHeader("Content-Length", "" + fileBytes.length);
            response.setHeader("Access-Control-Allow-Origin", "*");
            response.setHeader("Access-Control-Expose-Headers", "Content-Disposition");
            response.setContentType("application/octet-stream;charset=UTF-8");
            IoUtil.write(response.getOutputStream(), true, fileBytes);
        } catch (IOException e) {
            log.error(">>> 文件下载异常：", e);
        }
    }
}
