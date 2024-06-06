package com.example.provider.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;


/**
 * @author zhengxd
 * 执行原生sql语句mapper
 */
@Mapper
public interface SqlMapper extends BaseMapper<Void> {

    @Select("SELECT TABLE_NAME, TABLE_COMMENT\n" +
            "FROM information_schema.tables\n" +
            "WHERE table_schema = #{table_schema}")
    List<Map<String, Object>> selectTableMaps(@Param("table_schema") String tableSchema);


    @Select("SELECT ifnull(T2.COLUMN_NAME, '')    字段名,\n" +
            "       ifnull(T2.COLUMN_TYPE, '')    数据类型,\n" +
            "       ifnull(T2.IS_NULLABLE, '')    是否为空,\n" +
            "       ifnull(T2.COLUMN_DEFAULT, '') 默认值,\n" +
            "       ifnull(T2.COLUMN_COMMENT, '') 字段备注\n" +
            "FROM INFORMATION_SCHEMA.COLUMNS T2\n" +
            "WHERE T2.TABLE_SCHEMA = #{table_schema} and T2.TABLE_NAME  = #{table_name}")
    List<LinkedHashMap<String, Object>> selectColumnMaps(@Param("table_schema") String tableSchema,@Param("table_name") String tableName);

}
