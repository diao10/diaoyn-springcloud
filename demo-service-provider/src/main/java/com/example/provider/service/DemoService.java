package com.example.provider.service;

import com.example.common.vo.ResponseVO;
import com.example.common.vo.DemoRepVO;
import com.example.common.vo.DemoVO;

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
