package com.diaoyn.provider.handler;

import cn.hutool.core.date.DateTime;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.jwt.JWTUtil;
import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Objects;

/**
 * mybatis-plus自动填充(需要实体类里填写@TableField(fill = FieldFill.INSERT))等等，才会调取该方法
 * 该方法默认全部实体类
 *
 * @author diaoyn
 * @ClassName MybatisMetaObjectHandler
 * @Date 2024/9/6 13:24
 */
//
@Component
public class MybatisMetaObjectHandler implements MetaObjectHandler {

    /**
     * 删除标志
     */
    private static final String DELETE_FLAG = "deleteFlag";

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
    public void insertFill(MetaObject metaObject) {
        //获取当前对象
//        metaObject.getOriginalObject();
        //为空则设置deleteFlag
        Object deleteFlag = metaObject.getValue(DELETE_FLAG);
        if (ObjectUtil.isNull(deleteFlag)) {
            setFieldValByName(DELETE_FLAG, 0, metaObject);
        }
        //为空则设置createUser
        Object createUser = metaObject.getValue(CREATE_USER);
        if (ObjectUtil.isNull(createUser)) {
            setFieldValByName(CREATE_USER, this.getUserId(), metaObject);
        }
        //为空则设置createTime
        Object createTime = metaObject.getValue(CREATE_TIME);
        if (ObjectUtil.isNull(createTime)) {
            setFieldValByName(CREATE_TIME, DateTime.now(), metaObject);
        }
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        //设置updateUser
        setFieldValByName(UPDATE_USER, this.getUserId(), metaObject);
        //设置updateTime
        setFieldValByName(UPDATE_TIME, DateTime.now(), metaObject);
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
}
