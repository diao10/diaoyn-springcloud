package com.diaoyn.provider.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.poi.ss.formula.functions.T;

import java.util.List;

/**
 * @author diaoyn
 * @ClassName CommonMapper
 * @Date 2024/10/14 11:04
 */
public interface CommonMapper extends BaseMapper<T> {
    /**
     * 真正的批量插入
     * @param entityList entityList
     * @return int
     */
    int insertBatch(List<T> entityList);
}
