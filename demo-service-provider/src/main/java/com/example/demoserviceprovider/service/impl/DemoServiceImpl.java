package com.example.demoserviceprovider.service.impl;

import com.example.democommon.vo.DemoRepVO;
import com.example.democommon.vo.DemoVO;
import com.example.democommon.vo.ResponseVO;
import com.example.demoserviceprovider.service.DemoService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * @author diaoyn
 * @create 2024-03-29 15:16:41
 */
@Service
@RefreshScope
public class DemoServiceImpl implements DemoService {


    @Value("${user.username}")
    String username;

    @Value("${user.password}")
    String password;

    @Override
    public ResponseVO<DemoRepVO> demo(DemoVO vo) {

        ResponseVO<DemoRepVO> responseVO = ResponseVO.fail();

        if (!username.equals(vo.getName())
                || !password.equals(vo.getPassword())) {
            responseVO.setMessage("账号或者密码错误");
            return responseVO;
        }
        DemoRepVO repVO = new DemoRepVO();
        repVO.setName(vo.getName());
        repVO.setDesc("这是来自" + vo.getSource() + "的调用");
        repVO.setCreateDate(new Date());
        responseVO = ResponseVO.success(repVO);
        return responseVO;
    }
}
