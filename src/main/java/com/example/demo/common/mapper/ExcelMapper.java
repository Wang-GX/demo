package com.example.demo.common.mapper;

import com.example.demo.Excel.DemoData;
import org.apache.ibatis.annotations.Param;

public interface ExcelMapper {

    void save(@Param("demoData")DemoData demoData);
}
