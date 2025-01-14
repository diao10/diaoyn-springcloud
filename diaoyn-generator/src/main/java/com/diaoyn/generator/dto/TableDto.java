package com.diaoyn.generator.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 表实体类
 *
 * @author diaoyn
 * @ClassName TableDto
 * @Date 2024/9/6 11:10
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TableDto {

    /**
     * 表名称
     */
    private String tableName;

    /**
     * 表注释
     */
    private String tableComment;

    /**
     * 表名称（驼峰）
     */
    private String tableNameCamelCase;

    /**
     * 业务名(就是去掉了前缀)
     */
    private String bizName;

    /**
     * 表名称（首字母大写驼峰）
     */
    private String tableNameCamelCaseFirstUpper;

}
