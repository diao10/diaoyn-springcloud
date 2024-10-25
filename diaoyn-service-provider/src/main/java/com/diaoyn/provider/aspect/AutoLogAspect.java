package com.diaoyn.provider.aspect;

import cn.hutool.core.date.StopWatch;
import cn.hutool.core.util.ObjectUtil;
import com.diaoyn.provider.aspect.annotation.AutoLog;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.lang.reflect.Method;


/**
 * 系统日志，切面处理类
 *
 * @Author zhaoran
 * @Date 2024.7.10
 */
@Aspect
@Component
@Slf4j
public class AutoLogAspect {

    @Pointcut("@annotation(com.diaoyn.provider.aspect.annotation.AutoLog)")
    public void logPointCut() {

    }

    @Around("logPointCut()")
    public Object around(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        Object result = proceedingJoinPoint.proceed();
        stopWatch.stop();
        MethodSignature signature = (MethodSignature) proceedingJoinPoint.getSignature();
        Method method = signature.getMethod();
        AutoLog autoLog = method.getAnnotation(AutoLog.class);
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (ObjectUtil.isNull(attributes)) {
            return result;
        }
        log.info("注解打印日志打印日志-------------------------------------------------------------------");
        log.info("AutoLogAspect AutoLog: {} Request IP: {}, URI: {}, cost: {} ms, params: {}, result: {}"
                , autoLog.value() + "," + autoLog.name()
                , attributes.getRequest().getRemoteAddr()
                , attributes.getRequest().getRequestURI()
                , stopWatch.getTotalTimeMillis()
                , proceedingJoinPoint.getArgs()[0].toString()
                , result.toString());

        return result;
    }


}
