package com.qooems.wechat.service;

import com.qooems.wechat.common.base.BaseService;
import com.qooems.wechat.model.DataZI;

import java.util.List;

public interface DataZIService extends BaseService<DataZI> {

    void init();

    List<DataZI> findByIndex_(String index_);

}
