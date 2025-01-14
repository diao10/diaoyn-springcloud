package com.diaoyn.generator;

import com.diaoyn.generator.enums.DbColumnType;

/**
 * 数据库字段类型转换
 *
 * @author hubin
 * @author hanchunlin
 * @since 2017-01-20
 */
public interface ITypeConvert {

    /**
     * 执行类型转换
     *
     * @param fieldType 字段类型
     * @return ignore
     */
    DbColumnType processTypeConvert(String fieldType);

}
