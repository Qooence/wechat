package com.qooems.excel.mapper.wechat;

import com.qooems.excel.common.base.BaseMapper;
import com.qooems.excel.model.wechat.DataZI;
import org.apache.ibatis.annotations.Param;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Primary
public interface DataZIMapper extends BaseMapper<DataZI> {

    void clear();

    List<DataZI> findByIndex_(@Param("index_") String index_);

    void myInsertList(List<DataZI> list);

}
