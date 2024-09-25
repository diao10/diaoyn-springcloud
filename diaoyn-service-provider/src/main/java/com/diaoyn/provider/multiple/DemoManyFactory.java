package com.diaoyn.provider.multiple;


import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Map;

/**
 * @author diaoyn
 * @ClassName DemoManyFactory
 * @Date 2024/9/25 14:17
 */

@Component
public class DemoManyFactory {

    @Resource
    private Map<String, DemoManyService> demoManyServiceMap;

    public DemoManyService getDemoService(String multipleType) {
        return demoManyServiceMap.get(multipleType);
    }

}
