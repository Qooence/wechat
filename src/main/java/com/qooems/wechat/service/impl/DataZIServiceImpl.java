package com.qooems.wechat.service.impl;

import com.alibaba.excel.EasyExcel;
import com.qooems.wechat.common.base.BaseServiceImpl;
import com.qooems.wechat.common.excel.ExcelListener;
import com.qooems.wechat.mapper.DataZIMapper;
import com.qooems.wechat.model.DataZI;
import com.qooems.wechat.service.DataZIService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@Service
@Slf4j
public class DataZIServiceImpl extends BaseServiceImpl<DataZI> implements DataZIService {

    @Autowired
    DataZIMapper dataZIMapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void init() {
        try {
            dataZIMapper.clear();
            read();
        } catch (IOException e) {
            log.error("读取{}失败", "/doc/zi-pojie.xlsx");
            e.printStackTrace();
        }
    }

    public void read() throws IOException {
        InputStream in = null;
        try {
            ClassPathResource classPathResource = new ClassPathResource("/doc/zi-pojie.xlsx");
            in = classPathResource.getInputStream();
            //获取data对象
            ExcelListener listener = new ExcelListener();
            EasyExcel.read(in, DataZI.class, listener).sheet(4).headRowNumber(4).doRead();
            insertLists(listener.getDataList());
        } catch (IOException ex) {
            log.error("import excel to db fail", ex);
        } finally {
            in.close();
        }
    }

    private void insertLists(List<DataZI> list) {
        int insertLength = list.size();
        int i = 0;
        while (insertLength > 5000) {
            dataZIMapper.insertList(list.subList(i, i + 5000));
            i = i + 5000;
            insertLength = insertLength - 5000;
        }
        if (insertLength > 0) {
            dataZIMapper.insertList(list.subList(i, i + insertLength));
        }
    }

    @Override
    public List<DataZI> findByIndex_(String index_) {
        return dataZIMapper.findByIndex_(index_);
    }
}
