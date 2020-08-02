package com.qooems.wechat.mapper;

import com.qooems.wechat.common.base.BaseMapper;
import com.qooems.wechat.model.DataZI;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DataZIMapper extends BaseMapper<DataZI> {

    void clear();

    List<DataZI> findByIndex_(@Param("index_") String index_);

}
