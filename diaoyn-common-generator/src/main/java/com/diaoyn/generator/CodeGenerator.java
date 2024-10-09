package com.diaoyn.generator;


import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.NumberUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import cn.hutool.system.SystemUtil;
import com.diaoyn.generator.converts.JavaToTsConvert;
import com.diaoyn.generator.dto.FieldDto;
import com.diaoyn.generator.dto.TableDto;
import com.diaoyn.generator.enums.DbColumnType;
import org.beetl.core.Configuration;
import org.beetl.core.GroupTemplate;
import org.beetl.core.Template;
import org.beetl.core.resource.ClasspathResourceLoader;

import java.io.File;
import java.io.IOException;
import java.util.*;

/**
 * @author diaoyn
 * @ClassName Codeeg
 * @Date 2024/9/4 13:31
 */
public class CodeGenerator {

    /**
     * 开启 swagger 模式（默认 false 与 springdoc 不可同时使用）
     */
    private static boolean swagger = false;
    /**
     * 开启 springdoc 模式（默认 false 与 swagger 不可同时使用）
     */
    private static boolean springdoc = false;
    /**
     * 一些公用的字段
     */
    private static String CREATE_USER_KEY = "CREATE_USER";
    private static String CREATE_TIME_KEY = "CREATE_TIME";
    private static String UPDATE_USER_KEY = "UPDATE_USER";
    private static String UPDATE_TIME_KEY = "UPDATE_TIME";
    private static String DELETE_FLAG_KEY = "DEL_FLAG";

    private String url;
    private String username;
    private String password;

    private static final List<JSONObject> GEN_SQL_FILE_LIST = CollectionUtil.newArrayList(
            JSONUtil.createObj().set("name", "Mysql.sql.btl"),
            JSONUtil.createObj().set("name", "Oracle.sql.btl"));

    private static final List<JSONObject> GEN_FRONT_FILE_LIST = CollectionUtil.newArrayList(
            JSONUtil.createObj().set("name", "Api.ts.btl").set("path", "api"),
//            JSONUtil.createObj().set("name", "Api.js.btl").set("path", "api"),
//            JSONUtil.createObj().set("name", "form.vue.btl").set("path", "views"),
            JSONUtil.createObj().set("name", "index1.vue.btl").set("path", "views")
    );

    private static final List<JSONObject> GEN_MOBILE_FILE_LIST = CollectionUtil.newArrayList(
            JSONUtil.createObj().set("name", "page.json.btl"),
            JSONUtil.createObj().set("name", "Api.js.btl").set("path", "api"),
            JSONUtil.createObj().set("name", "search.vue.btl").set("path", "pages"),
            JSONUtil.createObj().set("name", "form.vue.btl").set("path", "pages"),
            JSONUtil.createObj().set("name", "more.vue.btl").set("path", "pages"),
            JSONUtil.createObj().set("name", "index.vue.btl").set("path", "pages")
    );

    private static final List<JSONObject> GEN_BACKEND_FILE_LIST = CollectionUtil.newArrayList(
            JSONUtil.createObj().set("name", "Controller.java.btl").set("path", "controller"),
            JSONUtil.createObj().set("name", "Entity.java.btl").set("path", "entity"),
//            JSONUtil.createObj().set("name", "Enum.java.btl").set("path", "enums"),
            JSONUtil.createObj().set("name", "Mapper.java.btl").set("path", "mapper"),
            JSONUtil.createObj().set("name", "Mapper.xml.btl").set("path", "mapper" + File.separator + "xml"),
            JSONUtil.createObj().set("name", "Vo.java.btl").set("path", "vo"),
//            JSONUtil.createObj().set("name", "EditParam.java.btl").set("path", "param"),
//            JSONUtil.createObj().set("name", "IdParam.java.btl").set("path", "param"),
//            JSONUtil.createObj().set("name", "PageParam.java.btl").set("path", "param"),
            JSONUtil.createObj().set("name", "Service.java.btl").set("path", "service"),
            JSONUtil.createObj().set("name", "ServiceImpl.java.btl").set("path", "service" + File.separator + "impl")
    );

