package com.example.demoserviceconsumer.controller;

import com.example.democommon.vo.DemoRepVO;
import com.example.democommon.vo.DemoVO;
import com.example.democommon.vo.ResponseVO;
import com.example.demoserviceconsumer.feign.FeignService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author diaoyn
 * @create 2024-03-29 15:00:41
 */
@RestController
@RequestMapping
@Api(tags = "demo2客户端接口")
public class Demo2Controller {

    @Resource
    FeignService feignService;

    @ApiOperation("测试用户2")
    @PostMapping("/test")
    public ResponseVO<DemoRepVO> demo(@RequestBody @Validated DemoVO vo) {
        vo.setSource("client");
        return feignService.demo(vo);
    }

}
