package com.diaoyn.common.aspect.annotation;

import java.lang.annotation.*;

/**
 * AutoLog 切面
 * @author diaoyn
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface AutoLog {

    /**
     * 日志内容
     */
    String value() default "";


    String name() default "";
}
