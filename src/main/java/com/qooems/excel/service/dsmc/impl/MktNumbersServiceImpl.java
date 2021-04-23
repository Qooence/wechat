package com.qooems.excel.service.dsmc.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.qooems.excel.common.base.BaseServiceImpl;
import com.qooems.excel.mapper.dsmc.MktNumbersMapper;
import com.qooems.excel.model.dsmc.MktNumbers;
import com.qooems.excel.service.dsmc.MktNumbersService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class MktNumbersServiceImpl extends BaseServiceImpl<MktNumbers> implements MktNumbersService {

    @Autowired
    MktNumbersMapper mktNumbersMapper;

    @Override
    public List<MktNumbers> list(MktNumbers mktNumbers,Integer pageNum,Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<MktNumbers> list = mktNumbersMapper.select(mktNumbers);
        return list;
    }
}
