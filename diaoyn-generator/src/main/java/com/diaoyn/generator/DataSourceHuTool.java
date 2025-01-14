package com.diaoyn.generator;

import cn.hutool.core.util.StrUtil;
import cn.hutool.db.*;
import cn.hutool.db.ds.simple.SimpleDataSource;
import com.diaoyn.generator.dto.FieldDto;
import com.diaoyn.generator.dto.TableDto;
import com.diaoyn.generator.enums.DbType;
import jakarta.validation.constraints.NotNull;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * 用huTool的连接数据库工具连接数据库
 *
 * @author diaoyn
 * @ClassName DataSourceHuTool
 * @Date 2024/9/6 10:40
 */
public class DataSourceHuTool {

    private static Db db;

    /**
     * 根据url获取数据库连接
     *
     * @param url      数据库连接url
     * @param user     数据库用户名
     * @param password 数据库密码
     */
    public static void getBb(String url, String user, String password) {
        try {
            if (db == null) {
                DataSource ds = new SimpleDataSource(url, user, password);
                db = DbUtil.use(ds);
            }
        } catch (DbRuntimeException e) {
            throw new RuntimeException("数据库连接失败：" + e);
        }
    }


    /**
     * 查看数据库表
     *
     * @param url        数据库连接url
     * @param username   数据库用户名
     * @param password   数据库密码
     * @param tableNames 表名
     * @return List<TableDto> 数据表列表
     */
    public static List<TableDto> getTable(String url, String username, String password, String... tableNames) {
        try {
            if (StrUtil.isEmpty(url) || StrUtil.isEmpty(username) || StrUtil.isEmpty(password)) {
                throw new RuntimeException("数据库连接信息不能为空");
            }
            getBb(url, username, password);
            GlobalDbConfig.setCaseInsensitive(true);
            IDbQuery query = getDbQuery(url);
            List<Entity> queryList = db.query(query.tablesSql(tableNames));
            List<TableDto> resultList = new ArrayList<>();
            for (Entity entity : queryList) {
                TableDto dto = TableDto.builder()
                        .tableName(entity.getStr(query.tableName()))
                        .tableComment(entity.getStr(query.tableComment()))
                        .tableNameCamelCase(StrUtil.toCamelCase(entity.getStr(query.tableName())))
                        .bizName(StrUtil.toCamelCase(StrUtil.subAfter(entity.getStr(query.tableName()),
                                StrUtil.UNDERLINE, false)))
                        .tableNameCamelCaseFirstUpper(StrUtil.upperFirst(StrUtil.toCamelCase(entity.getStr(query.tableName()))))
                        .build();
                resultList.add(dto);
            }
            return resultList;
        } catch (SQLException e) {
            throw new RuntimeException("sql执行失败：" + e);
        }
    }


    /**
     * 根据表名获取字段信息
     *
     * @param url       数据库连接url
     * @param username  数据库用户名
     * @param password  数据库密码
     * @param tableName 表名
     * @return 数据库字段列表
     */
    public static List<FieldDto> getField(String url, String username, String password, String tableName) {
        try {
            getBb(url, username, password);
            GlobalDbConfig.setCaseInsensitive(true);
            IDbQuery query = getDbQuery(url);
            ITypeConvert convert = getConvert(url);
            List<Entity> fieldList = db.query(query.tableFieldsSql(null, tableName));
            List<FieldDto> resultList = new ArrayList<>();
            for (Entity entity : fieldList) {
                FieldDto dto = FieldDto.builder()
                        .fieldName(entity.getStr(query.fieldName()))
                        .fieldType(entity.getStr(query.fieldType()))
                        .dbColumnType(convert.processTypeConvert(entity.getStr(query.fieldType())))
                        .fieldComment(entity.getStr(query.fieldComment()))
                        .fieldKey(entity.getStr(query.fieldKey()))
                        .build();
                resultList.add(dto);
            }
            return resultList;
        } catch (SQLException e) {
            throw new RuntimeException("sql执行失败：" + e);
        }
    }

    /**
     * 根据url获取数据库类型
     *
     * @param url 数据库连接url
     * @return IDbQuery
     */
    public static IDbQuery getDbQuery(String url) {
        DbType dbType = getDbType(url);
        DbQueryRegistry dbQueryRegistry = new DbQueryRegistry();
        // 默认 MYSQL
        return Optional.ofNullable(dbQueryRegistry.getDbQuery(dbType))
                .orElseGet(() -> dbQueryRegistry.getDbQuery(DbType.MYSQL));
    }

    /**
     * 根据url获取类型注册器
     *
     * @param url 数据库连接url
     * @return ITypeConvert
     */
    public static ITypeConvert getConvert(String url) {
        DbType dbType = getDbType(url);
        return TypeConverts.getTypeConvert(dbType);
    }


    /**
     * 根据url获取数据库类型
     *
     * @param url 数据库连接url
     * @return 数据库类型
     */
    private static DbType getDbType(@NotNull String url) {
        url = url.toLowerCase();
        if (url.contains(":mysql:") || url.contains(":cobar:")) {
            return DbType.MYSQL;
        } else if (url.contains(":oracle:")) {
            return DbType.ORACLE;
        } else if (url.contains(":postgresql:")) {
            return DbType.POSTGRE_SQL;
        } else if (url.contains(":sqlserver:")) {
            return DbType.SQL_SERVER;
        } else if (url.contains(":db2:")) {
            return DbType.DB2;
        } else if (url.contains(":mariadb:")) {
            return DbType.MARIADB;
        } else if (url.contains(":sqlite:")) {
            return DbType.SQLITE;
        } else if (url.contains(":h2:")) {
            return DbType.H2;
        } else if (url.contains(":lealone:")) {
            return DbType.LEALONE;
        } else if (url.contains(":kingbase:") || url.contains(":kingbase8:")) {
            return DbType.KINGBASE_ES;
        } else if (url.contains(":dm:")) {
            return DbType.DM;
        } else if (url.contains(":zenith:")) {
            return DbType.GAUSS;
        } else if (url.contains(":oscar:")) {
            return DbType.OSCAR;
        } else if (url.contains(":firebird:")) {
            return DbType.FIREBIRD;
        } else if (url.contains(":xugu:")) {
            return DbType.XU_GU;
        } else if (url.contains(":clickhouse:")) {
            return DbType.CLICK_HOUSE;
        } else if (url.contains(":sybase:")) {
            return DbType.SYBASE;
        } else {
            return DbType.OTHER;
        }
    }

}
