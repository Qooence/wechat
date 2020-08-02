package com.qooems.wechat.service;

import com.qooems.wechat.common.base.BaseService;
import com.qooems.wechat.model.DataZX;

import java.util.List;

public interface DataZXService extends BaseService<DataZX> {

    List<DataZX> findByIndex_(String index_);

    void init();

}
