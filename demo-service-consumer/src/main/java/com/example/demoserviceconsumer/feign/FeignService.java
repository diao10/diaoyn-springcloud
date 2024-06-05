package com.example.demoserviceconsumer.feign;

import com.example.democommon.vo.DemoRepVO;
import com.example.democommon.vo.DemoVO;
import com.example.democommon.vo.ResponseVO;
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
