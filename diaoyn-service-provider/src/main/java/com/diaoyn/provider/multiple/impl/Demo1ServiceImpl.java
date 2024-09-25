package com.diaoyn.provider.multiple.impl;

import com.diaoyn.provider.multiple.DemoManyService;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;


/**
 * @author diaoyn
 * @ClassName Demo1ServiceImpl
 * @Date 2024/9/25 17:21
 */
@Service
@Primary
@ConditionalOnProperty(value = "multiple.type", havingValue = "00")
public class Demo1ServiceImpl implements DemoManyService {
    @Override
    public String test() {
         return this.getClass().getName() + "的 test()返回。";
    }

    @Override
    public String test2() {
        return this.getClass().getName() + "的 test2()返回。";
    }
}
