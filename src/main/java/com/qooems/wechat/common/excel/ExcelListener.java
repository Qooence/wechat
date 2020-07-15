package com.qooems.wechat.common.excel;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.alibaba.fastjson.JSON;
import com.qooems.wechat.common.util.StrUtil;
import com.qooems.wechat.model.BaseData;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Slf4j
public class ExcelListener extends AnalysisEventListener<BaseData> {

    List<BaseData> dataList = new ArrayList<>(); // 要导入的数据
    private Integer sort = 1;

    @Override
    public void invokeHeadMap(Map<Integer, String> headMap, AnalysisContext context) {
        System.out.println("表头"+headMap);
    }

    //一行一行读取excel内容
    @Override
    public void invoke(BaseData data, AnalysisContext analysisContext) {
        log.info("解析到一条数据:{}", JSON.toJSONString(data));
        data.setSort(sort);
        data.setIndex_(StrUtil.subLastTwo(data.getIndex_(),"|"));
        dataList.add(data);
        sort ++;
    }

    //读取完成后要做的操作
    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {
        log.info("数据读取完成");
    }

    public List<BaseData> getDataList() {
        return dataList;
    }
}

