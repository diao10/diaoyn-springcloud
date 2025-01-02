package com.diaoyn.provider.job;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.io.IORuntimeException;
import cn.hutool.core.io.IoUtil;
import cn.hutool.core.util.RuntimeUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.core.util.ZipUtil;
import com.alibaba.druid.pool.DruidDataSource;
import com.baomidou.dynamic.datasource.DynamicRoutingDataSource;
import com.baomidou.dynamic.datasource.ds.ItemDataSource;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.sql.DataSource;
import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.util.Date;

/**
 * @author diaoyn
 * @ClassName BackupJob
 * @Date 2024/12/31 10:44
 */
@Slf4j
@Component
public class BackupJob {

    @Resource
    DynamicRoutingDataSource dynamicRoutingDataSource;

    @Scheduled(cron = "0 0 2 * * ?")
    @SneakyThrows
    public void execute() {
        log.info("--------------备份数据开始-------------------" + DateUtil.now());

        String cmd = "mysqldump";
        try {
            RuntimeUtil.execForStr(cmd);
        } catch (IORuntimeException ignore) {
//            windows默认地址
            cmd = "C:\\Program Files\\MySQL\\MySQL Server 8.0\\bin\\mysqldump";
            try {
                RuntimeUtil.execForStr(cmd);
            } catch (IORuntimeException e) {
                log.error("备份失败：" + e.getMessage());
                log.info("--------------备份数据结束-------------------" + DateUtil.now());
                return;
            }
        }
        DataSource dataSource = dynamicRoutingDataSource.getDataSource("master");
        DruidDataSource druidDataSource = ((DruidDataSource) (((ItemDataSource) dataSource).getDataSource()));
        String username = druidDataSource.getUsername();
        String password = druidDataSource.getPassword();
        String url = druidDataSource.getUrl();
        String host = StrUtil.subBetween(url, "//", ":");
        String port = StrUtil.subAfter(url, "//", false);
        port = StrUtil.subBetween(port, ":", "/");
        String database = StrUtil.subBefore(url, "?", false);
        database = StrUtil.subAfter(database, "/", true);
        //找目录
        String parentDir = FileUtil.file("").getAbsolutePath();
        parentDir = StrUtil.subBefore(parentDir, FileUtil.JAR_PATH_EXT, false);
        parentDir = StrUtil.subBefore(parentDir, File.separator, true);
        File file =
                FileUtil.file(parentDir + "/backup/" + database + "-" + DateUtil.today() + ".db");
        FileUtil.mkdir(file.getParentFile());
        Process process = RuntimeUtil.exec(cmd +
                " --host " + host +
                " --port " + port +
                " -u" + username +
                " -p" + password +
                " --databases " + database +
                " --compact");
        String line;
        BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
        while ((line = reader.readLine()) != null) {
            FileUtil.appendUtf8String(line + "\n", file);
        }
        IoUtil.close(reader);
        process.destroy();
        ZipUtil.zip(file);
        FileUtil.del(file);
        for (File sqlFile : FileUtil.loopFiles(file.getParentFile())) {
            Date modified = new Date(sqlFile.lastModified());
            if (DateUtil.betweenDay(new Date(), modified, true) > 10) {
                FileUtil.del(sqlFile);
            }
        }
        log.info("--------------备份数据结束-------------------" + DateUtil.now());
    }

}
