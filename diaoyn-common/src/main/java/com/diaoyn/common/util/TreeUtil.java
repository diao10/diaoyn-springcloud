package com.diaoyn.common.util;

import org.apache.commons.lang3.StringUtils;
import org.springframework.util.CollectionUtils;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 主要是根据 反射来 组装树形,所以 要保证 存在下面三个基本属性 regionId,parentId,children
 *
 * @author diaoyn
 * @ClassName TreeUtil
 * @Date 2025/1/14 10:53
 */
public class TreeUtil {

    /**
     * 1.通过递归方式构建树
     *
     * @param list 列表
     * @return {@link List}<{@link T}>
     */
    public static <T> List<T> buildTreeByRecursion(List<T> list) {
        return buildTreeByRecursion(list, "regionId", "parentId", "children");
    }

    /**
     * 2.通过Map方式构建树
     *
     * @param list 列表
     * @return {@link List}<{@link T}>
     */
    public static <T> List<T> buildTreeByMap(List<T> list) {
        return buildTreeByMap(list, "id", "parentId", "children", null);
    }

    /**
     * 3.通过两层for循环方式构建树
     *
     * @param list 列表
     * @return {@link List}<{@link T}>
     */
    public static <T> List<T> buildTreeByTwoLayersFor(List<T> list) {
        return buildTreeByTwoLayersFor(list, "regionId", "parentId", "children", "0");
    }

    /**
     * 通过递归方式构建树
     *
     * @param list         列表
     * @param idName       id名称
     * @param parentIdName 父id名称
     * @param childrenName 子节点列表名称
     * @return {@link List}<{@link T}>
     */
    public static <T> List<T> buildTreeByRecursion(List<T> list, String idName, String parentIdName,
                                                   String childrenName) {
        if (StringUtils.isBlank(idName) || StringUtils.isBlank(parentIdName) || StringUtils.isBlank(childrenName)) {
            return new ArrayList<>();
        }
        List<T> returnList = new ArrayList<>();
        //获取list中所有的主键id
        List<String> ids = list.stream().map(o -> getFieldValue(o, idName).toString()).collect(Collectors.toList());

        for (T t : list) {
            String parentId = getFieldValue(t, parentIdName).toString();
            //如果是顶级节点, 遍历该父节点的所有子节点,因为顶层的parentId为0
            if (!ids.contains(parentId)) {
                buildTreeRecursive(list, t, idName, parentIdName, childrenName);
                returnList.add(t);
            }
        }
        if (returnList.isEmpty()) {
            returnList = list;
        }
        return returnList;
    }

    /**
     * 递归fn
     *
     * @param list 分类表
     * @param node 子节点
     */
    private static <T> void buildTreeRecursive(List<T> list, T node, String idName, String parentIdName,
                                               String childrenName) {
        // 得到子节点列表
        List<T> childList = getChildList(list, node, idName, parentIdName);
        setFieldValue(node, childList, childrenName);
        for (T child : childList) {
            if (hasChild(list, child, idName, parentIdName)) {
                buildTreeRecursive(list, child, idName, parentIdName, childrenName);
            }
        }
    }

    /**
     * 得到子节点列表
     */
    private static <T> List<T> getChildList(List<T> list, T node, String idName, String parentIdName) {
        List<T> tlist = new ArrayList<>();
        String oId = getFieldValue(node, idName).toString();
        for (T child : list) {
            String tParentId = getFieldValue(child, parentIdName).toString();
            if (tParentId.equals(oId)) {
                tlist.add(child);
            }
        }
        return tlist;
    }

    /**
     * 判断是否有子节点
     */
    private static <T> boolean hasChild(List<T> list, T t, String idName, String parentIdName) {
        return getChildList(list, t, idName, parentIdName).size() > 0;
    }

    /**
     * 通过Map方式构建树
     *
     * @param list           列表
     * @param idName         id名称
     * @param parentIdName   父id名称
     * @param childrenName   子节点列表名称
     * @param topParentIdVal 顶层节点父id的值
     * @return {@link List}<{@link T}>
     */
    public static <T> List<T> buildTreeByMap(List<T> list, String idName, String parentIdName, String childrenName,
                                             String topParentIdVal) {
        if (StringUtils.isBlank(idName) || StringUtils.isBlank(parentIdName) || StringUtils.isBlank(childrenName)) {
            return new ArrayList<>();
        }
        //根据parentId进行分组
        Map<String, List<T>> mapList = list.stream().collect(Collectors.groupingBy(o -> getFieldValue(o,
                parentIdName).toString()));
        //给每个节点设置子节点列表
        list.forEach(node -> setFieldValue(node, mapList.get(getFieldValue(node, idName).toString()), childrenName));
        return list.stream().filter(o -> topParentIdVal.equals(getFieldValue(o, parentIdName).toString())).collect(Collectors.toList());
    }

    /**
     * 获取属性值
     *
     * @param o         对象
     * @param fieldName 属性名
     * @return {@link String}
     */
    private static Object getFieldValue(Object o, String fieldName) {
        try {
            Class<?> oClass = o.getClass();
            Field field = oClass.getDeclaredField(fieldName);
            field.setAccessible(true);
            return field.get(o);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 设置字段值
     *
     * @param o         对象
     * @param val       值
     * @param fieldName 属性名
     */
    private static void setFieldValue(Object o, Object val, String fieldName) {
        try {
            Class<?> oClass = o.getClass();
            Field field = oClass.getDeclaredField(fieldName);
            field.setAccessible(true);
            field.set(o, val);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 通过两层for循环方式构建树
     *
     * @param list           列表
     * @param idName         id名称
     * @param parentIdName   父id名称
     * @param childrenName   子节点列表名称
     * @param topParentIdVal 顶层节点父id的值
     * @return {@link List}<{@link T}>
     */
    public static <T> List<T> buildTreeByTwoLayersFor(List<T> list, String idName, String parentIdName,
                                                      String childrenName, String topParentIdVal) {
        List<T> resultList = new ArrayList<>();
        for (T node : list) {
            //如果是顶层节点
            if (topParentIdVal.equals(getFieldValue(node, parentIdName).toString())) {
                resultList.add(node);
            }
            for (T child : list) {
                if (getFieldValue(child, parentIdName).equals(getFieldValue(node, idName))) {
                    List<T> childrenList = (List<T>) getFieldValue(node, childrenName);
                    if (CollectionUtils.isEmpty(childrenList)) {
                        childrenList = new ArrayList<>();
                        setFieldValue(node, childrenList, childrenName);
                    }
                    childrenList.add(child);
                }
            }
        }
        return resultList;
    }

}


