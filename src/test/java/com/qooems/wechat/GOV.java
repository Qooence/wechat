package com.qooems.wechat;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.StrUtil;
import com.alibaba.excel.EasyExcel;
import com.qooems.wechat.common.excel.GovExcelListener;
import com.qooems.wechat.common.util.ArithHelper;
import com.qooems.wechat.common.util.poi.POICacheManager;
import com.qooems.wechat.mapper.gov2.GovDataMapper;
import com.qooems.wechat.model.gov2.GovData;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@SpringBootTest
@RunWith(SpringRunner.class)
@Slf4j
public class GOV {

    @Autowired
    GovDataMapper govDataMapper;


    /**
     * 修改云平台客户服务卡相关人的余额
     */
    @Test
    public void gov(){
        log.info("开始------------------------------");

        List<GovData> errorList = new ArrayList<>();

//        ClassPathResource classPathResource = new ClassPathResource("doc/修改剩余工时2020-07-23.xls");
//        InputStream inputStream = classPathResource.getStream();
//
//        List<GovData> list = readGov(inputStream);
//        String name = "doc/修改剩余工时2020-07-23.xls";
        String name = "doc/修改冻结工时（补充）.xlsx";



        List<GovData> list = readGov(name);

        if(CollectionUtil.isNotEmpty(list)){
            for (GovData govData : list) {
                List<Map<String,Object>> data = govDataMapper.findDataByIdNumber(govData.getIdNumber());
                if(data.size() != 1){
                    govData.setErrorMes("没有匹配到对应的数据");
                    errorList.add(govData);
                    continue;
                }
                Map<String,Object> map = data.get(0);
                if(!StrUtil.equals((String)map.get("name"),govData.getName())){
                    govData.setErrorMes("名字与身份证不匹配" );
                    errorList.add(govData);
                    continue;
                }

                if(map.get("MAN_HOUR_COEFFICIENT") == null){
                    govData.setErrorMes("工时系数为空");
                    errorList.add(govData);
                    continue;
                }

                if(name.contains("剩余")){
                    try {
                        govDataMapper.updateGovBuyBalance((String)map.get("id"), ArithHelper.mul((Double) map.get("MAN_HOUR_COEFFICIENT"),govData.getGovPrice()));
                    }catch (Exception e){
                        govData.setErrorMes("修改失败");
                        errorList.add(govData);
                    }

                }else if(name.contains("冻结")){

                    try {
                        govDataMapper.updateGovBuyFreeingBalance((String)map.get("id"), ArithHelper.mul((Double) map.get("MAN_HOUR_COEFFICIENT"),govData.getFreeingPrice()));
                    }catch (Exception e){
                        e.printStackTrace();
                        govData.setErrorMes("修改失败");
                        errorList.add(govData);
                    }
                }

            }
            log.info("修改完毕,开始导出错误信息");
            simpleWrite(errorList);
            log.info("修改完毕,错误信息导出完毕");
        }
    }

    private List<GovData> readGov(InputStream inputStream){
        try {
            GovExcelListener listener = new GovExcelListener();
            EasyExcel.read(inputStream, GovData.class, listener).sheet().doRead();
            log.info("数据获取完毕，总共获取数据{}",listener.getDataList().size());
            return listener.getDataList();
        } catch (Exception var13) {
            log.error(var13.getMessage(), var13);
            return null;
        } finally {
            try {
                inputStream.close();
            } catch (Exception var12) {
                log.error(var12.getMessage(), var12);
            }
        }
    }

    private List<GovData> readGov(String path){
        InputStream is = null;
        try {
            GovExcelListener listener = new GovExcelListener();
            is = POICacheManager.getFile(path);
            EasyExcel.read(is, GovData.class, listener).sheet().doRead();
            log.info("数据获取完毕，总共获取数据{}",listener.getDataList().size());
            return listener.getDataList();
        } catch (Exception var13) {
            log.error(var13.getMessage(), var13);
            return null;
        } finally {
            try {
                is.close();
            } catch (Exception var12) {
                log.error(var12.getMessage(), var12);
            }
        }
    }



    public void simpleWrite(List<GovData> list) {
        // 这里 需要指定写用哪个class去读，然后写到第一个sheet，名字为模板 然后文件流会自动关闭
        // 如果这里想使用03 则 传入excelType参数即可
        EasyExcel.write("E:/excel/free-error.xls", GovData.class).sheet("模板").doWrite(list);
    }

}
