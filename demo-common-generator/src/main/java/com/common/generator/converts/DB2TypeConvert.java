package com.common.generator.converts;

import com.common.generator.ITypeConvert;
import com.common.generator.TypeConverts;
import com.common.generator.enums.DbColumnType;

import static com.common.generator.TypeConverts.contains;
import static com.common.generator.TypeConverts.containsAny;
import static com.common.generator.enums.DbColumnType.*;

/**
 * DB2 字段类型转换
 *
 * @author zhanyao, hanchunlin
 * @since 2018-05-16
 */
public class DB2TypeConvert implements ITypeConvert {
    public static final DB2TypeConvert INSTANCE = new DB2TypeConvert();

    /**
     * {@inheritDoc}
     */
    @Override
    public DbColumnType processTypeConvert(String fieldType) {
        return TypeConverts.use(fieldType)
                .test(containsAny("char", "text", "json", "enum").then(STRING))
                .test(contains("bigint").then(LONG))
                .test(contains("smallint").then(BASE_SHORT))
                .test(contains("int").then(INTEGER))
                .test(containsAny("date", "time", "year").then(DATE))
                .test(contains("bit").then(BOOLEAN))
                .test(contains("decimal").then(BIG_DECIMAL))
                .test(contains("clob").then(CLOB))
                .test(contains("blob").then(BLOB))
                .test(contains("binary").then(BYTE_ARRAY))
                .test(contains("float").then(FLOAT))
                .test(contains("double").then(DOUBLE))
                .or(STRING);
    }

}
