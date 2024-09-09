package com.diaoyn.generator.dto;

import com.diaoyn.generator.enums.DbColumnType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 字段实体类
 *
 * @author diaoyn
 * @ClassName TableDto
 * @Date 2024/9/6 11:10
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FieldDto {

    /**
     * 字段名称
     */
    private String fieldName;

    /**
     * 字段类型
     */
    private String fieldType;

    /**
     * 转化java字段类型
     */
    private DbColumnType dbColumnType;

    /**
     * 字段注释
     */
    private String fieldComment;

    /**
     * 主键
     */
    private String fieldKey;
}
