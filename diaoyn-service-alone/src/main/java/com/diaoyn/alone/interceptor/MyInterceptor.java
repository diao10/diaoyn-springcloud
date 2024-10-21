package com.diaoyn.alone.interceptor;

import cn.hutool.core.util.StrUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.tomcat.util.http.MimeHeaders;
import org.springframework.lang.Nullable;
import org.springframework.web.servlet.HandlerInterceptor;

import java.lang.reflect.Field;

/**
 * @ClassName MyInterceptor
 * @Author diaoyn
 * @Date 2024/5/23 13:26
 */
public class MyInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, @Nullable HttpServletResponse response,
                             @Nullable Object handler) throws Exception {
        String token = request.getHeader("token");
        if (StrUtil.isEmpty(token)) {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "请重新登录");
            return false;
        }
        addRequestHeader(request, "userId", "userId");
        addRequestHeader(request, "userName", "userName");
        return true;
    }

    /**
     * 修改header信息，key-value键值对儿加入到header中
     *
     * @param request request
     * @param key     key
     * @param value   value
     */
    private void addRequestHeader(HttpServletRequest request, String key, String value) {
        Class<? extends HttpServletRequest> requestClass = request.getClass();
        try {
            Field field = requestClass.getDeclaredField("request");
            field.setAccessible(true);
            Object o = field.get(request);
            Field coyoteRequest = o.getClass().getDeclaredField("coyoteRequest");
            coyoteRequest.setAccessible(true);
            Object o1 = coyoteRequest.get(o);
            Field headers = o1.getClass().getDeclaredField("headers");
            headers.setAccessible(true);
            MimeHeaders mimeHeaders = (MimeHeaders) headers.get(o1);
            mimeHeaders.addValue(key).setString(value);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
