package com.diaoyn.provider.multiple.impl;

import com.diaoyn.provider.multiple.DemoManyService;
import org.springframework.stereotype.Service;

/**
 * @author diaoyn
 * @ClassName Demo2ServiceImpl
 * @Date 2024/9/25 17:21
 */
@Service
//@ConditionalOnProperty(value = "multiple.type", havingValue = "11")
public class Demo2ServiceImpl implements DemoManyService {
    @Override
    public String test() {
        return this.getClass().getName() + "的 test()返回。";
    }

    @Override
    public String test2() {
        return this.getClass().getName() + "的 test2()返回。";
    }
}
