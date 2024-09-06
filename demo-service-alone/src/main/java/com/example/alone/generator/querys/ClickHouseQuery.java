package com.example.alone.generator.querys;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * ClickHouse 表数据查询
 *
 * @author gaosheng
 * @since 2021-03-10
 */
public class ClickHouseQuery extends AbstractDbQuery {

    @Override
    public String tablesSql() {
        return "SELECT * FROM system.tables WHERE 1=1 ";
    }

    @Override
    public String tablesSql(String... tableNames) {
        return null;
    }

    @Override
    public String tableFieldsSql(String schema, String tableName) {
        return "select * from system.columns where table='" + tableName + "'";
    }

    @Override
    public String tableName() {
        return "name";
    }

    @Override
    public String tableComment() {
        return "comment";
    }

    @Override
    public String fieldName() {
        return "name";
    }


    @Override
    public String fieldType() {
        return "type";
    }


    @Override
    public String fieldComment() {
        return "comment";
    }


    @Override
    public String fieldKey() {
        return "is_in_primary_key";
    }

    @Override
    public boolean isKeyIdentity(ResultSet results) throws SQLException {
        return "1".equals(results.getString("is_in_primary_key"));
    }

}
