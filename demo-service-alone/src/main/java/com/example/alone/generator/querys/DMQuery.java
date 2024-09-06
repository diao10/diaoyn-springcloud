package com.example.alone.generator.querys;

/**
 * DM 表数据查询
 *
 * @author halower, daiby
 * @since 2019-06-27
 */
public class DMQuery extends AbstractDbQuery {

    @Override
    public String tablesSql() {
        return "SELECT * FROM (SELECT DISTINCT T1.TABLE_NAME AS TABLE_NAME,T2.COMMENTS AS TABLE_COMMENT FROM " +
                "USER_TAB_COLUMNS T1 " +
                "INNER JOIN USER_TAB_COMMENTS T2 ON T1.TABLE_NAME = T2.TABLE_NAME and owner='%s') WHERE 1=1 ";
    }

    @Override
    public String tablesSql(String... tableNames) {
        return null;
    }

    @Override
    public String tableFieldsSql(String schema, String tableName) {
        return
                "SELECT T2.COLUMN_NAME,T1.COMMENTS," +
                        "CASE WHEN T2.DATA_TYPE='NUMBER' THEN (CASE WHEN T2.DATA_PRECISION IS NULL THEN T2.DATA_TYPE " +
                        "WHEN NVL(T2.DATA_SCALE, 0) > 0 THEN T2.DATA_TYPE||'('||T2.DATA_PRECISION||','||T2" +
                        ".DATA_SCALE||')' ELSE T2.DATA_TYPE||'('||T2.DATA_PRECISION||')' END) ELSE T2.DATA_TYPE END " +
                        "DATA_TYPE ," +
                        "CASE WHEN CONSTRAINT_TYPE='P' THEN 'PRI' END AS KEY " +
                        "FROM USER_COL_COMMENTS T1, USER_TAB_COLUMNS T2, " +
                        "(SELECT T4.TABLE_NAME, T4.COLUMN_NAME ,T5.CONSTRAINT_TYPE " +
                        "FROM USER_CONS_COLUMNS T4, USER_CONSTRAINTS T5 " +
                        "WHERE T4.CONSTRAINT_NAME = T5.CONSTRAINT_NAME " +
                        "AND T5.CONSTRAINT_TYPE = 'P')T3 " +
                        "WHERE T1.TABLE_NAME = T2.TABLE_NAME AND " +
                        "T1.COLUMN_NAME=T2.COLUMN_NAME AND " +
                        "T1.TABLE_NAME = T3.TABLE_NAME(+) AND " +
                        "T1.COLUMN_NAME=T3.COLUMN_NAME(+)   AND " +
                        "T1.TABLE_NAME = '" + tableName + "' and owner='" + schema + "' " +
                        "ORDER BY T2.TABLE_NAME,T2.COLUMN_ID";
    }

    @Override
    public String tableName() {
        return "TABLE_NAME";
    }

    @Override
    public String tableComment() {
        return "TABLE_COMMENT";
    }

    @Override
    public String fieldName() {
        return "COLUMN_NAME";
    }

    @Override
    public String fieldType() {
        return "DATA_TYPE";
    }

    @Override
    public String fieldComment() {
        return "COMMENTS";
    }

    @Override
    public String fieldKey() {
        return "KEY";
    }
}
