package com.diaoyn.alone.filter;

import com.diaoyn.alone.vo.ResponseVO;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.Map;
import java.util.Set;
import java.util.regex.Pattern;

/**
 * @author diaoyn
 * @ClassName EmojiFilter
 * @Date 2024/11/29 16:25
 */
@Component
@Slf4j
public class EmojiFilter implements Filter {

    private static final String EMOJI_REGEX = "[\\x{1F600}-\\x{1F64F}\\x{1F300}-\\x{1F5FF}\\x{1F680}-\\x{1F6FF}\\x" +
            "{1F700}-\\x{1F77F}\\x{1F780}-\\x{1F7FF}\\x{1F800}-\\x{1F8FF}\\x{1F900}-\\x{1F9FF}\\x{1FA00}-\\x{1FA6F" +
            "}\\x{1FA70}-\\x{1FAFF}\\x{2600}-\\x{26FF}\\x{2700}-\\x{27BF}\\x{2300}-\\x{23FF}]";

    private static final String GET = "GET";

    private static final String POST = "POST";

    private static final String PUT = "PUT";

    @Override
    public void init(FilterConfig filterConfig) {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException,
            ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;
        //post和put请求只能读一次stream 需要封装
        CustomHttpServletRequestWrapper emojiWrapper = new CustomHttpServletRequestWrapper(req);
        res.setCharacterEncoding("utf-8");
        res.setHeader("content-type", "application/json;charset=utf-8");
        BufferedReader br = null;
        String method = req.getMethod();
        boolean tag = false;
        Pattern pattern = Pattern.compile(EMOJI_REGEX);

        if (GET.equals(method)) {
            Map<String, String[]> map = req.getParameterMap();
            Set<String> keySet = map.keySet();
            for (String key : keySet) {
                String value = req.getParameter(key);
                if (pattern.matcher(value).find()) {
                    tag = true;
                    break;
                }
            }
        }
        if (POST.equals(method) || PUT.equals(method)) {
            try {
                br = emojiWrapper.getReader();
                StringBuffer string = new StringBuffer();
                String line;
                while ((line = br.readLine()) != null) {
                    string.append(line);
                }
                if (pattern.matcher(string).find()) {
                    tag = true;
                }
            } catch (Exception ex) {
                log.error("emoji filter error", ex);
            } finally {
                if (br != null) {
                    br.close();
                }
            }
        }
        if (tag) {
            ObjectMapper objectMapper = new ObjectMapper();

            objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
            String result = objectMapper.writeValueAsString(ResponseVO.fail("请不要输入表情"));
            response.getWriter().println(result);
        } else {
            chain.doFilter(emojiWrapper, response);
        }
    }

    @Override
    public void destroy() {
    }

}
