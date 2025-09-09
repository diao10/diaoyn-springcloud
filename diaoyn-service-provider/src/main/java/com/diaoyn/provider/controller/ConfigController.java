package com.diaoyn.provider.controller;

import com.diaoyn.common.vo.ResponseVO;
import com.diaoyn.provider.aspect.annotation.CacheExpire;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author diaoyn
 * @Date 2025-09-09 18:26:52
 */
@RestController
@RequestMapping
@Api(tags = "配置项服务端接口")
@Slf4j
public class ConfigController {

    @GetMapping(value = "/getAll")
    @Cacheable(value = "config_all")
    @CacheExpire(expire = 24 * 60 * 60)
    public ResponseVO<Void> getAll() {
        return ResponseVO.fail("not implemented");
    }


    @GetMapping(value = "/get/{id}")
    @Cacheable(value = "config_codes", key = "'config_codes_'+#id")
    @CacheExpire(expire = 24 * 60 * 60)
    public ResponseVO<Void> getByCode(@PathVariable String id) {
        log.info("ConfigController getByCode id: {}", id);
        return ResponseVO.fail("not implemented");
    }
}
