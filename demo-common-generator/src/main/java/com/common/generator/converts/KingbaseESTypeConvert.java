package com.common.generator.converts;

import com.common.generator.ITypeConvert;
import com.common.generator.TypeConverts;
import com.common.generator.enums.DbColumnType;

import static com.common.generator.TypeConverts.contains;
import static com.common.generator.TypeConverts.containsAny;
import static com.common.generator.enums.DbColumnType.*;

/**
 * KingbaseES 字段类型转换
 *
 * @author kingbase, hanchunlin
 * @since 2019-10-12
 */
public class KingbaseESTypeConvert implements ITypeConvert {
    public static final KingbaseESTypeConvert INSTANCE = new KingbaseESTypeConvert();

    /**
     * @param fieldType 字段类型
     * @return 返回对应的字段类型
     */
    @Override
    public DbColumnType processTypeConvert(String fieldType) {
        return TypeConverts.use(fieldType)
                .test(containsAny("char", "text", "json", "enum").then(STRING))
                .test(contains("bigint").then(LONG))
                .test(contains("int").then(INTEGER))
//            .test(containsAny("date", "time").then(p -> toDateType(globalConfig, p)))
                .test(containsAny("bit", "boolean").then(BOOLEAN))
                .test(containsAny("decimal", "numeric").then(BIG_DECIMAL))
                .test(contains("clob").then(CLOB))
                .test(contains("blob").then(BYTE_ARRAY))
                .test(contains("float").then(FLOAT))
                .test(contains("double").then(DOUBLE))
                .or(STRING);
    }

//    /**
//     * 转换为日期类型
//     *
//     * @param config 配置信息
//     * @param type   类型
//     * @return 返回对应的列类型
//     */
//    private DbColumnType toDateType(GlobalConfig config, String type) {
//        DateType dateType = config.getDateType();
//        if (dateType == DateType.SQL_PACK) {
//            switch (type) {
//                case "date":
//                    return DATE_SQL;
//                case "time":
//                    return TIME;
//                default:
//                    return TIMESTAMP;
//            }
//        } else if (dateType == DateType.TIME_PACK) {
//            switch (type) {
//                case "date":
//                    return LOCAL_DATE;
//                case "time":
//                    return LOCAL_TIME;
//                default:
//                    return LOCAL_DATE_TIME;
//            }
//        }
//        return DbColumnType.DATE;
//    }

}
