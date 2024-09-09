package com.diaoyn.provider.service;

import com.diaoyn.common.vo.DemoRepVO;
import com.diaoyn.common.vo.DemoVO;
import com.diaoyn.common.vo.ResponseVO;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import javax.validation.constraints.NotBlank;

/**
 * @author diaoyn
 * @create 2024-03-29 15:15:50
 */
public interface DemoService {

    /**
     * demo
     *
     * @param vo vo
     * @return result
     */
    ResponseVO<DemoRepVO> demo(DemoVO vo);

    SseEmitter getConn(@NotBlank String clientId);

    void send(@NotBlank String clientId);

    void closeConn(@NotBlank String clientId);

}
