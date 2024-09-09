package com.diaoyn.provider.service.impl;

import cn.hutool.core.date.DateUtil;
import com.diaoyn.common.vo.DemoRepVO;
import com.diaoyn.common.vo.DemoVO;
import com.diaoyn.common.vo.ResponseVO;
import com.diaoyn.provider.service.DemoService;
import com.diaoyn.provider.service.IMyUserService;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import javax.annotation.Resource;
import java.util.Date;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author diaoyn
 * @create 2024-03-29 15:16:41
 */
@Service
@RefreshScope
@Slf4j
public class DemoServiceImpl implements DemoService {

    private static final Map<String, SseEmitter> SSE_CACHE = new ConcurrentHashMap<>();
    @Resource
    private IMyUserService myUserService;

    @Override
    public ResponseVO<DemoRepVO> demo(DemoVO vo) {
        ResponseVO<DemoRepVO> responseVO;
        if (!myUserService.exists(vo.getUserName(), vo.getPassword())) {
            return ResponseVO.fail("账号或者密码不存在");
        }

        DemoRepVO repVO = new DemoRepVO();
        repVO.setName(vo.getUserName());
        repVO.setDesc("这是来自" + vo.getSource() + "的调用");
        repVO.setCreateDate(new Date());
        responseVO = ResponseVO.success(repVO);
        return responseVO;
    }

    @Override
    public SseEmitter getConn(String clientId) {
        SseEmitter sseEmitter = SSE_CACHE.get(clientId);

        if (sseEmitter != null) {
            return sseEmitter;
        } else {
            // 设置连接超时时间，需要配合配置项 spring.mvc.async.request-timeout: 600000 一起使用
            final SseEmitter emitter = new SseEmitter(600_000L);
            // 注册超时回调，超时后触发
            emitter.onTimeout(() -> {
                log.info("连接已超时，正准备关闭，clientId = {}", clientId);
                SSE_CACHE.remove(clientId);
            });
            // 注册完成回调，调用 emitter.complete() 触发
            emitter.onCompletion(() -> {
                log.info("连接已关闭，正准备释放，clientId = {}", clientId);
                SSE_CACHE.remove(clientId);
                log.info("连接已释放，clientId = {}", clientId);
            });
            // 注册异常回调，调用 emitter.completeWithError() 触发
            emitter.onError(throwable -> {
                log.error("连接已异常，正准备关闭，clientId = {}", clientId, throwable);
                SSE_CACHE.remove(clientId);
            });

            SSE_CACHE.put(clientId, emitter);

            return emitter;
        }
    }

    @Override
    @SneakyThrows
    public void send(String clientId) {
        SseEmitter emitter = SSE_CACHE.get(clientId);
        for (int i = 0; i < 20; i++) {
            emitter.send(DateUtil.now(), MediaType.APPLICATION_JSON);
            Thread.sleep(1000);
        }
    }

    @Override
    public void closeConn(String clientId) {
        SseEmitter sseEmitter = SSE_CACHE.get(clientId);
        if (sseEmitter != null) {
            sseEmitter.complete();
        }
    }
}
