package com.diaoyn.generator.querys;

/**
 * DB2 表数据查询
 *
 * @author zhanyao
 * @since 2018-05-16
 */
public class DB2Query extends AbstractDbQuery {

    @Override
    public String tablesSql() {
        return "SELECT * FROM SYSCAT.TABLES where tabschema='%s'";
    }

    @Override
    public String tablesSql(String... tableNames) {
        return null;
    }


    @Override
    public String tableFieldsSql(String schema, String tableName) {
        return "SELECT * FROM syscat.columns WHERE tabschema='" + schema + "' AND tabname='" + tableName + "'";
    }


    @Override
    public String tableName() {
        return "TABNAME";
    }


    @Override
    public String tableComment() {
        return "REMARKS";
    }


    @Override
    public String fieldName() {
        return "COLNAME";
    }


    @Override
    public String fieldType() {
        return "TYPENAME";
    }


    @Override
    public String fieldComment() {
        return "REMARKS";
    }


    @Override
    public String fieldKey() {
        return "IDENTITY";
    }

}
