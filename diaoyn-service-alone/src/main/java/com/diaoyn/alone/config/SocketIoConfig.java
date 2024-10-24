//package com.diaoyn.alone.config;
//
//import cn.hutool.core.thread.ThreadUtil;
//import cn.hutool.core.util.StrUtil;
//import com.corundumstudio.socketio.SocketConfig;
//import com.corundumstudio.socketio.SocketIOServer;
//import com.corundumstudio.socketio.Transport;
//import com.corundumstudio.socketio.annotation.SpringAnnotationScanner;
//import com.ynqd.socketio.handler.ExceptionListenerHandler;
//import lombok.SneakyThrows;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
//import java.net.InetAddress;
//import java.net.UnknownHostException;
//import java.util.concurrent.TimeUnit;
//
///**
// * @author diaoyn
// * @ClassName SocketIOConfig
// * @Date 2024/9/24 11:03
// */
//@Configuration
//public class SocketIoConfig {
//
//    @Value("${socketIo.port}")
//    private Integer port;
//
//    @Value("${socketIo.maxFramePayloadLength}")
//    private Integer maxFramePayloadLength;
//
//    @Value("${socketIo.maxHttpContentLength}")
//    private Integer maxHttpContentLength;
//
//    @Value("${socketIo.bossCount}")
//    private int bossCount;
//
//    @Value("${socketIo.workCount}")
//    private int workCount;
//
//    @Value("${socketIo.allowCustomRequests}")
//    private boolean allowCustomRequests;
//
//    @Value("${socketIo.upgradeTimeout}")
//    private int upgradeTimeout;
//
//    @Value("${socketIo.pingTimeout}")
//    private int pingTimeout;
//
//    @Value("${socketIo.pingInterval}")
//    private int pingInterval;
//
//    @Bean
//    @SneakyThrows
//    public SocketIOServer socketIoServer() {
//        SocketConfig socketConfig = new SocketConfig();
//        socketConfig.setTcpNoDelay(true);
//        socketConfig.setSoLinger(0);
//        com.corundumstudio.socketio.Configuration config = new com.corundumstudio.socketio.Configuration();
//        config.setSocketConfig(socketConfig);
//        config.setHostname(getIp4Address());
//        config.setPort(port);
//        config.setBossThreads(bossCount);
//        config.setWorkerThreads(workCount);
//        config.setAllowCustomRequests(allowCustomRequests);
//        config.setUpgradeTimeout(upgradeTimeout);
//        config.setPingTimeout(pingTimeout);
//        config.setPingInterval(pingInterval);
//        config.setTransports(Transport.WEBSOCKET, Transport.POLLING);
//        config.setMaxFramePayloadLength(maxFramePayloadLength);
//        config.setMaxHttpContentLength(maxHttpContentLength);
//        config.setOrigin(":*:");
//        config.setExceptionListener(new ExceptionListenerHandler());
//        return new SocketIOServer(config);
//    }
//
//    /**
//     * 获取实际ip,127.0.0.1好像也可以但是没试过
//     *
//     * @return ip4
//     * @throws UnknownHostException UnknownHostException
//     */
//    public static String getIp4Address() throws UnknownHostException {
//        boolean flag = true;
//        String ip = "";
//        while (flag) {
//            InetAddress localHost = InetAddress.getLocalHost();
//            ip = localHost.getHostAddress();
//            if (!StrUtil.containsIgnoreCase(ip, "localhost")
//                    && !StrUtil.containsIgnoreCase(ip, "127.0.0.1")) {
//                flag = false;
//            }
//            ThreadUtil.sleep(1, TimeUnit.SECONDS);
//        }
//        return ip;
//    }
//
//    /**
//     * 扫描socket相关的注解，例如@OnConnect、@DisConnect、@OnEvent
//     *
//     * @return SpringAnnotationScanner
//     */
//    @Bean
//    public SpringAnnotationScanner springAnnotationScanner() {
//        return new SpringAnnotationScanner(socketIoServer());
//    }
//
//}