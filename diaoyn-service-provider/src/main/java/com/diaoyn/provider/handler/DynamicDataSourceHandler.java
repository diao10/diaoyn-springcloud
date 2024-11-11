package com.diaoyn.provider.handler;

import cn.hutool.core.util.StrUtil;
import com.alibaba.druid.pool.DruidDataSource;
import com.baomidou.dynamic.datasource.DynamicRoutingDataSource;
import com.baomidou.dynamic.datasource.ds.ItemDataSource;
import com.baomidou.dynamic.datasource.toolkit.DynamicDataSourceContextHolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;

/**
 * 数据库切换验证，新建一个druid测试连接
 *
 * @author diaoyn
 * @ClassName itemTypeEnum
 * @Date 2024/7/18 10:58
 */
@Component
public class DynamicDataSourceHandler {

    //主数据库名称main
    private final static String MAIN = "main";


    @Autowired(required = false)
    DynamicRoutingDataSource dynamicRoutingDataSource;

    /**
     * 克隆一个druid链接测试能否链接到数据库
     *
     * @return boolean
     */
    public boolean tryConnect() {
        return tryConnect(MAIN);
    }

    /**
     * 克隆一个druid链接测试能否链接到数据库
     *
     * @param ds 数据库名字
     * @return boolean
     */
    public boolean tryConnect(String ds) {
        try {
            DataSource dataSource = dynamicRoutingDataSource.getDataSource(ds);
            DruidDataSource druidDataSource2 =
                    (DruidDataSource) ((DruidDataSource) (((ItemDataSource) dataSource).getDataSource())).clone();
            druidDataSource2.setBreakAfterAcquireFailure(true);
            druidDataSource2.setConnectionErrorRetryAttempts(0);
            druidDataSource2.getConnection(2000);
            druidDataSource2.close();
            return true;
        } catch (Exception e) {
            return false;
        }
    }


    /**
     * 切换数据源
     *
     * @param ds 数据库名字
     * @return boolean
     */
    public boolean pushDataSource(String ds) {
        if (StrUtil.isEmpty(ds)) {
            return false;
        }
        if (!tryConnect(ds)) {
            return false;
        }
        DynamicDataSourceContextHolder.push(ds);
        return true;
    }

    /**
     * 移除数据源，必须跟pushDataSource方法联合使用
     */
    public void pollDataSource() {
        DynamicDataSourceContextHolder.poll();
    }

    /**
     * 清除数据源
     */
    public void clearDataSource() {
        DynamicDataSourceContextHolder.clear();
    }
}