    /**
     * 开启 swagger 模式
     */
    public CodeGenerator enableSwagger() {
        swagger = true;
        return this;
    }

    /**
     * 开启 springdoc 模式
     */
    public CodeGenerator enableSpringdoc() {
        springdoc = true;
        return this;
    }


    /**
     * 修改 CREATE_USER_KEY
     */
    public CodeGenerator setCreateUserKey(String key) {
        CREATE_USER_KEY = key;
        return this;
    }

    /**
     * 修改 CREATE_TIME_KEY
     */
    public CodeGenerator setCreateTimeKey(String key) {
        CREATE_TIME_KEY = key;
        return this;
    }

    /**
     * 修改 UPDATE_USER_KEY
     */
    public CodeGenerator setUpdateUserKey(String key) {
        UPDATE_USER_KEY = key;
        return this;
    }

    /**
     * 修改 UPDATE_TIME_KEY
     */
    public CodeGenerator setUpdateTimeKey(String key) {
        UPDATE_TIME_KEY = key;
        return this;
    }

    /**
     * 修改 DELETE_FLAG_KEY
     */
    public CodeGenerator setDeleteFlagKey(String key) {
        DELETE_FLAG_KEY = key;
        return this;
    }

    public CodeGenerator setDb(String url, String username, String password) {
        this.url = url;
        this.username = username;
        this.password = password;
        return this;
    }


    /**
     * 执行生成文件
     *
     * @param moduleName  模块名称
     * @param packageName 包名称
     * @param tableNames  表名称集合
     */
    public void execute(String moduleName, String packageName,
                        String... tableNames) {
        System.out.println("==========================准备生成文件...==========================");

        List<TableDto> tableDtoList = DataSourceHuTool.getTable(url, username, password, tableNames);
        for (int i = 0; i < tableDtoList.size(); i++) {
            System.out.println("==========================" +
                    NumberUtil.formatPercent((double) (i + 1) / tableDtoList.size(), 0) +
                    "==========================");
            List<FieldDto> fieldDtoList = DataSourceHuTool.getField(url, username, password,
                    tableDtoList.get(i).getTableName());

            Map<String, Object> bindMap = initBinding(packageName, tableDtoList.get(i), fieldDtoList);
            executeBackend(moduleName, packageName, tableDtoList.get(i), bindMap);
            executeFrontend(moduleName, tableDtoList.get(i), bindMap);
        }
        System.out.println("==========================文件生成完成！！！==========================");
    }

