package com.example.gateway.config;

import cn.hutool.core.date.StopWatch;
import com.example.common.vo.ResponseVO;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.core.io.buffer.DataBufferFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 * @author diaoyn
 * @create 2024-03-30 15:23:34
 */
@Component
@Slf4j
public class AuthFilter implements GlobalFilter, Ordered {

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        ServerHttpRequest request = exchange.getRequest();
        ServerHttpResponse response = exchange.getResponse();

        if (request.getURI().getPath().contains("/demo")
                || request.getURI().getPath().contains("/v2/api-docs")) {
            return chain.filter(exchange).then(Mono.fromRunnable(() -> {
                stopWatch.stop();
                if (stopWatch.getTotalTimeMillis() > 3000) {
                    log.info("响应时间超过3秒的请求打印日志-------------------------------------------------------------------");
                    log.info("Request IP: {}, URL: {}, cost: {} ms", exchange.getRequest().getRemoteAddress(), exchange.getRequest().getURI(), stopWatch.getTotalTimeSeconds());
                }
            }));
        }


        // 业务逻辑代码
        if (request.getHeaders().getFirst("Authorization") == null) {
            // 权限有问题返回，并结束执行
            response.setStatusCode(HttpStatus.OK);
            return response.writeWith(Mono.fromSupplier(() -> {
                DataBufferFactory bufferFactory = response.bufferFactory();
                ObjectMapper objectMapper = new ObjectMapper();

                try {
                    //writeValueAsBytes 组装错误响应结果
                    return bufferFactory.wrap(objectMapper.writeValueAsBytes(ResponseVO.fail("权限不足")));
                } catch (JsonProcessingException e) {
                    return bufferFactory.wrap(new byte[0]);
                }
            }));
        }
        return chain.filter(exchange).then(Mono.fromRunnable(() -> {
            stopWatch.stop();
            if (stopWatch.getTotalTimeMillis() > 3000) {
                log.info("响应时间超过3秒的请求打印日志-------------------------------------------------------------------");
                log.info("Request IP: {}, URL: {}, cost: {} ms", exchange.getRequest().getRemoteAddress(), exchange.getRequest().getURI(), stopWatch.getTotalTimeSeconds());
            }
        }));
    }


    @Override
    public int getOrder() {
        return 1;
    }
}
