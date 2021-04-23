package com.qooems.excel.service.dsmc;

import com.qooems.excel.common.base.BaseService;
import com.qooems.excel.model.dsmc.MktNumbers;

import java.util.List;


public interface MktNumbersService extends BaseService<MktNumbers> {

    List<MktNumbers> list(MktNumbers mktNumbers, Integer pageNum, Integer pageSize);

}
