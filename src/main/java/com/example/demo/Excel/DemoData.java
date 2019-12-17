package com.example.demo.Excel;

import java.util.Date;

import com.alibaba.excel.annotation.ExcelIgnore;
import com.alibaba.excel.annotation.ExcelProperty;

import lombok.Data;

/**
 * 基础数据类，这里的排序必须和excel里面的排序一致
 *
 * @author Jiaju Zhuang
 **/
@Data
public class DemoData {
    @ExcelProperty("字符串标题")
    private String string;
    @ExcelProperty("日期标题")
    private Date date;
    @ExcelProperty("数字标题")
    private Double doubleData;
    /**
     * 忽略这个字段(该字段默认不会被输出到excel中，无需手动添加到排除表头列表中)
     */
    @ExcelIgnore
    private String ignore;

}