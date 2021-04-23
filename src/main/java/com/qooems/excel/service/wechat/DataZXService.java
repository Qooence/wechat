package com.qooems.excel.service.wechat;

import com.qooems.excel.common.base.BaseService;
import com.qooems.excel.model.wechat.DataZX;

import java.util.List;

public interface DataZXService extends BaseService<DataZX> {

    List<DataZX> findByIndex_(String index_);

    void init();

}
