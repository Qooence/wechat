package com.qooems.wechat.controller;

import com.alibaba.excel.EasyExcel;
import com.qooems.wechat.common.excel.ExcelListener;
import com.qooems.wechat.model.BaseData;
import com.qooems.wechat.service.BaseDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("baseData")
public class BaseDataController {

    @Autowired
    BaseDataService baseDataService;

    @GetMapping("read")
    public void readBaseData(){
        String fileName = "E:\\excel\\jisuan_data.xlsx";
        ExcelListener listener = new ExcelListener();
        EasyExcel.read(fileName, BaseData.class, listener).sheet().headRowNumber(4).doRead();
        insertList(listener.getDataList());
    }

    public void insertList(List<BaseData> list) {
        int insertLength = list.size();
        int i = 0;
        while (insertLength > 5000) {
            baseDataService.insertList(list.subList(i, i + 5000));
            i = i + 5000;
            insertLength = insertLength - 5000;
        }
        if (insertLength > 0) {
            baseDataService.insertList(list.subList(i, i + insertLength));
        }
    }
}
