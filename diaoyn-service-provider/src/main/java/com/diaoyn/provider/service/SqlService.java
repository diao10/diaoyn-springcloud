package com.diaoyn.provider.service;

import cn.hutool.core.util.StrUtil;
import cn.hutool.poi.excel.ExcelWriter;
import cn.hutool.poi.excel.cell.CellUtil;
import com.diaoyn.provider.mapper.SqlMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * @ClassName SqlService
 * @Author diaoyn
 * @Date 2024/6/5 13:10
 */
@Service
public class SqlService {

    @Resource
    private SqlMapper sqlMapper;

    /**
     * 导出表结构
     * @param tableSchema 数据库名称
     * @param dir         导出位置
     * @return
     */
    public String exportTable(String tableSchema, String dir) {
        if (StrUtil.isEmpty(tableSchema) || StrUtil.isEmpty(dir)) {
            return "fail";
        }
        List<Map<String, Object>> tableMaps = sqlMapper.selectTableMaps(tableSchema);
        ExcelWriter writer = new ExcelWriter(dir + tableSchema + ".xlsx");
        for (Map<String, Object> tableMap : tableMaps) {
            CellUtil.setCellValue(writer.getOrCreateCell(0, writer.getCurrentRow()), "表名",
                    writer.getStyleSet().getHeadCellStyle());
            CellUtil.setCellValue(writer.getOrCreateCell(1, writer.getCurrentRow()), tableMap.get("TABLE_NAME"),
                    writer.getStyleSet().getHeadCellStyle());
            CellUtil.setCellValue(writer.getOrCreateCell(2, writer.getCurrentRow()), tableMap.get("TABLE_COMMENT"),
                    writer.getStyleSet().getHeadCellStyle());
            writer.passCurrentRow();
            List<LinkedHashMap<String, Object>> columnMaps = sqlMapper.selectColumnMaps(tableSchema,
                    (String) tableMap.get("TABLE_NAME"));
            writer.write(columnMaps, true);
            writer.passRows(2);
        }
        writer.autoSizeColumnAll();
        writer.flush();
        writer.close();
        return "success";
    }
}
