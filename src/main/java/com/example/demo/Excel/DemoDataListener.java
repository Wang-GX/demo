package com.example.demo.Excel;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.alibaba.fastjson.JSON;
import com.example.demo.common.mapper.ExcelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * 模板的读取类
 *
 * @author Jiaju Zhuang
 */
//TODO 有个很重要的点 DemoDataListener 不能被spring管理，要每次读取excel都要new,然后里面用到spring可以构造方法传进去
public class DemoDataListener extends AnalysisEventListener<DemoData> {

    private static final Logger LOGGER = LoggerFactory.getLogger(DemoDataListener.class);

    /**
     * 每隔5条存储数据库，实际使用中可以3000条，然后清理list ，方便内存回收
     */
    private static final int BATCH_COUNT = 5;
    private List<DemoData> list = new ArrayList<>();
    private ExcelMapper excelMapper;


    /**
     * 如果使用了spring，请使用这个构造方法。每次创建Listener的时候需要把spring管理的类传进来
     */
    public DemoDataListener(ExcelMapper excelMapper) {
        this.excelMapper = excelMapper;
    }


    /**
     * 每一条数据解析都会调用
     */
    @Override
    public void invoke(DemoData data, AnalysisContext context) {
        LOGGER.info("解析到一条数据:{}", JSON.toJSONString(data));
        list.add(data);
        // 达到BATCH_COUNT了，需要去存储一次数据库，防止数据几万条数据在内存，容易BOOM
        if (list.size() >= BATCH_COUNT) {
            saveData();
            // 存储完成清理 list
            list.clear();
        }
    }

    /**
     * 所有数据解析完成调用
     */
    @Override
    public void doAfterAllAnalysed(AnalysisContext context) {
        // 这里也要保存数据，确保最后遗留的数据也存储到数据库
        saveData();
        LOGGER.info("所有数据解析完成！");
    }

    /**
     * 将读取到的数据存入数据库
     */
    private void saveData() {
        LOGGER.info("{}条数据，开始存储数据库！", list.size());
        for (DemoData demoData:list) {
            this.excelMapper.save(demoData);
        }
        LOGGER.info("存储数据库成功！");
    }
}