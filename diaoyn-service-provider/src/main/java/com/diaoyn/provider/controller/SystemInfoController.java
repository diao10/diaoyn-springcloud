package com.diaoyn.provider.controller;


import com.diaoyn.common.vo.ResponseVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import oshi.SystemInfo;


/**
 * @author diaoyn
 * @since 2025-01-06 16:59:09
 */
@RestController
@RequestMapping("/systemInfo")
@Api(tags = "硬件的信息")
public class SystemInfoController {


    @GetMapping("/mac")
    @ApiOperation("查看mac地址")
    public ResponseVO<String> mac() {
        SystemInfo systemInfo = new SystemInfo();
        return ResponseVO.success(systemInfo.getHardware().getNetworkIFs(false)
                .get(0).getMacaddr());
    }

    @GetMapping("/hardware")
    @ApiOperation("查看硬件uuid")
    public ResponseVO<String> hardware() {
        SystemInfo systemInfo = new SystemInfo();
        return ResponseVO.success(systemInfo.getHardware().getComputerSystem().getHardwareUUID());
    }
}

