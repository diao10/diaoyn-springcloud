package com.example.provider.service.impl;

import com.example.common.vo.DemoRepVO;
import com.example.common.vo.DemoVO;
import com.example.common.vo.ResponseVO;
import com.example.provider.service.DemoService;
import com.example.provider.service.IMyUserService;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;

/**
 * @author diaoyn
 * @create 2024-03-29 15:16:41
 */
@Service
@RefreshScope
public class DemoServiceImpl implements DemoService {


    @Resource
    private IMyUserService myUserService;

    @Override
    public ResponseVO<DemoRepVO> demo(DemoVO vo) {
        ResponseVO<DemoRepVO> responseVO;
        if (!myUserService.exists(vo.getUserName(), vo.getPassword())) {
            return ResponseVO.fail("账号或者密码不存在");
        }

        DemoRepVO repVO = new DemoRepVO();
        repVO.setName(vo.getUserName());
        repVO.setDesc("这是来自" + vo.getSource() + "的调用");
        repVO.setCreateDate(new Date());
        responseVO = ResponseVO.success(repVO);
        return responseVO;
    }
}
