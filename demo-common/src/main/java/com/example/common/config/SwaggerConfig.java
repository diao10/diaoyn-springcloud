package com.example.common.config;

import io.swagger.annotations.ApiOperation;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.builders.RequestParameterBuilder;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.ParameterType;
import springfox.documentation.service.RequestParameter;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

import java.util.ArrayList;
import java.util.List;

/**
 * @author diaoyn
 * @create 2024-03-29 14:08:51
 */
@Configuration
public class SwaggerConfig implements WebMvcConfigurer {

    @Bean
    public Docket createRestApi() {
        return new Docket(DocumentationType.OAS_30)
                .globalRequestParameters(getGlobalRequestParameters())
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.withMethodAnnotation(ApiOperation.class))
                .paths(PathSelectors.any())
                .paths(PathSelectors.regex("/error.*").negate())//错误路径不监控
                .build();
    }


    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("基于Swagger构建的Rest API文档")
                .version("1.0")
                .build();
    }

    private List<RequestParameter> getGlobalRequestParameters() {
        List<RequestParameter> parameters = new ArrayList<>();
        parameters.add(new RequestParameterBuilder()
                .name("Authorization")
                .description("Authorization")
                .in(ParameterType.HEADER)
                .required(false)
                .build());
        return parameters;
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.
                addResourceHandler("/swagger-ui/**")
                .addResourceLocations("classpath:/META-INF/resources/webjars/springfox-swagger-ui/")
                .resourceChain(false);
        registry.addResourceHandler("/swagger-ui.html").addResourceLocations("classpath:/META-INF/resources/");
        registry.addResourceHandler("/doc.html").addResourceLocations("classpath:/META-INF/resources/");
        registry.addResourceHandler("/webjars/**").addResourceLocations("classpath:/META-INF/resources/webjars/");
    }

}
