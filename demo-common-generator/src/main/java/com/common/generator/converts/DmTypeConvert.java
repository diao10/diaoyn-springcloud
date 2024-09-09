package com.common.generator.converts;

import com.common.generator.ITypeConvert;
import com.common.generator.TypeConverts;
import com.common.generator.enums.DbColumnType;

import static com.common.generator.TypeConverts.contains;
import static com.common.generator.TypeConverts.containsAny;
import static com.common.generator.enums.DbColumnType.*;

/**
 * DM 字段类型转换
 *
 * @author halower, hanchunlin, daiby
 * @since 2019-06-27
 */
public class DmTypeConvert implements ITypeConvert {
    public static final DmTypeConvert INSTANCE = new DmTypeConvert();

    /**
     * 字符数据类型: CHAR,CHARACTER,VARCHAR
     * <p>
     * 数值数据类型: NUMBER,NUMERIC,DECIMAL,DEC,MONEY,BIT,BOOL,BOOLEAN,INTEGER,INT,BIGINT,TINYINT,BYTE,SMALLINT,BINARY,
     * VARBINARY
     * <p>
     * 近似数值数据类型: FLOAT
     * <p>
     * DOUBLE, DOUBLE PRECISION,REAL
     * <p>
     * 日期时间数据类型
     * <p>
     * 多媒体数据类型: TEXT,LONGVARCHAR,CLOB,BLOB,IMAGE
     *
     * @param fieldType 字段类型
     * @return 对应的数据类型
     */
    @Override
    public DbColumnType processTypeConvert(String fieldType) {
        return TypeConverts.use(fieldType)
                .test(containsAny("char", "text").then(STRING))
                .test(contains("number").then(DmTypeConvert::toNumberType))
                .test(containsAny("numeric", "dec", "money").then(BIG_DECIMAL))
                .test(containsAny("bit", "bool").then(BOOLEAN))
                .test(contains("bigint").then(BIG_INTEGER))
                .test(containsAny("int", "byte").then(INTEGER))
                .test(contains("binary").then(BYTE_ARRAY))
                .test(contains("float").then(FLOAT))
                .test(containsAny("double", "real").then(DOUBLE))
                .test(containsAny("date", "time").then(DATE))
                .test(contains("clob").then(CLOB))
                .test(contains("blob").then(BLOB))
                .test(contains("image").then(BYTE_ARRAY))
                .or(STRING);
    }

    private static DbColumnType toNumberType(String typeName) {
        if (typeName.matches("number\\([0-9]\\)")) {
            return DbColumnType.INTEGER;
        } else if (typeName.matches("number\\(1[0-8]\\)")) {
            return DbColumnType.LONG;
        }
        return DbColumnType.BIG_DECIMAL;
    }
}
