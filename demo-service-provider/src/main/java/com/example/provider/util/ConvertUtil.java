package com.example.provider.util;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.*;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * @author diaoyn
 * @version 1.0
 * @date 2021/8/16 15:56
 */
public class ConvertUtil {

    //大写加下划线
    private static final String reg = "^[A-Z_]+$";

    private static final String[] num = {"零", "一", "二", "三", "四", "五", "六", "七", "八", "九", "十"};
    private static final String[] unit = {"十", "百", "千", "万", "十", "百", "千", "亿", "十", "百", "千"};

    private static final List<String> REMOVE_SURPLUS_LIST = new ArrayList<String>() {{
        add("主键ID");
        add("企业ID");
        add("数据原始主键");
        add("数据原始类别编号");
        add("数据类型");
        add("输入的名称");
        add("输入的证件号码");
        add("匹配度");
        add("查询裁判文书详情的 id 号");
        add("新闻ID");
        add("事件类型ID");
        add("正面情感概率");
        add("情感分析置信度");
        add("舆情详情文本");
        add("数据时间");
        add("格式化后的新闻时间");
        add("事件类型ID");
        add("事件主体全称");
        add("新闻主体企业");
        add("创建时间");
        add("修改时间");
        add("企业的简称");
        add("文本ID");
        add("舆情数据来源");

    }};

    /**
     * 代码项添加代码名称
     *
     * @param list    list
     * @param map     map
     * @param flag    flag
     * @param columns columns
     * @param <K>     <K>
     * @param <V>     <V>
     * @return List<Map < String, Object>>
     */
    public static <K, V> List<Map<String, Object>> convertColumn(List<Map<String, Object>> list, Map<K, V> map,
                                                                 Boolean flag, String... columns) {

        list.stream().filter(m -> {
            convertColumn(m, map, flag, columns);
            return true;
        }).collect(Collectors.toList());
        return list;
    }

    /**
     * 代码项添加代码名称
     *
     * @param t       t
     * @param map     map
     * @param flag    flag
     * @param columns columns
     * @param <T>     <T>
     * @param <K>     <K>
     * @param <V>     <V>
     * @return Map<String, Object>
     */
    public static <T, K, V> Map<String, Object> convertColumn(T t, Map<K, V> map, Boolean flag, String... columns) {
        return convertColumn(object2Map(t), map, flag, columns);
    }

    /**
     * 代码项添加代码名称
     *
     * @param m       m
     * @param map     map
     * @param flag    flag
     * @param columns columns
     * @param <K>     <K>
     * @param <V>     <V>
     * @return Map<String, Object>
     */
    public static <K, V> Map<String, Object> convertColumn(Map<String, Object> m, Map<K, V> map, Boolean flag,
                                                           String... columns) {
        String name = "";
        for (String column : columns) {
            if (Pattern.matches(reg, column)) {
                name = "_NAME";
            } else {
                name = "Name";
            }
            if (m.containsKey(column)) {
                if (ObjectUtil.isNull(m.get(column)) || StrUtil.isEmpty(m.get(column).toString())) {
                    m.put(column + name, "");
                } else {
                    //                字段用逗号分隔保存的代码项
                    if (flag) {
                        String[] strs = m.get(column).toString().split(",");
                        List<V> value = new ArrayList<>();
                        for (String str : strs) {
                            value.add(map.get(str));
                        }
                        m.put(column + name, StrUtil.join(",", value));
                    } else {
                        m.put(column + name, map.get(m.get(column).toString()));
                    }
                }
            }
        }
        return m;
    }

    /**
     * 代码项添加代码名称
     *
     * @param list    list
     * @param map     map
     * @param columns columns
     * @param <T>     <T>
     * @param <K>     <K>
     * @param <V>     <V>
     * @return List<Map < String, Object>>
     */
    public static <T, K, V> List<Map<String, Object>> convertColumn(List<T> list, Map<K, V> map, String...
            columns) {
        List<Map<String, Object>> newList = new ArrayList<>();
        for (T t : list) {
            newList.add(object2Map(t));
        }
        return convertColumn(newList, map, false, columns);
    }

    /**
     * 代码项添加代码名称
     *
     * @param e       e
     * @param map     map
     * @param columns columns
     * @param <E>     <E>
     * @param <T>     <T>
     * @param <F>     <F>
     * @param <K>     <K>
     * @param <V>     <V>
     * @return <E extends IPage<T>
     */
    public static <E extends IPage<T>, T, F extends IPage<Map<String, Object>>, K, V> F
    convertColumn(E e, Map<K, V> map, String... columns) {
        F f = (F) convertPageResult(e);
        List<Map<String, Object>> newList;
        try {
            newList = convertColumn(f.getRecords(), map, false, columns);
        } catch (Exception exception) {
            exception.printStackTrace();
            return f;
        }
        f.setRecords(newList);
        return f;
    }

