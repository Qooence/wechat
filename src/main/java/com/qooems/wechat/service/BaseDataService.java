package com.qooems.wechat.service;

import com.qooems.wechat.common.base.BaseService;
import com.qooems.wechat.model.BaseData;

import java.util.List;

public interface BaseDataService extends BaseService<BaseData> {

    List<BaseData> findByIndex_(String index_);

}
