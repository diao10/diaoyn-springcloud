package com.diaoyn.alone.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Collections;

/**
 * @ClassName SpringdocConfig
 * @Author diaoyn
 * @Date 2024/5/13 13:58
 */
@Configuration
public class SpringdocConfig {

    @Bean
    public GroupedOpenApi publicApi() {
        return GroupedOpenApi.builder()
                .group("我的")
                .pathsToMatch("/**/**")
                .addOperationCustomizer((operation, handlerMethod) -> operation.security(
                        Collections.singletonList(new SecurityRequirement().addList("token")))
                )
                .build();
    }

    @Bean
    public OpenAPI customOpenApi() {
        Contact contact = new Contact();
        contact.setName("diaoyn");
        return new OpenAPI().components(new Components()
                        .addSecuritySchemes("token", new SecurityScheme().type(SecurityScheme.Type.APIKEY).scheme(
                                "token")))
                .info(new Info().title("API")
                        .description("This is API")
                        .contact(contact)
                );
    }
}
