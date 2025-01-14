package com.diaoyn.generator.converts;

import com.diaoyn.generator.enums.DbColumnType;

import static com.diaoyn.generator.enums.DbColumnType.*;

/**
 * java类型转ts类型
 *
 * @author diaoyn
 * @ClassName JavaToTsConvert
 * @Date 2024/10/8 18:04
 */
public class JavaToTsConvert {

    public static String processConvert(DbColumnType dbColumnType) {
        if (INTEGER == dbColumnType || LONG == dbColumnType || DOUBLE == dbColumnType || FLOAT == dbColumnType) {
            return "number";
        }
        return "string";
    }
}
