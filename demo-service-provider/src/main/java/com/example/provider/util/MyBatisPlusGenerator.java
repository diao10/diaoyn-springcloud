package com.example.provider.util;

import com.baomidou.mybatisplus.generator.FastAutoGenerator;
import com.baomidou.mybatisplus.generator.engine.BeetlTemplateEngine;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;
import com.baomidou.mybatisplus.generator.engine.VelocityTemplateEngine;

import java.nio.file.Paths;

/**
 * @ClassName MyBatisPlusGenerator
 * @Author diaoyn
 * @Date 2024/6/6 9:59
 */
public class MyBatisPlusGenerator {
    public static void main(String[] args) {
        FastAutoGenerator.create("jdbc:mysql://localhost:3306/demo", "root", "root")
                .globalConfig(builder -> builder
                        .author("diaoyn")
                        .outputDir(Paths.get(System.getProperty("user.dir")) + "/demo-service-provider" + "/src/main/java")
                        .commentDate("yyyy-MM-dd")
                )
                .packageConfig(builder -> builder
                        .parent("com.example.provider")
                        .entity("entity")
                        .mapper("mapper")
                        .service("service")
                        .serviceImpl("service.impl")
                        .xml("mapper.xml")
                )
                .strategyConfig(builder -> builder
                        .entityBuilder()
                        .enableLombok()
                        .enableFileOverride()
                )
                .templateEngine(new FreemarkerTemplateEngine())
                .execute();
    }
}
