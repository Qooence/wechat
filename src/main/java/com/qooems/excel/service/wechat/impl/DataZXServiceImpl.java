package com.qooems.excel.service.wechat.impl;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelReader;
import com.alibaba.excel.read.metadata.ReadSheet;
import com.qooems.excel.common.base.BaseServiceImpl;
import com.qooems.excel.common.excel.ZXExcelListener;
import com.qooems.excel.mapper.wechat.DataZXMapper;
import com.qooems.excel.model.wechat.DataZX;
import com.qooems.excel.service.wechat.DataZXService;
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
public class DataZXServiceImpl extends BaseServiceImpl<DataZX> implements DataZXService {

    @Autowired
    DataZXMapper dataZXMapper;

    @Override
    public List<DataZX> findByIndex_(String index_) {
        return dataZXMapper.findByIndex_(index_);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void init() {
        try {
            dataZXMapper.clear();
            read();
        } catch (IOException e) {
            log.error("读取{}失败", "/doc/zx_2020_9_28.xlsx");
            e.printStackTrace();
        }
    }

    public void read() throws IOException {
        ExcelReader excelReader = null;
        InputStream in = null;
        try {
//            ClassPathResource classPathResource = new ClassPathResource("/doc/zx-pojie.xlsx"); // modify 2020-9-28
            ClassPathResource classPathResource = new ClassPathResource("/doc/zx_2020_9_28.xlsx");// add 2020-9-28
            in = classPathResource.getInputStream();
            ZXExcelListener excelListener = new ZXExcelListener();
            excelReader = EasyExcel.read(in, DataZX.class, excelListener).build();
            //获取data1对象
            ReadSheet data1 = EasyExcel.readSheet(2).headRowNumber(4).build();
            //读取数据
            excelReader.read(data1);
            List<DataZX> list = excelListener.getDataList();
            insertLists(list);
            //清空list数据
            list.clear();
            //获取data2对象
            ReadSheet data2 = EasyExcel.readSheet(3).headRowNumber(4).build();
            //读取数据
            excelReader.read(data2);
            list = excelListener.getDataList();
            insertLists(list);
        } catch (IOException ex) {
            log.error("import excel to db fail", ex);
        } finally {
            in.close();
            // 这里一定别忘记关闭，读的时候会创建临时文件，到时磁盘会崩
            if (excelReader != null) {
                excelReader.finish();
            }
        }
    }

    private void insertLists(List<DataZX> list) {
        int insertLength = list.size();
        int i = 0;
        while (insertLength > 500) {
            dataZXMapper.myInsertList(list.subList(i, i + 500));
            i = i + 500;
            insertLength = insertLength - 500;
        }
        if (insertLength > 0) {
            dataZXMapper.myInsertList(list.subList(i, i + insertLength));
        }
    }
}
