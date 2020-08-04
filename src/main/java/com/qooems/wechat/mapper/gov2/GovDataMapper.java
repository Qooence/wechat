package com.qooems.wechat.mapper.gov2;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;


@Repository
public interface GovDataMapper {

    List<Map<String,Object>> findDataByIdNumber(@Param("idNumber") String idNumber);

    int updateGovBuyBalance(@Param("id") String id,@Param("govByyBalance") Double govByyBalance);

    int updateGovBuyFreeingBalance(@Param("id") String id,@Param("GovBuyFreeingBalance") Double GovBuyFreeingBalance);

}