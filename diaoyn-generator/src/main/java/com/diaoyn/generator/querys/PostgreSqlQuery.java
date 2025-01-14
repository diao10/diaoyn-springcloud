package com.diaoyn.generator.querys;


import cn.hutool.core.util.StrUtil;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * PostgreSql 表数据查询
 *
 * @author hubin
 * @since 2018-01-16
 */
public class PostgreSqlQuery extends AbstractDbQuery {

    @Override
    public String tablesSql() {
        return "SELECT A.tablename, obj_description(relfilenode, 'pg_class') AS comments FROM pg_tables A, pg_class B" +
                " WHERE A.schemaname='%s' AND A.tablename = B.relname";
    }

    @Override
    public String tablesSql(String... tableNames) {
        return null;
    }

    @Override
    public String tableFieldsSql(String schema, String tableName) {
        return "SELECT\n" +
                "   A.attname AS name,format_type (A.atttypid,A.atttypmod) AS type,col_description (A.attrelid,A" +
                ".attnum) AS comment,\n" +
                "\t D.column_default,\n" +
                "   CASE WHEN length(B.attname) > 0 THEN 'PRI' ELSE '' END AS key\n" +
                "FROM\n" +
                "   pg_attribute A\n" +
                "LEFT JOIN (\n" +
                "    SELECT\n" +
                "        pg_attribute.attname\n" +
                "    FROM\n" +
                "        pg_index,\n" +
                "        pg_class,\n" +
                "        pg_attribute\n" +
                "    WHERE\n" +
                "        pg_class.oid ='" + tableName + "' :: regclass\n" +
                "    AND pg_index.indrelid = pg_class.oid\n" +
                "    AND pg_attribute.attrelid = pg_class.oid\n" +
                "    AND pg_attribute.attnum = ANY (pg_index.indkey)\n" +
                ") B ON A.attname = b.attname\n" +
                "INNER JOIN pg_class C on A.attrelid = C.oid\n" +
                "INNER JOIN information_schema.columns D on A.attname = D.column_name\n" +
                "WHERE A.attrelid ='" + tableName + "' :: regclass AND A.attnum> 0 AND NOT A.attisdropped AND D" +
                ".table_name = '" + tableName + "'\n" +
                " and table_schema='" + schema + "'\n" +
                "ORDER BY A.attnum;";
    }

    @Override
    public String tableName() {
        return "tablename";
    }

    @Override
    public String tableComment() {
        return "comments";
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
        return "key";
    }

    @Override
    public boolean isKeyIdentity(ResultSet results) throws SQLException {
        return StrUtil.isNotBlank(results.getString("column_default")) && results.getString("column_default").contains("nextval");
    }
}
