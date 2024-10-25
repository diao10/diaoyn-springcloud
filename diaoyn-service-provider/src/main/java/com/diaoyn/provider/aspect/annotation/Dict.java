package com.diaoyn.provider.aspect.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author diaoyn
 * @ClassName Dict
 * @Date 2024/10/25 15:01
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Dict {

    /**
     * 方法描述:  数据code
     *
     * @return 返回类型： String
     */
    String dicCode();

    /**
     * 方法描述:  数据固定值Text
     *
     * @return 返回类型： String
     */
    String dicText() default "";


}
