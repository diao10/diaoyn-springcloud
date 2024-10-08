package com.diaoyn.common.handle;

import com.alibaba.fastjson.annotation.JSONField;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.stereotype.Component;
import springfox.documentation.builders.PropertySpecificationBuilder;
import springfox.documentation.schema.property.ModelSpecificationFactory;
import springfox.documentation.spi.schema.contexts.ModelPropertyContext;
import springfox.documentation.spring.web.DescriptionResolver;
import springfox.documentation.swagger.schema.ApiModelPropertyPropertyBuilder;

import java.lang.reflect.Field;
import java.util.Optional;

import static java.util.Optional.empty;
import static springfox.documentation.schema.Annotations.findPropertyAnnotation;

/**
 * swagger2如何让页面的展示字段按照@JSONField配置的属性来展示
 *
 * @author diaoyn
 * @ClassName ApiModelPropertyPropertyBuilderJson
 * @Date 2024/10/8 10:44
 */
@Component
public class ApiModelPropertyPropertyBuilderJson extends ApiModelPropertyPropertyBuilder {

    public ApiModelPropertyPropertyBuilderJson(DescriptionResolver descriptions,
                                               ModelSpecificationFactory modelSpecifications) {
        super(descriptions, modelSpecifications);
    }

    @Override
    public void apply(ModelPropertyContext context) {
        super.apply(context);
        Optional<ApiModelProperty> apiModelProperty = empty();
        Optional<JSONField> jsonField = empty();
        if (context.getBeanPropertyDefinition().isPresent()) {
            apiModelProperty = apiModelProperty.map(Optional::of).orElse(findPropertyAnnotation(
                    context.getBeanPropertyDefinition().get(),
                    ApiModelProperty.class));
            jsonField = jsonField.map(Optional::of).orElse(findPropertyAnnotation(
                    context.getBeanPropertyDefinition().get(),
                    JSONField.class));
        }
        Optional<ApiModelProperty> finalApi = apiModelProperty;
        Optional<JSONField> finalJson = jsonField;
        if (finalApi.isPresent() && finalJson.isPresent()) {
            PropertySpecificationBuilder specificationBuilder = context.getSpecificationBuilder();
            try {
                Field field = specificationBuilder.getClass().getDeclaredField("name");
                field.setAccessible(true);
                Object oldValue = field.get(specificationBuilder);
                if (oldValue == null) {
                    oldValue = "";
                }
                field.set(specificationBuilder, finalJson.map(JSONField::name).orElse(oldValue.toString()));
            } catch (NoSuchFieldException | IllegalAccessException e) {
                e.printStackTrace();
            }
        }
    }
}
