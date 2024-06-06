package com.example.consumer.feign;

import com.example.common.vo.DemoRepVO;
import com.example.common.vo.DemoVO;
import com.example.common.vo.ResponseVO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

/**
 * @author diaoyn
 * @create 2024-03-29 19:56:07
 */
@FeignClient("provider")
public interface FeignService {

    @PostMapping("/demo")
    ResponseVO<DemoRepVO> demo(DemoVO vo);

}
