package com.example.demo.ExcelTest;

import com.alibaba.excel.EasyExcel;
import com.example.demo.Excel.DemoData;
import com.example.demo.Excel.DemoDataListener;
import com.example.demo.common.mapper.ExcelMapper;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * 读的常见写法
 *
 * @author Jiaju Zhuang
 */

@SpringBootTest
public class ReadTest {
    private static final Logger LOGGER = LoggerFactory.getLogger(ReadTest.class);

    @Autowired
    private ExcelMapper excelMapper;

    /**
     * 最简单的读(只读取单一sheet)
     * <p>
     * 1. 创建excel对应的实体对象 参照{@link DemoData}
     * <p>
     * 2. 由于默认异步读取excel，所以需要创建excel一行一行的回调监听器，参照{@link DemoDataListener}
     * <p>
     * 3. 直接读即可
     */
    @Test
    public void simpleRead() {
        // 有个很重要的点 DemoDataListener 不能被spring管理，要每次读取excel都要new,然后里面用到spring可以构造方法传进去
        //指定文件读取路径
        String fileName = System.getProperty("user.dir") + "/src/main/resources/" + "demo" + ".xlsx";
        // 这里 需要指定读用哪个class去读，然后读取第一个sheet 文件流会自动关闭
        EasyExcel.read(fileName, DemoData.class, new DemoDataListener(this.excelMapper)).sheet().doRead();
    }


}

