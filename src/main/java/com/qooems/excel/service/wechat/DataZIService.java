package com.qooems.excel.service.wechat;

import com.qooems.excel.common.base.BaseService;
import com.qooems.excel.model.wechat.DataZI;

import java.util.List;

public interface DataZIService extends BaseService<DataZI> {

    void init();

    List<DataZI> findByIndex_(String index_);

}
