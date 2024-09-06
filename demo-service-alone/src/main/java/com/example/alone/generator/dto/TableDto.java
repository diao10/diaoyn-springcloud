package com.example.alone.generator.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
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

}
