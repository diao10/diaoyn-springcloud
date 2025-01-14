package com.diaoyn.generator.converts;

import com.diaoyn.generator.ITypeConvert;
import com.diaoyn.generator.TypeConverts;
import com.diaoyn.generator.enums.DbColumnType;

import static com.diaoyn.generator.TypeConverts.contains;
import static com.diaoyn.generator.TypeConverts.containsAny;
import static com.diaoyn.generator.enums.DbColumnType.*;

/**
 * MYSQL 数据库字段类型转换
 * bit类型数据转换 bit(1) -> Boolean类型  bit(2->64)  -> Byte类型
 *
 * @author hubin, hanchunlin, xiaoliang
 * @since 2017-01-20
 */
public class MySqlTypeConvert implements ITypeConvert {
    public static final MySqlTypeConvert INSTANCE = new MySqlTypeConvert();

    @Override
    public DbColumnType processTypeConvert(String fieldType) {
        return TypeConverts.use(fieldType)
                .test(contains("point").then(BYTE_ARRAY))
                .test(containsAny("char", "text", "json", "enum").then(STRING))
                .test(contains("bigint").then(LONG))
                .test(containsAny("tinyint(1)", "bit(1)").then(BOOLEAN))
                .test(contains("bit").then(BYTE))
                .test(contains("int").then(INTEGER))
                .test(contains("decimal").then(BIG_DECIMAL))
                .test(contains("clob").then(CLOB))
                .test(contains("blob").then(BLOB))
                .test(contains("binary").then(BYTE_ARRAY))
                .test(contains("float").then(FLOAT))
                .test(contains("double").then(DOUBLE))
                .test(containsAny("date", "time", "year").then(DATE))
//                .test(containsAny("date", "time", "year").then(t -> toDateType(config, t)))
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
//        String dateType = type.replaceAll("\\(\\d+\\)", "");
//        switch (config.getDateType()) {
//            case ONLY_DATE:
//                return DbColumnType.DATE;
//            case SQL_PACK:
//                switch (dateType) {
//                    case "date":
//                    case "year":
//                        return DbColumnType.DATE_SQL;
//                    case "time":
//                        return DbColumnType.TIME;
//                    default:
//                        return DbColumnType.TIMESTAMP;
//                }
//            case TIME_PACK:
//                switch (dateType) {
//                    case "date":
//                        return DbColumnType.LOCAL_DATE;
//                    case "time":
//                        return DbColumnType.LOCAL_TIME;
//                    case "year":
//                        return DbColumnType.YEAR;
//                    default:
//                        return DbColumnType.LOCAL_DATE_TIME;
//                }
//        }
//        return STRING;
//    }
}
