package com.example.alone.generator;

import cn.hutool.db.*;
import cn.hutool.db.ds.simple.SimpleDataSource;
import com.example.alone.generator.dto.FieldDto;
import com.example.alone.generator.dto.TableDto;
import com.example.alone.generator.enums.DbType;
import jakarta.validation.constraints.NotNull;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * @author diaoyn
 * @ClassName DataSourceHuTool
 * @Date 2024/9/6 10:40
 */
public class DataSourceHuTool {

    private static Db db;

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


    public static List<TableDto> getTable(String url, String user, String password, String... tableNames) {
        try {
            getBb(url, user, password);
            GlobalDbConfig.setCaseInsensitive(true);
            IDbQuery query = getDbQuery(url);
            List<Entity> queryList = db.query(query.tablesSql(tableNames));
            List<TableDto> resultList = new ArrayList<>();
            for (Entity entity : queryList) {
                TableDto dto = TableDto.builder()
                        .tableName(entity.getStr(query.tableName()))
                        .tableComment(entity.getStr(query.tableComment()))
                        .build();
                resultList.add(dto);
            }
            return resultList;
        } catch (SQLException e) {
            throw new RuntimeException("sql执行失败：" + e);
        }
    }


    public static List<FieldDto> getField(String url, String user, String password, String tableName) {
        try {
            getBb(url, user, password);
            GlobalDbConfig.setCaseInsensitive(true);
            IDbQuery query = getDbQuery(url);
            List<Entity> queryList = db.query(query.tableFieldsSql(null, tableName));
            List<FieldDto> resultList = new ArrayList<>();
            for (Entity entity : queryList) {
                FieldDto dto = FieldDto.builder()
                        .fieldName(entity.getStr(query.fieldName()))
                        .fieldType(entity.getStr(query.fieldType()))
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

    public static IDbQuery getDbQuery(String url) {
        DbType dbType = getDbType(url);
        DbQueryRegistry dbQueryRegistry = new DbQueryRegistry();
        // 默认 MYSQL
        return Optional.ofNullable(dbQueryRegistry.getDbQuery(dbType))
                .orElseGet(() -> dbQueryRegistry.getDbQuery(DbType.MYSQL));
    }

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