    /**
     * page实体专map
     *
     * @param e   e
     * @param <E> <E>
     * @param <T> <T>
     * @return <E extends IPage<T>, T>
     */
    public static <E extends IPage<T>, T> Page<Map<String, Object>> convertPageResult(E e) {
        if (e == null) {
            return null;
        }
        List<Map<String, Object>> newList = new ArrayList<>();
        for (T t : e.getRecords()) {
            if (t instanceof Map) {
                return (Page<Map<String, Object>>) e;
            }
            newList.add(object2Map(t));
        }
        Page<Map<String, Object>> p = new Page<>();
        p.setTotal(e.getTotal());
        p.setCurrent(e.getCurrent());
        p.setPages(e.getPages());
        p.setSize(e.getSize());
        p.setRecords(newList);
        p.setOrders(e.orders());
        p.setOptimizeCountSql(e.optimizeCountSql());
        p.setSearchCount(e.searchCount());
        return p;

    }

    /**
     * 对象转map
     *
     * @param t   t
     * @param <T> <T>
     * @return <T>
     */
    public static <T> Map<String, Object> object2Map(T t) {
        Map<String, Object> map = new HashMap<>();
        if (t == null) {
            return map;
        }
        try {
            Class c = t.getClass();
            List<Field> fieldList = new ArrayList<>();
            while (c != null) {
                fieldList.addAll(Arrays.asList(c.getDeclaredFields()));
                c = c.getSuperclass();
            }
            for (Field f : fieldList) {
                f.setAccessible(true);
                if (f.getType() == Date.class) {
                    map.put(f.getName(), DateUtil.formatDateTime((Date) f.get(t)));
                } else {
                    map.put(f.getName(), f.get(t));
                }
            }
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return map;
    }

    /**
     * 对象组转map组
     *
     * @param tList tList
     * @param <T>   <T>
     * @return <T>
     */
    public static <T> List<Map<String, Object>> objectList2MapList(List<T> tList) {
        List<Map<String, Object>> list = new ArrayList<>();
        if (tList == null || tList.isEmpty()) {
            return list;
        }
        for (T t : tList) {
            list.add(object2Map(t));
        }
        return list;
    }

    /**
     * 内容标红
     *
     * @param str str
     * @param key key
     * @return String
     */
    public static String remarkRed(String str, String key) {
        if (StrUtil.isEmpty(str) || StrUtil.isEmpty(key)) {
            return str;
        }
        String newKey = "<font color=\"red\">" + key + "</font>";
        return str.replace(key, newKey);
    }

    /**
     * 内容标红
     *
     * @param str  str
     * @param list list
     * @return String
     */
    public static String remarkRed(String str, List<String> list) {
        if (StrUtil.isEmpty(str) || list == null || list.isEmpty()) {
            return str;
        }
        return remarkRed(str, list.stream().collect(Collectors.toSet()));
    }

    /**
     * 内容标红
     *
     * @param str str
     * @param set set
     * @return String
     */
    public static String remarkRed(String str, Set<String> set) {
        if (StrUtil.isEmpty(str) || set == null || set.isEmpty()) {
            return str;
        }
        for (String s : set) {
            str = remarkRed(str, s);
        }
        return str;
    }

    /**
     * 数字转汉字
     *
     * @param str str
     * @return String
     */
    public static String int2chineseNum(String str) {
        if (StrUtil.isEmpty(str)) {
            return str;
        }
        String result = "";

        try {
            int n = str.length();
            for (int i = 0; i < n; i++) {
                int c = str.charAt(i) - '0';
                if (i != n - 1 && c != 0) {
                    result += num[c] + unit[n - 2 - i];
                } else {
                    result += num[c];
                }
            }
            //十、十万特殊处理
            if (str.length() == 2 || str.length() == 6) {
                result = result.substring(1);
            }
            //去尾零
            for (int i = result.length() - 1; i >= 0; i--) {
                if (result.charAt(i) == '零') {
                    result = result.substring(0, i);
                } else {
                    break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            return result;
        }

        return result;
    }


    /**
     * 数字转中文排名
     *
     * @param arg arg
     * @return <T>
     */
    public static <T> List<T> int2chineseRank(List<T> list, String arg) {
        list.forEach(o -> {
            try {
                Field field = o.getClass().getDeclaredField(arg);
                field.setAccessible(true);
                if (ObjectUtil.isNotNull(field.get(o))) {
                    field.set(o, "第" + int2chineseNum((String) field.get(o)) + "名");
                }
            } catch (NoSuchFieldException | IllegalAccessException e) {
                e.printStackTrace();
            }
        });

        return list;
    }


    /**
     * 金额加逗号和单位
     *
     * @param str  str
     * @param unit unit
     * @return String
     */

    public static String num2Comma(String str, String unit) {
        try {
            if (StrUtil.isEmpty(str)) {
                return str;
            }
            if (unit == null) {
                unit = "";
            }
            DecimalFormat format = new DecimalFormat("#,##0.00");
            return format.format(new BigDecimal(str)) + unit;
        } catch (Exception e) {
            e.printStackTrace();
            return str;
        }
    }

    /**
     * 数字转百分比
     *
     * @param num   num
     * @param point point
     * @return String
     */
    public static String decimal2Percent(BigDecimal num, Integer point) {
        if (num == null) {
            num = BigDecimal.ZERO;
        }
        if (ObjectUtil.isNull(point)) {
            point = 2;
        }
        NumberFormat percent = NumberFormat.getPercentInstance();
        percent.setMaximumFractionDigits(point);
        return percent.format(num);
    }

    /**
     * 数字转百分比
     *
     * @param numStr numStr
     * @param point  point
     * @return String
     */
    public static String decimal2Percent(String numStr, Integer point) {
        if (StrUtil.isEmpty(numStr)) {
            return numStr;
        }
        BigDecimal num;
        try {
            num = new BigDecimal(numStr);
        } catch (Exception e) {
            e.printStackTrace();
            return numStr;
        }

        return decimal2Percent(num, point);
    }

    /**
     * 转化为tag
     *
     * @param isEmphasis isEmphasis
     * @param resPerNum  resPerNum
     * @param volNum     volNum
     * @return List<Map < String, Object>>
     */
    public static List<Map<String, Object>> column2Tags(Integer isEmphasis, Long resPerNum, Long volNum) {
        List<Map<String, Object>> list = new ArrayList<>();
        Map<String, Object> map;
        if (ObjectUtil.isNotNull(isEmphasis)) {
            map = new HashMap<>();
            map.put("type", isEmphasis);
            map.put("name", "重点供应商");
            list.add(map);
        }
        if (ObjectUtil.isNotNull(resPerNum) && resPerNum > 0) {
            map = new HashMap<>();
            map.put("type", resPerNum);
            map.put("name", "驻场" + resPerNum + "人");
            list.add(map);
        }
        if (ObjectUtil.isNotNull(volNum) && volNum > 0) {
            map = new HashMap<>();
            map.put("type", volNum);
            map.put("name", "行内成交" + volNum + "次");
            list.add(map);
        }
        return list;
    }

    /**
     * 去除详情里多余的字段
     *
     * @param details details
     * @return String
     */
    public static String removeSurplus(String details) {
        if (StrUtil.isEmpty(details)) {
            return details;
        }
        try {
            JSONArray array = JSONUtil.parseArray(details);
            JSONArray removeArray = new JSONArray();
            for (int i = 0; i < array.size(); i++) {
                String name = String.valueOf(array.getJSONObject(i).get("name"));
                if (Pattern.matches(reg, name)) {
                    removeArray.add(array.getJSONObject(i));
                } else if (REMOVE_SURPLUS_LIST.contains(name)) {
                    removeArray.add(array.getJSONObject(i));
                }
            }
            array.removeAll(removeArray);
            return array.toString();
        } catch (Exception e) {
            e.printStackTrace();
            return details;
        }
    }

    /**
     * 递归tree
     *
     * @param list        list
     * @param param       param
     * @param parentParam parentParam
     * @return JSONArray
     */
    public static JSONArray findChildren(List<Map<String, Object>> list, String param, String parentParam) {
        JSONArray array = new JSONArray();
        if (StrUtil.isEmpty(param)) {
            return array;
        }
        if (StrUtil.isEmpty(parentParam)) {
            parentParam = "parent" + param.substring(0, 1).toUpperCase() + param.substring(1);
        }
        for (Map<String, Object> m : list) {
            if (ObjectUtil.isEmpty(m.get(parentParam))) {
                JSONObject json = JSONUtil.parseObj(m);
                json.put("children", findChildren(list, param, parentParam, m.get(param)));
                array.add(json);
            }
        }
        return array;
    }

    /**
     * 递归tree
     *
     * @param list        list
     * @param param       param
     * @param parentParam parentParam
     * @param code        code
     * @return JSONArray
     */
    public static JSONArray findChildren(List<Map<String, Object>> list, String param, String parentParam,
                                         Object code) {
        JSONArray array = new JSONArray();
        if (ObjectUtil.isEmpty(code)) {
            return null;
        }
        for (Map<String, Object> m : list) {
            if (ObjectUtil.isNotEmpty(m.get(parentParam)) && code.equals(m.get(parentParam))) {
                JSONObject json = JSONUtil.parseObj(m);
                json.put("children", findChildren(list, param, parentParam, m.get(param)));
                array.add(json);
            }
        }
        return array;
    }
}