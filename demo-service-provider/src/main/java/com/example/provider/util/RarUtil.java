package com.example.provider.util;


import net.sf.sevenzipjbinding.*;
import net.sf.sevenzipjbinding.impl.RandomAccessFileInStream;
import net.sf.sevenzipjbinding.simple.ISimpleInArchive;
import net.sf.sevenzipjbinding.simple.ISimpleInArchiveItem;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.RandomAccessFile;
import java.util.Arrays;

/**
 * rar解压
 *
 * @author diaoyn
 * @ClassName RarUtil
 * @Date 2024/8/20 16:02
 */
public class RarUtil {

    /**
     * @param rarDir   rar解压文件路径包含文件名称
     * @param outDir   rar存储文件路径不含文件名称
     * @param passWord 密码
     * @return File
     */
    public static File unRar(String rarDir, String outDir, String passWord) {
        File file = new File(outDir);
        RandomAccessFile randomAccessFile = null;
        IInArchive inArchive = null;
        try {
            // 第一个参数是需要解压的压缩包路径，第二个参数参考JdkAPI文档的RandomAccessFile
            randomAccessFile = new RandomAccessFile(rarDir, "r");
            if (StringUtils.isNotBlank(passWord)) {
                inArchive = SevenZip.openInArchive(null, new RandomAccessFileInStream(randomAccessFile), passWord);
            } else {
                inArchive = SevenZip.openInArchive(null, new RandomAccessFileInStream(randomAccessFile));
            }
            ISimpleInArchive simpleInArchive = inArchive.getSimpleInterface();
            for (final ISimpleInArchiveItem item : simpleInArchive.getArchiveItems()) {
                //PDF是否
                if (!item.isFolder()) {
                    ExtractOperationResult result;
                    File outFile = new File(file.getAbsolutePath() + "/" + item.getPath());
                    File parent = outFile.getParentFile();
                    if ((!parent.exists()) && (!parent.mkdirs())) {
                        continue;
                    }
                    if (StringUtils.isNotBlank(passWord)) {
                        result = item.extractSlow(data -> {
                            try {
                                IOUtils.write(data, new FileOutputStream(outFile, true));
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            return data.length; // Return amount of consumed
                        }, passWord);
                    } else {
                        result = item.extractSlow(data -> {
                            try {
                                IOUtils.write(data, new FileOutputStream(outFile, true));
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            return data.length; // Return amount of consumed
                        });
                    }

                    if (result != ExtractOperationResult.OK) {
                        break;
                    }
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (inArchive != null) {
                    inArchive.close();
                    randomAccessFile.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return file;
    }

    /**
     * @param rarDir   rar解压文件路径包含文件名称
     * @param passWord 密码
     * @return File
     */
    public static File unRar(String rarDir, String passWord) {
        return unRar(rarDir, new File(rarDir).getParent(), passWord);
    }

    /**
     * @param rarDir rar解压文件路径包含文件名称
     * @return File
     */
    public static File unRar(String rarDir) {
        return unRar(rarDir, new File(rarDir).getParent(), null);
    }


    /**
     * 创建RAR5判断方式是否检测密码方式
     *
     * @param rarFilePath rarFilePath
     * @return boolean
     */
    public static boolean checkPwdRar5(String rarFilePath) {
        IInStream inStream = null;
        IInArchive inArchive = null;
        try {
            // 创建RAR文件对象
            File rarFile = new File(rarFilePath);

            // 创建RandomAccessFile用于读取RAR文件
            RandomAccessFile randomAccessFile = new RandomAccessFile(rarFile, "r");

            // 创建RandomAccessFileInStream
            inStream = new RandomAccessFileInStream(randomAccessFile);

            // 打开压缩文件
            inArchive = SevenZip.openInArchive(null, inStream);

            // 获取压缩文件中的所有项
            ISimpleInArchive simpleInArchive = inArchive.getSimpleInterface();
            ISimpleInArchiveItem[] archiveItems = simpleInArchive.getArchiveItems();


            // 检查文件是否有密码
            boolean hasPassword = Arrays.stream(archiveItems)
                    .anyMatch(item -> {
                        try {
                            return item.isEncrypted();
                        } catch (SevenZipException e) {
                            throw new RuntimeException(e);
                        }
                    });

            // 输出检测结果
            if (hasPassword) {
                System.out.println("RAR文件带有密码！");
                return true;
            } else {
                System.out.println("RAR文件没有密码。");
                return false;
            }

        } catch (Exception e) {
            System.out.println("检测rar5异常" + e.getMessage());
            return false;
        } finally {
            // 关闭RandomAccessFileInStream和压缩文件
            if (inArchive != null) {
                try {
                    inArchive.close();
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
            if (inStream != null) {
                try {
                    inStream.close();
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }
}
