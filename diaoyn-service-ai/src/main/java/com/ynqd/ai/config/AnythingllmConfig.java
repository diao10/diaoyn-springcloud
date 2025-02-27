package com.ynqd.ai.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author diaoyn
 * @ClassName AnythingllmConfig
 * @Date 2025/2/20 18:20
 */
@Data
@Component
@ConfigurationProperties(prefix = "anythingllm")
public class AnythingllmConfig {

    private String url;

    private String token;

    private String slug;
}
