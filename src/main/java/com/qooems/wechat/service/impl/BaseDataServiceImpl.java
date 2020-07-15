package com.qooems.wechat.service.impl;

import com.qooems.wechat.common.base.BaseServiceImpl;
import com.qooems.wechat.mapper.BaseDataMapper;
import com.qooems.wechat.model.BaseData;
import com.qooems.wechat.service.BaseDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BaseDataServiceImpl extends BaseServiceImpl<BaseData> implements BaseDataService {

    @Autowired
    BaseDataMapper baseDataMapper;

    @Override
    public List<BaseData> findByIndex_(String index_) {
        return baseDataMapper.findByIndex_(index_);
    }
}
