package com.example.demoserviceprovider.service;

import com.example.democommon.vo.ResponseVO;
import com.example.democommon.vo.DemoRepVO;
import com.example.democommon.vo.DemoVO;

/**
 * @author diaoyn
 * @create 2024-03-29 15:15:50
 */
public interface DemoService {

    /**
     * demo
     * @param vo vo
     * @return result
     */
    ResponseVO<DemoRepVO> demo(DemoVO vo);
}
