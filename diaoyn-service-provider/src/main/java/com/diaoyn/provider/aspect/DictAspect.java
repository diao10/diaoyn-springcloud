package com.diaoyn.provider.aspect;

import cn.hutool.cache.Cache;
import cn.hutool.cache.CacheUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.diaoyn.common.vo.ResponseVO;
import com.diaoyn.provider.aspect.annotation.Dict;
import lombok.Builder;
import lombok.Data;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.util.*;

/**
 * @author diaoyn
 * @ClassName DictAspect
 * @Date 2024/10/25 15:02
 */
@Aspect
@Component
public class DictAspect {
    //切面获取字段后缀
    private static final String SUFFIX = "Name";
    //1小时缓存
    private static final Cache<String, Map<String, String>> DICT_CACHE = CacheUtil.newTimedCache(60 * 60 * 60 * 1000);

    @Pointcut("(@within(org.springframework.web.bind.annotation.RestController) " +
            "|| @within(org.springframework.stereotype.Controller)) " +
            "&& execution(public com.diaoyn.common.vo.ResponseVO *.*(..))")
    public void dictPointCut() {
    }

    @Data
    @Builder
    private static class DictEntity {
        private String fieldName;
        private String dicCode;
        private String dicText;
    }

    @Around("dictPointCut()")
    @SuppressWarnings("all")
    public Object around(ProceedingJoinPoint pjp) throws Throwable {
        return this.parseDictText(pjp.proceed());
    }

    private <T> Object parseDictText(Object result) {
        List<T> records = new ArrayList<>();
        if (((ResponseVO<?>) result).getData() instanceof IPage) {
            records = ((IPage) ((ResponseVO) result).getData()).getRecords();

        } else if (((ResponseVO<?>) result).getData() instanceof List) {
            records = ((ResponseVO<List>) result).getData();

        } else if (((ResponseVO<?>) result).getData() instanceof ResponseVO) {
            records.add(((ResponseVO<T>) result).getData());
        } else {
            return result;
        }
        List<DictEntity> dictEntityList = checkDict(records);
        if (CollUtil.isEmpty(dictEntityList)) {
            return result;
        }
        List<JSONObject> jsonList = new ArrayList<>();
        records.forEach(record -> {
            JSONObject json = JSONUtil.parseObj(record);
            dictEntityList.forEach(dictEntity ->
                    json.set(dictEntity.getFieldName() + SUFFIX,
                            getDictText(dictEntity, json.getStr(dictEntity.getFieldName()))));
            jsonList.add(json);
        });

        if (((ResponseVO<?>) result).getData() instanceof IPage) {
            ((IPage) ((ResponseVO) result).getData()).setRecords(jsonList);

        } else if (((ResponseVO<?>) result).getData() instanceof List) {
            ((ResponseVO<List>) result).setData(jsonList);

        } else if (((ResponseVO<?>) result).getData() instanceof ResponseVO) {
            ((ResponseVO) result).setData(jsonList.get(0));
        }
        return result;
    }


    /**
     * 根据属性获取字典值
     *
     * @param dictEntity dictEntity
     * @param value      码值
     * @return 字典值
     */
    private String getDictText(DictEntity dictEntity, String value) {

        if (ObjectUtil.isNull(dictEntity)) {
            return "";
        }
        if (StrUtil.isNotEmpty(dictEntity.getDicText())) {
            return dictEntity.getDicText();
        }
        if (!DICT_CACHE.containsKey(dictEntity.getDicCode())) {
            loadDictCache(dictEntity.getDicCode());
        }
        return DICT_CACHE.get(dictEntity.getDicCode()).getOrDefault(value, "");
    }

    /**
     * 从字典表获取数据
     */
    private void loadDictCache(String dicCode) {
        Map<String, String> map = new HashMap<>();
        DICT_CACHE.put(dicCode, map);
    }

    /**
     * 检查并获取Dict注解
     *
     * @param records records
     * @param <T>     <T>
     * @return Boolean
     */
    private static <T> List<DictEntity> checkDict(List<T> records) {
        List<DictEntity> list = new ArrayList<>();
        if (CollUtil.isEmpty(records)) {
            return list;
        }
        for (Field field : getAllFields(records.get(0))) {
            if (ObjectUtil.isNotEmpty(field.getAnnotation(Dict.class))) {
                DictEntity dictEntity = DictEntity.builder()
                        .fieldName(field.getName())
                        .dicCode(field.getAnnotation(Dict.class).dicCode())
                        .dicText(field.getAnnotation(Dict.class).dicText())
                        .build();
                list.add(dictEntity);
            }
        }
        return list;
    }


    /**
     * 获取类的所有属性，包括父类
     *
     * @param object object
     * @return Field[]
     */
    private static Field[] getAllFields(Object object) {
        return getFields(object);
    }

    /**
     * 获取类的所有属性，包括父类
     *
     * @param object object
     * @return Field[]
     */
    public static Field[] getFields(Object object) {
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