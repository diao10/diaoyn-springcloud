package com.example.alone.generator;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * 表数据查询接口
 *
 * @author diaoyn
 * @ClassName IDbQuery
 * @Date 2024/9/6 10:27
 */
public interface IDbQuery {
    /**
     * 表信息查询 SQL
     */
    String tablesSql();

    /**
     * 根据表名称查询 SQL
     */
    String tablesSql(String... tableNames);

    /**
     * 表字段信息查询 SQL
     */
    String tableFieldsSql(String schema, String tableName);

    /**
     * 表名称
     */
    String tableName();

    /**
     * 表注释
     */
    String tableComment();

    /**
     * 字段名称
     */
    String fieldName();

    /**
     * 字段类型
     */
    String fieldType();

    /**
     * 字段注释
     */
    String fieldComment();

    /**
     * 主键字段
     */
    String fieldKey();

    /**
     * 判断主键是否为identity
     *
     * @param results ResultSet
     * @return 主键是否为identity
     * @throws SQLException ignore
     */
    boolean isKeyIdentity(ResultSet results) throws SQLException;

    /**
     * 自定义字段名称
     */
    String[] fieldCustom();
}
