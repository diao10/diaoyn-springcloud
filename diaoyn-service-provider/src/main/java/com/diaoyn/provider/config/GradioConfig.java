package com.diaoyn.provider.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author diaoyn
 * @ClassName GradioConfig
 * @Date 2025/2/19 14:51
 */
@Data
@Component
@ConfigurationProperties(prefix = "gradio")
public class GradioConfig {

     private String url;

//     private final GradioConfig gradioConfig;
//
//     @Autowired
//     public GradioServiceImpl(@Lazy GradioConfig gradioConfig) {
//          this.gradioConfig = gradioConfig;
//          HttpGlobalConfig.setTimeout(5 * 60 * 1000);
//     }

}
