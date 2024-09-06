//package com.common.handle;
//
//import cn.hutool.core.date.DateTime;
//import cn.hutool.core.util.ObjectUtil;
//import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
//import org.apache.ibatis.reflection.MetaObject;
//import org.springframework.stereotype.Component;
//
///**
// * @author diaoyn
// * @ClassName CustomMetaObjectHandler
// * @Date 2024/9/6 13:24
// */
//@Component
//public class CustomMetaObjectHandler implements MetaObjectHandler {
//
//    /**
//     * 删除标志
//     */
//    private static final String DELETE_FLAG = "deleteFlag";
//
//    /**
//     * 创建人
//     */
//    private static final String CREATE_USER = "createUser";
//
//    /**
//     * 创建时间
//     */
//    private static final String CREATE_TIME = "createTime";
//
//    /**
//     * 更新人
//     */
//    private static final String UPDATE_USER = "updateUser";
//
//    /**
//     * 更新时间
//     */
//    private static final String UPDATE_TIME = "updateTime";
//
//    @Override
//    public void insertFill(MetaObject metaObject) {
//        //为空则设置deleteFlag
//        Object deleteFlag = metaObject.getValue(DELETE_FLAG);
//        if (ObjectUtil.isNull(deleteFlag)) {
//            setFieldValByName(DELETE_FLAG, 0, metaObject);
//        }
//        //为空则设置createUser
//        Object createUser = metaObject.getValue(CREATE_USER);
//        if (ObjectUtil.isNull(createUser)) {
//            setFieldValByName(CREATE_USER, this.getUserId(), metaObject);
//        }
//        //为空则设置createTime
//        Object createTime = metaObject.getValue(CREATE_TIME);
//        if (ObjectUtil.isNull(createTime)) {
//            setFieldValByName(CREATE_TIME, DateTime.now(), metaObject);
//        }
//    }
//
//    @Override
//    public void updateFill(MetaObject metaObject) {
//        //设置updateUser
//        setFieldValByName(UPDATE_USER, this.getUserId(), metaObject);
//        //设置updateTime
//        setFieldValByName(UPDATE_TIME, DateTime.now(), metaObject);
//    }
//
//    /**
//     * 获取用户id
//     */
//    private String getUserId() {
//        try {
//            String userId = null;
//            if (ObjectUtil.isNotEmpty(userId)) {
//                return userId;
//            } else {
//                return "-1";
//            }
//        } catch (Exception e) {
//            return "-1";
//        }
//
//    }
//}
