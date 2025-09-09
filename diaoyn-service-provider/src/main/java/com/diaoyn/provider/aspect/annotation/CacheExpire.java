package com.diaoyn.provider.aspect.annotation;

import java.lang.annotation.*;

/**
 * @author diaoyn
 * @Date 2025-09-09 18:32:28
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface CacheExpire {

    /**
     * 过期时间，单位秒
     * 默认值为 3600 秒（1小时）
     */
    long expire() default 3600L;
}