    /**
     * 生成后端代码
     *
     * @param moduleName  模块名称
     * @param packageName 包名称
     * @param tableDto    表名称
     * @param bindMap     btl里的参数映射
     */
    public static void executeBackend(String moduleName, String packageName, TableDto tableDto,
                                      Map<String, Object> bindMap) {
        try {
            GroupTemplate groupTemplate = new GroupTemplate(new ClasspathResourceLoader("backend"),
                    Configuration.defaultConfiguration());
            GEN_BACKEND_FILE_LIST.forEach(t -> {
                Template templateBackend = groupTemplate.getTemplate(t.getStr("name"));
                templateBackend.binding(bindMap);
                String userDir = SystemUtil.getUserInfo().getCurrentDir();
                String className = StrUtil.removeSuffix(t.getStr("name"), ".btl");
                if ("Entity.java.btl".equalsIgnoreCase(t.getStr("name"))) {
                    className = ".java";
                }
                String outDir = userDir;
                if (StrUtil.isNotBlank(moduleName)) {
                    outDir = outDir + moduleName
                            + File.separator + "src" + File.separator + "main" + File.separator + "java"
                            + File.separator;
                } else {
                    outDir =
                            outDir + ((ClasspathResourceLoader) groupTemplate.getResourceLoader()).getRoot() + File.separator;
                }
                outDir = outDir
                        + File.separator + StrUtil.nullToEmpty(packageName).replace(".", "/")
                        + File.separator + t.getStr("path")
                        + File.separator + tableDto.getTableNameCamelCaseFirstUpper()
                        + className;
                FileUtil.writeUtf8String(templateBackend.render(), outDir);
            });

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 生成前端代码
     *
     * @param moduleName 模块名称
     * @param tableDto   表名称
     * @param bindMap    btl里的参数映射
     */
    public static void executeFrontend(String moduleName, TableDto tableDto,
                                       Map<String, Object> bindMap) {
        try {
            GroupTemplate groupTemplate = new GroupTemplate(new ClasspathResourceLoader("frontend"),
                    Configuration.defaultConfiguration());
            GEN_FRONT_FILE_LIST.forEach(t -> {
                Template templateBackend = groupTemplate.getTemplate(t.getStr("name"));
                templateBackend.binding(bindMap);
                String userDir = SystemUtil.getUserInfo().getCurrentDir();
                String className = StrUtil.removeSuffix(t.getStr("name"), ".btl");
                String outDir =
                        userDir + StrUtil.nullToEmpty(moduleName)
                                + File.separator + ((ClasspathResourceLoader) groupTemplate.getResourceLoader()).getRoot()
                                + File.separator + t.getStr("path")
                                + File.separator + tableDto.getBizName()
                                + className;
                FileUtil.writeUtf8String(templateBackend.render(), outDir);
            });


        } catch (
                IOException e) {
            throw new RuntimeException(e);
        }

    }

    /**
     * btl里的参数映射
     *
     * @param packageName  包名称
     * @param tableDto     表名称
     * @param fieldDtoList 字段列表
     * @return
     */
    public static Map<String, Object> initBinding(String packageName, TableDto tableDto, List<FieldDto> fieldDtoList) {
        Map<String, Object> map = new HashMap<>();
        Set<String> importPackages = new HashSet<>();
        // 代码模块名
//        map.put("moduleName", genBasic.getModuleName());
        // 功能名
//        map.put("functionName", genBasic.getFunctionName());
        // 业务名
//        map.put("busName", genBasic.getBusName());
        // 包名
        map.put("packageName", packageName);
        // 开启 swagger 模式
        map.put("swagger", swagger);
        if (!swagger) {
            // 开启 springdoc 模式
            map.put("springdoc", springdoc);
        }

//        // 类首字母小写名
//        map.put("classNameFirstLower", StrUtil.lowerFirst(genBasic.getClassName()));
//        // 主键名
//        map.put("dbTableKey", genBasic.getDbTableKey());
//        // 主键Java类型
//        map.put("dbTableKeyJavaType", "String");
//        // 主键名驼峰
//        map.put("dbTableKeyCamelCase", StrUtil.toCamelCase(genBasic.getDbTableKey().toLowerCase()));
//        // 主键首字母大写名
//        map.put("dbTableKeyFirstUpper", StrUtil.upperFirst(StrUtil.toCamelCase(genBasic.getDbTableKey().toLowerCase
//        ())));
//        // 主键注释
//        map.put("dbTableKeyRemark", genBasic.getDbTableKey());
//        // 表单布局
//        map.put("formLayout", genBasic.getFormLayout());
//        // 使用栅格
//        map.put("gridWhether", genBasic.getGridWhether().equalsIgnoreCase(GenYesNoEnum.Y.getValue()));
//        // 父菜单ID
//        map.put("parentId", genBasic.getMenuPid());
//        // 菜单ID
//        map.put("menuId", IdWorker.getIdStr());
//        // 菜单编码
//        map.put("menuCode", RandomUtil.randomString(10));
//        // 菜单路径
//        map.put("menuPath", StrUtil.SLASH + genBasic.getModuleName() + StrUtil.SLASH + genBasic.getBusName());
//        // 菜单组件
//        map.put("menuComponent", genBasic.getModuleName() + StrUtil.SLASH + genBasic.getBusName() + StrUtil.SLASH +
//        "index");
//        // 模块ID
//        map.put("moduleId", genBasic.getModule());
//        // 移动端模块ID
//        map.put("mobileModuleId", genBasic.getMobileModule());
//        // 添加按钮ID
//        map.put("addButtonId", IdWorker.getIdStr());
//        // 编辑按钮ID
//        map.put("editButtonId", IdWorker.getIdStr());
//        // 删除按钮ID
//        map.put("deleteButtonId", IdWorker.getIdStr());
//        // 批量删除按钮ID
//        map.put("batchDeleteButtonId", IdWorker.getIdStr());
        // 作者
        map.put("authorName", "diaoyn");
        // 生成时间
        map.put("createTime", DateUtil.now());
        // 表名
        map.put("tableName", tableDto.getTableName());
        // 注释名
        map.put("tableComment", StrUtil.removeSuffix(tableDto.getTableComment(), "表"));
        // 类名
        map.put("className", tableDto.getTableNameCamelCaseFirstUpper());
        // 表名驼峰
        map.put("classNameCamelCase", tableDto.getTableNameCamelCase());
        // 业务名(就是去掉了前缀)
        map.put("bizName", tableDto.getBizName());
        // 定义配置详情列表
        List<JSONObject> configList = CollectionUtil.newArrayList();
        fieldDtoList.forEach(fieldDto -> {
            JSONObject configItem = JSONUtil.createObj();
            // 字段驼峰名
            configItem.set("fieldNameCamelCase", StrUtil.toCamelCase(fieldDto.getFieldName()));
            // 主键
            configItem.set("isKeyId", false);
            if (StrUtil.isNotBlank(fieldDto.getFieldKey())) {
                configItem.set("isKeyId", true);
                importPackages.add("com.baomidou.mybatisplus.annotation.TableId");
            }
            // 实体类型
            configItem.set("fieldJavaType", fieldDto.getDbColumnType().getType());
            if (fieldDto.getDbColumnType() == DbColumnType.DATE) {
                importPackages.add("com.fasterxml.jackson.annotation.JsonFormat");
            }
            configItem.set("fieldTsType", JavaToTsConvert.processConvert(fieldDto.getDbColumnType()));
            importPackages.add(fieldDto.getDbColumnType().getPkg());
            // 字段注释
            configItem.set("fieldComment", fieldDto.getFieldComment());
            // 是否需要逻辑删除,StrUtil.toUnderlineCase兼容输入的是驼峰
            configItem.set("isLogicDelete", DELETE_FLAG_KEY.equalsIgnoreCase(fieldDto.getFieldName())
                    || StrUtil.toUnderlineCase(DELETE_FLAG_KEY).equalsIgnoreCase(fieldDto.getFieldName()));
            if (configItem.getBool("isLogicDelete")) {
                importPackages.add("com.baomidou.mybatisplus.annotation.TableLogic");
                importPackages.add("com.fasterxml.jackson.annotation.JsonIgnore");
            }
            // 是否需要自动插入, StrUtil.toUnderlineCase兼容输入的是驼峰
            configItem.set("isAutoInsert",
                    CREATE_USER_KEY.equalsIgnoreCase(fieldDto.getFieldName())
                            || StrUtil.toUnderlineCase(CREATE_USER_KEY).equalsIgnoreCase(fieldDto.getFieldName())
                            || CREATE_TIME_KEY.equalsIgnoreCase(fieldDto.getFieldName())
                            || StrUtil.toUnderlineCase(CREATE_TIME_KEY).equalsIgnoreCase(fieldDto.getFieldName()));
            // 是否需要自动更新
            configItem.set("isAutoUpdate",
                    UPDATE_USER_KEY.equalsIgnoreCase(fieldDto.getFieldName())
                            || StrUtil.toUnderlineCase(UPDATE_USER_KEY).equalsIgnoreCase(fieldDto.getFieldName())
                            || UPDATE_TIME_KEY.equalsIgnoreCase(fieldDto.getFieldName())
                            || StrUtil.toUnderlineCase(UPDATE_TIME_KEY).equalsIgnoreCase(fieldDto.getFieldName()));
            configList.add(configItem);
        });
        // 配置信息
        map.put("configList", configList);
        // 导入包
        importPackages.remove(null);
        map.put("importPackages", importPackages);

//        // 定义是否有排序字段
//        AtomicBoolean hasSortCodeField = new AtomicBoolean(false);
//        genConfigService.list(new LambdaQueryWrapper<GenConfig>().eq(GenConfig::getBasicId, genBasic.getId()))
//                .forEach(genConfig -> {
//                    // 定义字段信息
//                    JSONObject configItem = JSONUtil.createObj();
//                    if(genConfig.getFieldName().equalsIgnoreCase(SORT_CODE_KEY)) {
//                        hasSortCodeField.set(true);
//                    }
//                    // 如果是主键，则无需作为添加参数，需要作为编辑参数，需要主键注解
//                    if(genConfig.getFieldName().equalsIgnoreCase(genBasic.getDbTableKey())) {
//                        configItem.set("needAdd", false);
//                        configItem.set("needEdit", true);
//                        configItem.set("needPage", false);
//                        configItem.set("needPageType", "none");
//                        configItem.set("required", true);
//                        map.put("dbTableKeyJavaType", genConfig.getFieldJavaType());
//                        map.put("dbTableKeyRemark", genConfig.getFieldRemark());
//                    } else {
//                        // 排除删除标志
//                        String logicDeleteField = mybatisPlusProperties.getGlobalConfig().getDbConfig()
//                        .getLogicDeleteField();
//                        if(ObjectUtil.isEmpty(logicDeleteField)) {
//                            logicDeleteField = "DELETE_FLAG";
//                        }
//                        if(genConfig.getFieldName().equalsIgnoreCase(logicDeleteField)) {
//                            configItem.set("needAdd", false);
//                            configItem.set("needEdit", false);
//                            configItem.set("needPage", false);
//                            configItem.set("needPageType", "none");
//                            configItem.set("required", false);
//                            configItem.set("isKeyId", false);
//                        } else {
//                            boolean needAddAndUpdate = genConfig.getWhetherAddUpdate().equalsIgnoreCase
//                            (GenYesNoEnum.Y.getValue());
//                            configItem.set("needAdd", needAddAndUpdate);
//                            configItem.set("needEdit", needAddAndUpdate);
//                            configItem.set("needPage", genConfig.getQueryWhether().equalsIgnoreCase(GenYesNoEnum.Y
//                            .getValue()));
//                            configItem.set("needPageType", genConfig.getQueryType());
//                            configItem.set("required", genConfig.getWhetherRequired().equalsIgnoreCase(GenYesNoEnum
//                            .Y.getValue()));
//                            configItem.set("isKeyId", false);
//                        }
//                    }
//                    // 列显示
//                    configItem.set("whetherTable", genConfig.getWhetherTable().equalsIgnoreCase(GenYesNoEnum.Y
//                    .getValue()));
//                    // 列省略
//                    configItem.set("whetherRetract", genConfig.getWhetherRetract().equalsIgnoreCase(GenYesNoEnum.Y
//                    .getValue()));
//                    // 增改
//                    configItem.set("whetherAddUpdate", genConfig.getWhetherAddUpdate().equalsIgnoreCase
//                    (GenYesNoEnum.Y.getValue()));
//                    // 作用类型
//                    configItem.set("effectType", genConfig.getEffectType());
//                    // 字典值
//                    configItem.set("dictTypeCode", genConfig.getDictTypeCode());
//                    // 实体类型
//                    configItem.set("fieldJavaType", genConfig.getFieldJavaType());

//                    // 字段驼峰首字母大写名
//                    configItem.set("fieldNameCamelCaseFirstUpper", StrUtil.upperFirst(StrUtil.toCamelCase(genConfig
//                    .getFieldName().toLowerCase())));


//                    configList.add(configItem);
//
//                });
//        // 有排序字段
//        map.put("hasSortCodeField", hasSortCodeField.get());
        return map;

    }


    public static void main(String[] args) {
        //开启swagger2
        new CodeGenerator()
                .enableSwagger()
                .setCreateUserKey("createBy")
                .setDb("jdbc:mysql://192.168.0.200:3306/test-system?characterEncoding=UTF-8&useUnicode=true" +
                                "&useSSL=false&tinyInt1isBit=false&allowPublicKeyRetrieval=true&serverTimezone=Asia" +
                                "/Shanghai",
                        "root",
                        "root")
                .execute(
                        "diaoyn-common-generator",
                        "com.diaoyn.example"
                );
    }
}
