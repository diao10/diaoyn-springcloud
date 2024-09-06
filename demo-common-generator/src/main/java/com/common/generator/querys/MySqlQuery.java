package com.common.generator.querys;

import cn.hutool.core.text.StrBuilder;
import cn.hutool.core.util.ArrayUtil;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * MySql 表数据查询
 *
 * @author hubin
 * @since 2018-01-16
 */
public class MySqlQuery extends AbstractDbQuery {

    @Override
    public String tablesSql() {
        return "show table status WHERE 1=1 ";
    }

    @Override
    public String tablesSql(String... tableNames) {
        if (ArrayUtil.isEmpty(tableNames)) {
            return tablesSql();
        }
        StrBuilder builder = new StrBuilder();
        builder.append("show table status WHERE ").append(tableName()).append(" in (");
        for (String tableName : tableNames) {
            builder.append("`").append(tableName).append("`");
            builder.append(",");
        }
        builder.delTo(1);
        builder.append(")");

        return builder.toString();
    }


    @Override
    public String tableFieldsSql(String schema, String tableName) {
        return "show full columns from `" + tableName + "`";
    }


    @Override
    public String tableName() {
        return "NAME";
    }


    @Override
    public String tableComment() {
        return "COMMENT";
    }


    @Override
    public String fieldName() {
        return "FIELD";
    }


    @Override
    public String fieldType() {
        return "TYPE";
    }


    @Override
    public String fieldComment() {
        return "COMMENT";
    }


    @Override
    public String fieldKey() {
        return "KEY";
    }


    @Override
    public boolean isKeyIdentity(ResultSet results) throws SQLException {
        return "auto_increment".equals(results.getString("Extra"));
    }
}
