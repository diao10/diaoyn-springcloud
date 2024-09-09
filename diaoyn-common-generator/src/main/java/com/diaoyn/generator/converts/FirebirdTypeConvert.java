package com.diaoyn.generator.converts;

import com.diaoyn.generator.ITypeConvert;
import com.diaoyn.generator.TypeConverts;
import com.diaoyn.generator.enums.DbColumnType;

import static com.diaoyn.generator.TypeConverts.contains;
import static com.diaoyn.generator.TypeConverts.containsAny;
import static com.diaoyn.generator.enums.DbColumnType.*;

/**
 * MYSQL 数据库字段类型转换
 *
 * @author hubin, hanchunlin
 * @since 2017-01-20
 */
public class FirebirdTypeConvert implements ITypeConvert {
    public static final FirebirdTypeConvert INSTANCE = new FirebirdTypeConvert();

    /**
     * {@inheritDoc}
     */
    @Override
    public DbColumnType processTypeConvert(String fieldType) {
        return TypeConverts.use(fieldType)
                .test(containsAny("cstring", "text").then(STRING))
                .test(contains("short").then(SHORT))
                .test(contains("long").then(LONG))
                .test(contains("float").then(FLOAT))
                .test(contains("double").then(DOUBLE))
                .test(contains("blob").then(BLOB))
                .test(contains("int64").then(LONG))
//            .test(containsAny("date", "time", "year").then(t -> toDateType(config, t)))
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
//            case ONLY_DATE:
//                return DbColumnType.DATE;
//            case SQL_PACK:
//                switch (type) {
//                    case "date":
//                    case "year":
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
//                    case "year":
//                        return DbColumnType.YEAR;
//                    default:
//                        return DbColumnType.LOCAL_DATE_TIME;
//                }
//        }
//        return STRING;
//    }

}
