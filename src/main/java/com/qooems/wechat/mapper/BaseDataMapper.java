package com.qooems.wechat.mapper;

import com.qooems.wechat.common.base.BaseMapper;
import com.qooems.wechat.model.BaseData;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BaseDataMapper extends BaseMapper<BaseData> {

    List<BaseData> findByIndex_(@Param("index_") String index_);

}
