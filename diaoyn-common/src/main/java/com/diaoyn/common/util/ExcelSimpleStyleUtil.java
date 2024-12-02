//package com.diaoyn.common.util;
//
//import cn.afterturn.easypoi.excel.export.styler.ExcelExportStylerDefaultImpl;
//import cn.hutool.core.bean.BeanUtil;
//import cn.hutool.core.date.DateUtil;
//import cn.hutool.core.io.FileUtil;
//import cn.hutool.core.util.StrUtil;
//
//import java.io.BufferedOutputStream;
//import java.io.File;
//import java.io.IOException;
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
///** easypoi导出工具类
// * @author diaoyn
// * @ClassName ExcelSimpleStyleUtil
// * @Date 2024/11/29 11:57
// */
//public class ExcelSimpleStyleUtil extends ExcelExportStylerDefaultImpl {
//    public ExcelSimpleStyleUtil(Workbook workbook) {
//        super(workbook);
//    }
//
//    @Override
//    public CellStyle getHeaderStyle(short color) {
//        CellStyle cellStyle = super.getHeaderStyle(color);
//        Font font = workbook.createFont();
//        font.setFontName("微软雅黑");
//        font.setBold(true);
//        font.setFontHeightInPoints((short) 10);
//        cellStyle.setFont(font);
//        return cellStyle;
//    }
//
//    @Override
//    public CellStyle getTitleStyle(short color) {
//        CellStyle cellStyle = super.getTitleStyle(color);
//        Font font = workbook.createFont();
//        font.setFontName("微软雅黑");
//        font.setBold(true);
//        font.setFontHeightInPoints((short) 10);
//        font.setColor(IndexedColors.WHITE.getIndex());
//        cellStyle.setFont(font);
//        //自定义背景色2个都需要，看清楚单词阿喂
//        cellStyle.setFillForegroundColor(IndexedColors.BLUE_GREY.getIndex());
//        cellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
//        return cellStyle;
//    }
//
//
//    @Override
//    public CellStyle stringSeptailStyle(Workbook workbook, boolean isWarp) {
//        CellStyle cellStyle = super.stringSeptailStyle(workbook, isWarp);
//        Font font = workbook.createFont();
//        font.setFontName("微软雅黑");
//        font.setFontHeightInPoints((short) 10);
//        cellStyle.setFont(font);
//        return cellStyle;
//
//    }
//
//    @Override
//    public CellStyle stringNoneStyle(Workbook workbook, boolean isWarp) {
//        CellStyle cellStyle = super.stringNoneStyle(workbook, isWarp);
//        Font font = workbook.createFont();
//        font.setFontName("微软雅黑");
//        font.setFontHeightInPoints((short) 10);
//        cellStyle.setFont(font);
//        return cellStyle;
//    }
//
//    private void createExcel(File rootDir, CsProjectFunctionTreeNodeModel model) {
//        Map<String, List<CsProjectFunctionTreeNodeModel>> excelMap = new HashMap<>();
//        //三级名称分类
//        for (CsProjectFunctionTreeNodeModel child : model.getChildren()) {
//            if (child.getName().contains(StrUtil.DASHED)) {
//                String key = StrUtil.subBefore(child.getName(), "-", false);
//                List<CsProjectFunctionTreeNodeModel> list = excelMap.getOrDefault(key, new ArrayList<>());
//                list.add(child);
//                excelMap.put(key, list);
//            } else {
//                List<CsProjectFunctionTreeNodeModel> list = excelMap.getOrDefault(model.getName(), new ArrayList<>());
//                list.add(child);
//                excelMap.put(model.getName(), list);
//            }
//        }
//        for (Map.Entry<String, List<CsProjectFunctionTreeNodeModel>> entry : excelMap.entrySet()) {
//            List<Map<String, Object>> sheetList = new ArrayList<>();
//            for (int i = 0; i < entry.getValue().size(); i++) {
//                CsProjectFunctionTreeNodeModel treeNodeModel = entry.getValue().get(i);
//                List<String> nodeIdList = new ArrayList<>();
//                Map<String, String> nodeNameMap = new HashMap<>();
//                getChildNodeId(treeNodeModel, null, nodeIdList, nodeNameMap);
//
//                LambdaQueryWrapper<CsUsecase> queryWrapper = new LambdaQueryWrapper<>();
//                queryWrapper.in(CsUsecase::getNodeId, nodeIdList);
//                queryWrapper.eq(CsUsecase::getStatus, ApprovalStatusEnum.PASS.getCode());
//                queryWrapper.eq(CsUsecase::getFreezeFlag, NumberEnum.ONE.getCode());
//                queryWrapper.orderByAsc(CsUsecase::getNodeId);
//                queryWrapper.orderByAsc(CsUsecase::getCreateTime);
//
//                List<CsUsecase> usecaseList = this.list(queryWrapper);
//                if (usecaseList.isEmpty()) {
//                    continue;
//                }
//                List<CsUsecaseTemplateVo> templateVoList = new ArrayList<>();
//                for (CsUsecase usecase : usecaseList) {
//                    CsUsecaseTemplateVo templateVo = new CsUsecaseTemplateVo();
//                    BeanUtil.copyProperties(usecase, templateVo);
//                    String nodeNames = nodeNameMap.get(usecase.getNodeId());
//                    addTreeNodeName(templateVo, nodeNames);
//
//                    QueryWrapper wrapper = new QueryWrapper<>();
//                    wrapper.eq("usecase_id", usecase.getId());
//                    List<CsUsecasePrecondition> preconditionList = csUsecasePreconditionService.list(wrapper);
//                    List<String> preconditionContentList =
//                            preconditionList.stream().map(CsUsecasePrecondition::getContent).toList();
//                    templateVo.setPreconditions(StrUtil.join(StrUtil.LF, preconditionContentList));
//                    List<CsUsecaseStep> stepList = csUsecaseStepService.list(wrapper);
//                    List<String> stepActionDecriptionList =
//                            stepList.stream().map(CsUsecaseStep::getActionDecription).toList();
//                    templateVo.setSteps(StrUtil.join(StrUtil.LF, stepActionDecriptionList));
//                    List<CsUsecaseExpectResult> expectResultList = csUsecaseExpectResultService.list(wrapper);
//                    List<String> expectResultContentList =
//                            expectResultList.stream().map(CsUsecaseExpectResult::getExpectResult).toList();
//                    templateVo.setExpectResults(StrUtil.join(StrUtil.LF, expectResultContentList));
//                    templateVoList.add(templateVo);
//                }
//
//                Map<String, Object> sheetMap = new HashMap<>();
//                ExportParams params = new ExportParams(treeNodeModel.getName() + "测试用例", treeNodeModel.getName());
//                params.setHeight((short) 60);
//                params.setStyle(ExcelSimpleStyleUtil.class);
//                sheetMap.put("title", params);
//                sheetMap.put("entity", CsUsecaseTemplateVo.class);
//                sheetMap.put("data", templateVoList);
//                sheetList.add(sheetMap);
//            }
//            Workbook workbook = ExcelExportUtil.exportExcel(sheetList, ExcelType.XSSF);
//            File excelFile =
//                    FileUtil.file(rootDir.getAbsolutePath()
//                            + "/" + model.getName()
//                            + "/" + model.getName() + "-" + entry.getKey()
//                            + ".xlsx");
//            BufferedOutputStream stream = FileUtil.getOutputStream(excelFile);
//            try {
//                workbook.write(stream);
//                workbook.close();
//                stream.close();
//            } catch (IOException e) {
//                throw new RuntimeException(e);
//            }
//        }
//    }
//
//    public String bugExport(CsExportVo vo) {
//        if (StrUtil.isEmpty(vo.getProjectId())) {
//            throw new RuntimeException("项目id不能为空");
//        }
//        ExportParams params = new ExportParams();
//        params.setAutoSize(true);
//        Workbook workbook = ExcelExportUtil.exportExcel(params, CsBugTemplateVo.class,
//                baseMapper.listExport(vo.getProjectId(), vo.getIds()));
//        File exportFile = FileUtil.file(FileUtil.getTmpDir() + StrUtil.SLASH + "bug" + DateUtil.currentSeconds() +
//                ".xlsx");
//        BufferedOutputStream outputStream = FileUtil.getOutputStream(exportFile);
//        workbook.write(outputStream);
//        workbook.close();
//        outputStream.close();
//        return MinioUtil.getFullUrl(MinioUtil.upload(exportFile.getName(), MinioUtil.EXPORT,
//                FileUtil.getTmpDir().getAbsolutePath()));
//    }
//}
