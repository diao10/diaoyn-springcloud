package com.common.generator.enums;

import lombok.Getter;

/**
 * 数据库类型
 * @author diaoyn
 * @ClassName DbType
 * @Date 2024/9/6 10:32
 */
@Getter
public enum DbType {
    MYSQL("mysql", "MySql数据库"),
    MARIADB("mariadb", "MariaDB数据库"),
    ORACLE("oracle", "Oracle11g及以下数据库(高版本推荐使用ORACLE_NEW)"),
    ORACLE_12C("oracle12c", "Oracle12c+数据库"),
    DB2("db2", "DB2数据库"),
    H2("h2", "H2数据库"),
    HSQL("hsql", "HSQL数据库"),
    SQLITE("sqlite", "SQLite数据库"),
    POSTGRE_SQL("postgresql", "Postgre数据库"),
    SQL_SERVER2005("sqlserver2005", "SQLServer2005数据库"),
    SQL_SERVER("sqlserver", "SQLServer数据库"),
    DM("dm", "达梦数据库"),
    XU_GU("xugu", "虚谷数据库"),
    KINGBASE_ES("kingbasees", "人大金仓数据库"),
    PHOENIX("phoenix", "Phoenix HBase数据库"),
    GAUSS("zenith", "Gauss 数据库"),
    CLICK_HOUSE("clickhouse", "clickhouse 数据库"),
    GBASE("gbase", "南大通用(华库)数据库"),
    GBASE_8S("gbase-8s", "南大通用数据库 GBase 8s"),
    /** @deprecated */
    @Deprecated
    GBASEDBT("gbasedbt", "南大通用数据库"),
    /** @deprecated */
    @Deprecated
    GBASE_INFORMIX("gbase 8s", "南大通用数据库 GBase 8s"),
    GBASE8S_PG("gbase8s-pg", "南大通用数据库 GBase 8s兼容pg"),
    GBASE_8C("gbase8c", "南大通用数据库 GBase 8c"),
    SINODB("sinodb", "星瑞格数据库"),
    OSCAR("oscar", "神通数据库"),
    SYBASE("sybase", "Sybase ASE 数据库"),
    OCEAN_BASE("oceanbase", "OceanBase 数据库"),
    FIREBIRD("Firebird", "Firebird 数据库"),
    HIGH_GO("highgo", "瀚高数据库"),
    CUBRID("cubrid", "CUBRID数据库"),
    SUNDB("sundb", "SUNDB数据库"),
    SAP_HANA("hana", "SAP_HANA数据库"),
    IMPALA("impala", "impala数据库"),
    VERTICA("vertica", "vertica数据库"),
    XCloud("xcloud", "行云数据库"),
    REDSHIFT("redshift", "亚马逊redshift数据库"),
    OPENGAUSS("openGauss", "华为 opengauss 数据库"),
    TDENGINE("TDengine", "TDengine数据库"),
    INFORMIX("informix", "Informix数据库"),
    UXDB("uxdb", "优炫数据库"),
    LEALONE("lealone", "Lealone数据库"),
    TRINO("trino", "Trino数据库"),
    PRESTO("presto", "Presto数据库"),
    OTHER("other", "其他数据库");

    private final String db;
    private final String desc;

    public static DbType getDbType(String dbType) {
        DbType[] var1 = values();
        int var2 = var1.length;

        for(int var3 = 0; var3 < var2; ++var3) {
            DbType type = var1[var3];
            if (type.db.equalsIgnoreCase(dbType)) {
                return type;
            }
        }

        return OTHER;
    }

    public boolean mysqlSameType() {
        return this == MYSQL || this == MARIADB || this == GBASE || this == OSCAR || this == XU_GU || this == CLICK_HOUSE || this == OCEAN_BASE || this == CUBRID || this == SUNDB;
    }

    public boolean oracleSameType() {
        return this == ORACLE || this == DM || this == GAUSS;
    }

    public boolean postgresqlSameType() {
        return this == POSTGRE_SQL || this == H2 || this == LEALONE || this == SQLITE || this == HSQL || this == KINGBASE_ES || this == PHOENIX || this == SAP_HANA || this == IMPALA || this == HIGH_GO || this == VERTICA || this == REDSHIFT || this == OPENGAUSS || this == TDENGINE || this == UXDB || this == GBASE8S_PG || this == GBASE_8C;
    }

    private DbType(final String db, final String desc) {
        this.db = db;
        this.desc = desc;
    }
}
