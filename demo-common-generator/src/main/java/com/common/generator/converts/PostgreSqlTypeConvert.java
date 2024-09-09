package com.common.generator.converts;

import com.common.generator.ITypeConvert;
import com.common.generator.TypeConverts;
import com.common.generator.enums.DbColumnType;

import static com.common.generator.TypeConverts.contains;
import static com.common.generator.TypeConverts.containsAny;
import static com.common.generator.enums.DbColumnType.*;

/**
 * PostgreSQL 字段类型转换
 *
 * @author hubin, hanchunlin
 * @since 2017-01-20
 */
public class PostgreSqlTypeConvert implements ITypeConvert {
    public static final PostgreSqlTypeConvert INSTANCE = new PostgreSqlTypeConvert();

    /**
     * {@inheritDoc}
     */
    @Override
    public DbColumnType processTypeConvert(String fieldType) {
        return TypeConverts.use(fieldType)
                .test(containsAny("char", "text", "json", "enum").then(STRING))
                .test(contains("bigint").then(LONG))
                .test(contains("int").then(INTEGER))
//            .test(containsAny("date", "time").then(t -> toDateType(config, t)))
                .test(contains("bit").then(BOOLEAN))
                .test(containsAny("decimal", "numeric").then(BIG_DECIMAL))
                .test(contains("bytea").then(BYTE_ARRAY))
                .test(contains("float").then(FLOAT))
                .test(contains("double").then(DOUBLE))
                .test(contains("boolean").then(BOOLEAN))
                .or(STRING);
    }

//    /**
//     * 转换为日期类型
//     *
//     * @param config 配置信息
//     * @param type   类型
//     * @return 返回对应的列类型
//     */
//    public static DbColumnType toDateType(GlobalConfig config, String type) {
//        switch (config.getDateType()) {
//            case SQL_PACK:
//                switch (type) {
//                    case "date":
//                        return DbColumnType.DATE_SQL;
//                    case "time":
//                        return DbColumnType.TIME;
//                    default:
//                        return DbColumnType.TIMESTAMP;
//                }
//            case TIME_PACK:
//                switch (type) {
//                    case "date":
//                        return DbColumnType.LOCAL_DATE;
//                    case "time":
//                        return DbColumnType.LOCAL_TIME;
//                    default:
//                        return DbColumnType.LOCAL_DATE_TIME;
//                }
//            default:
//                return DbColumnType.DATE;
//        }
//    }
}
