package com.example.provider.aspect;

import cn.hutool.core.date.StopWatch;
import cn.hutool.core.util.ObjectUtil;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

/**
 * @author diaoyn
 * @create 2024-03-30 16:12:01
 */
@Aspect
@Slf4j
@Component
public class LogAspect {

    @Pointcut("execution(public * com.example.provider.controller..*.*(..))") // 扫描包下所有方法
    public void logTime() {
    }

    @Around("logTime()")
    public Object around(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        Object result = proceedingJoinPoint.proceed();
        stopWatch.stop();
        if (stopWatch.getTotalTimeMillis() > 3000) {
            ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
            if (ObjectUtil.isNull(attributes)) {
                return result;
            }
            proceedingJoinPoint.getArgs();
            log.info("响应时间超过3秒的请求打印日志-------------------------------------------------------------------");
            log.info("Request IP: {}, URI: {}, cost: {} ms, params: {}, result: {}"
                    , attributes.getRequest().getRemoteAddr()
                    , attributes.getRequest().getRequestURI()
                    , stopWatch.getTotalTimeMillis()
                    , proceedingJoinPoint.getArgs()[0].toString()
                    , result.toString());
        }
        return result;
    }
}
