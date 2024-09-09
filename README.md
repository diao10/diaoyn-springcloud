# 基于原生 springcloud 或 springcloudAlibaba 框架搭建项目

# 集成Nacos 作为配置中心和注册中心

# 各个模块以微服务形式启动

swagger2本地访问地址为

[//]: # (http://localhost:8721/swagger-ui/index.html)
http://localhost:8721/doc.html
/diaoyn接口不会验证权限
Authorization只校验了是否为空，填写任意值即可通过

**diaoyn.common**
- 公共的类等

**diaoyn-common-generator**
- 自己写的生成代码的东西
- 一边写一边改

**diaoyn-gateway**
- 全局异常处理，响应结果对象化标准化（统一格式）
- 全局过滤器，响应时间超过3秒的请求打印日志（包括客户端IP、请求URI、耗时）
- 全局过滤器，请求头Authorization 授权校验无效时返回失败

**diaoyn-service-alone**
- 一个单独的jar包，基于jdk17

**diaoyn-service-consumer**
- 全局异常处理，响应结果对象化标准化（统一格式）
- 全局 controller 日志切面，响应时间超过3秒的请求打印日志（包括客户端IP、请求URI 、耗时、请求入参，响应结果）
- API接口请求入参对象化，请求入参有标准化校验，响应结果对象化标准化（统一格式）

**diaoyn-service-plugin**
- 一个插件包，被diaoyn-service-provider所应用，也是一种分布式开发的思想

**diaoyn-service-provider**
- 全局异常处理，响应结果对象化标准化（统一格式）
- 全局 controller 日志切面，响应时间超过3秒的请求打印日志（包括客户端IP、请求URI 、耗时、请求入参，响应结果）
- API接口请求入参对象化，请求入参有标准化校验，响应结果对象化标准化（统一格式）
