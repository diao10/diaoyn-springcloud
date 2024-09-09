package com.common.generator.converts;

import com.common.generator.ITypeConvert;
import com.common.generator.TypeConverts;
import com.common.generator.enums.DbColumnType;

import static com.common.generator.TypeConverts.contains;
import static com.common.generator.TypeConverts.containsAny;
import static com.common.generator.enums.DbColumnType.*;

/**
 * SQLServer 字段类型转换
 *
 * @author hubin, hanchunlin
 * @since 2017-01-20
 */
public class SqlServerTypeConvert implements ITypeConvert {

    public static final SqlServerTypeConvert INSTANCE = new SqlServerTypeConvert();

    /**
     * {@inheritDoc}
     */
    @Override
    public DbColumnType processTypeConvert(String fieldType) {
        return TypeConverts.use(fieldType)
                .test(containsAny("char", "xml", "text").then(STRING))
                .test(contains("bigint").then(LONG))
                .test(contains("int").then(INTEGER))
//            .test(containsAny("date", "time").then(t -> toDateType(config, t)))
                .test(contains("bit").then(BOOLEAN))
                .test(containsAny("decimal", "numeric").then(DOUBLE))
                .test(contains("money").then(BIG_DECIMAL))
                .test(containsAny("binary", "image").then(BYTE_ARRAY))
                .test(containsAny("float", "real").then(FLOAT))
                .or(STRING);
    }

    /**
     * 转换为日期类型
     *
     * @param config 配置信息
     * @param fieldType   类型
     * @return 返回对应的列类型
     */
//    public static DbColumnType toDateType(GlobalConfig config, String fieldType) {
//        switch (config.getDateType()) {
//            case SQL_PACK:
//                switch (fieldType) {
//                    case "date":
//                        return DATE_SQL;
//                    case "time":
//                        return TIME;
//                    default:
//                        return TIMESTAMP;
//                }
//            case TIME_PACK:
//                switch (fieldType) {
//                    case "date":
//                        return LOCAL_DATE;
//                    case "time":
//                        return LOCAL_TIME;
//                    default:
//                        return LOCAL_DATE_TIME;
//                }
//            default:
//                return DATE;
//        }
//    }
}
