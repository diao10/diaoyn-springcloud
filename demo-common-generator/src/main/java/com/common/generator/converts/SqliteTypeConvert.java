package com.common.generator.converts;

import com.common.generator.ITypeConvert;
import com.common.generator.TypeConverts;
import com.common.generator.enums.DbColumnType;

import static com.common.generator.TypeConverts.contains;
import static com.common.generator.TypeConverts.containsAny;
import static com.common.generator.enums.DbColumnType.*;

/**
 * SQLite 字段类型转换
 *
 * @author chen_wj, hanchunlin
 * @since 2019-05-08
 */
public class SqliteTypeConvert implements ITypeConvert {
    public static final SqliteTypeConvert INSTANCE = new SqliteTypeConvert();

    /**
     * {@inheritDoc}
     * <p>
     * //     * @see MySqlTypeConvert#toDateType(GlobalConfig, String)
     */
    @Override
    public DbColumnType processTypeConvert(String fieldType) {
        return TypeConverts.use(fieldType)
                .test(contains("bigint").then(LONG))
                .test(containsAny("tinyint(1)", "boolean").then(BOOLEAN))
                .test(contains("int").then(INTEGER))
                .test(containsAny("text", "char", "enum").then(STRING))
                .test(containsAny("decimal", "numeric").then(BIG_DECIMAL))
                .test(contains("clob").then(CLOB))
                .test(contains("blob").then(BLOB))
                .test(contains("float").then(FLOAT))
                .test(contains("double").then(DOUBLE))
//            .test(containsAny("date", "time", "year").then(t -> toDateType(config, t)))
                .or(DbColumnType.STRING);
    }

}
