package com.diaoyn.provider.util;

import javax.servlet.http.HttpServletRequest;
import java.util.regex.Pattern;

public class BrowserUtils {

    /** 判断请求是否来自电脑端 */
    public static boolean isDesktop(HttpServletRequest request) {
        return !isMobile(request);
    }

    /** 判断请求是否来自移动端 */
    public static boolean isMobile(HttpServletRequest request) {
        String ua = request.getHeader("User-Agent").toLowerCase();
        String type = "(phone|pad|pod|iphone|ipod|ios|ipad|android|mobile|blackberry|iemobile|mqqbrowser|juc|fennec|wosbrowser|browserng|webos|symbian|windows phone)";
        Pattern pattern = Pattern.compile(type);
        return pattern.matcher(ua).find();
    }

}
