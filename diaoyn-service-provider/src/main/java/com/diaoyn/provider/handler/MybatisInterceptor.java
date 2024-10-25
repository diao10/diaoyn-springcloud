package com.diaoyn.provider.handler;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.jwt.JWTUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.SqlCommandType;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Signature;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Field;
import java.util.*;

/**
 * 通过拦截器实现公共字段插入
 *
 * @author diaoyn
 * @ClassName MybatisInterceptor
 * @Date 2024/10/24 18:00
 */
@Slf4j
@Component
@Intercepts({@Signature(type = Executor.class, method = "update", args = {MappedStatement.class, Object.class})})
public class MybatisInterceptor implements Interceptor {

    /**
     * 创建人
     */
    private static final String CREATE_USER = "createUser";

    /**
     * 创建时间
     */
    private static final String CREATE_TIME = "createTime";

    /**
     * 更新人
     */
    private static final String UPDATE_USER = "updateUser";

    /**
     * 更新时间
     */
    private static final String UPDATE_TIME = "updateTime";

    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        MappedStatement mappedStatement = (MappedStatement) invocation.getArgs()[0];
        SqlCommandType sqlCommandType = mappedStatement.getSqlCommandType();
        Object parameter = invocation.getArgs()[1];
        String userId = getUserId();
        if (parameter == null || StrUtil.isEmpty(userId)) {
            return invocation.proceed();
        }
        Field[] fields = getAllFields(parameter);
        for (Field field : fields) {
            try {
                if (!CREATE_USER.equals(field.getName())
                        && !CREATE_TIME.equals(field.getName())
                        && !UPDATE_USER.equals(field.getName())
                        && !UPDATE_TIME.equals(field.getName())) {
                    continue;
                }
                field.setAccessible(true);
                Object localObject = field.get(parameter);
                if (ObjectUtil.isNotNull(localObject)) {
                    continue;
                }
                if (SqlCommandType.INSERT == sqlCommandType && CREATE_USER.equals(field.getName())) {
                    field.set(parameter, userId);
                }
                if (SqlCommandType.INSERT == sqlCommandType && CREATE_TIME.equals(field.getName())) {
                    field.set(parameter, new Date());
                }
                if (SqlCommandType.UPDATE == sqlCommandType && UPDATE_USER.equals(field.getName())) {
                    field.set(parameter, userId);
                }
                if (SqlCommandType.UPDATE == sqlCommandType && UPDATE_TIME.equals(field.getName())) {
                    field.set(parameter, new Date());
                }
                field.setAccessible(false);

            } catch (Exception ignored) {

            }
        }
        return invocation.proceed();
    }

    /**
     * 获取用户id
     *
     * @return String
     */
    private String getUserId() {
        // 从请求中获取Token，这里假设Token在请求头中
        HttpServletRequest request =
                ((ServletRequestAttributes) Objects.requireNonNull(RequestContextHolder.getRequestAttributes())).getRequest();
        String token = request.getHeader("token");
        if (StrUtil.isNotEmpty(token)) {
            return JWTUtil.parseToken(token).getPayload("userId").toString();
        }
        return null;
    }

    /**
     * 获取类的所有属性，包括父类
     *
     * @param object object
     * @return Field[]
     */
    public static Field[] getAllFields(Object object) {
        Class<?> clazz = object.getClass();
        List<Field> fieldList = new ArrayList<>();
        while (clazz != null) {
            fieldList.addAll(new ArrayList<>(Arrays.asList(clazz.getDeclaredFields())));
            clazz = clazz.getSuperclass();
        }
        Field[] fields = new Field[fieldList.size()];
        fieldList.toArray(fields);
        return fields;
    }
}
