package com.diaoyn.provider.aspect;

import com.diaoyn.provider.aspect.annotation.CacheExpire;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.expression.EvaluationContext;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.concurrent.TimeUnit;

/**
 * @author diaoyn
 * @Date 2025-09-09 18:33:05
 */
@Aspect
@Component
public class CacheExpireAspect {

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @Around("@annotation(com.diaoyn.provider.aspect.annotation.CacheExpire)")
    public Object handleRedisCacheExpire(ProceedingJoinPoint joinPoint) throws Throwable {
        // 执行原方法
        Object result = joinPoint.proceed();

        // 获取方法上的注解
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();

        Cacheable cacheable = method.getAnnotation(Cacheable.class);
        CacheExpire cacheExpire = method.getAnnotation(CacheExpire.class);

        // 如果同时存在 @Cacheable 和 @CacheExpire 注解
        if (cacheable != null && cacheExpire != null) {
            // 生成缓存键
            String key = generateRedisKey(method, joinPoint.getArgs(), cacheable.key(), cacheable.value());

            // 设置过期时间
            if (cacheExpire.expire() > 0) {
                redisTemplate.expire(key, cacheExpire.expire(), TimeUnit.SECONDS);
            }
        }

        return result;
    }

    /**
     * 生成 Redis 缓存键
     */
    private String generateRedisKey(Method method, Object[] args, String keyExpression, String[] cacheNames) {
        String cacheName = cacheNames.length > 0 ? cacheNames[0] : "default";

        if (org.springframework.util.StringUtils.hasText(keyExpression)) {
            // 解析 SpEL 表达式
            ExpressionParser parser = new SpelExpressionParser();
            EvaluationContext context = new StandardEvaluationContext();
// TODO: 2025/9/9 啊啊啊啊没写
            // 设置方法参数到上下文
//            String[] paramNames = ((MethodSignature) method).getParameterNames();
//            for (int i = 0; i < args.length; i++) {
//                context.setVariable(paramNames[i], args[i]);
//            }
//
//            Expression expression = parser.parseExpression(keyExpression);
//            String key = expression.getValue(context, String.class);
//            return cacheName + "::" + key;
        }

        // 默认键生成逻辑
        StringBuilder keyBuilder = new StringBuilder(cacheName).append("::").append(method.getName());
        for (Object arg : args) {
            keyBuilder.append(":").append(arg);
        }
        return keyBuilder.toString();
    }
}
