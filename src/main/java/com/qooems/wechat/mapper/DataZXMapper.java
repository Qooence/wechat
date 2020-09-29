package com.qooems.wechat.mapper;

import com.qooems.wechat.common.base.BaseMapper;
import com.qooems.wechat.model.DataZX;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DataZXMapper extends BaseMapper<DataZX> {
    List<DataZX> findByIndex_(@Param("index_") String index_);

    void clear();

    void myInsertList(List<DataZX> list);
}
