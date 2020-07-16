package com.qooems.wechat.controller;

import cn.afterturn.easypoi.word.WordExportUtil;
import cn.hutool.core.collection.CollectionUtil;
import com.alibaba.fastjson.JSONObject;
import com.qooems.wechat.common.response.Response;
import com.qooems.wechat.common.response.ResponseCode;
import com.qooems.wechat.common.util.ArithHelper;
import com.qooems.wechat.model.BaseData;
import com.qooems.wechat.model.Message;
import com.qooems.wechat.service.BaseDataService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.FileOutputStream;
import java.util.*;

@RequestMapping("export")
@RestController
@Slf4j
public class ExportWordController {

    @Autowired
    BaseDataService baseDataService;

    @GetMapping("compute")
    public Response compute(Message message){
        log.info("传入的数据:[{}]", JSONObject.toJSON(message));
        // 1年0岁 男性
        // factor!$B5 = MATCH(A5,data!$A$5:$A$54772,0) 拿到序号
        // facort!GP ==OFFSET(data!I$4,factor!$B5,0)
        // 985.9 从数据库获取 GP
        // OFFSET(985.9,1,0)  // 从
        // facort!GP 其实就是拿数据库中的GP
        // 985.9


        //  MATCH函数语法为：MATCH（lookup_value,lookuparray,match-type）
//        lookup_value：表示查询的指定内容；
//        lookuparray：表示查询的指定区域；
//        match-type：表示查询的指定方式，用数字-1、0或者1表示，具体如图：
        // factor!$B5 == MATCH(A5,data!$A$5:$A$54772,0)

        // factor!J5 = =OFFSET(data!I$4,factor!$B5,0)

        //基本保额 INPUT_GP/1000*factor!J5
        //message.getPrice()/1000*factor

        String sexName = "F";
        String rSexName = "女士";
        if("男".equals(message.getSex())){
            sexName = "M";
            rSexName = "先生";
        }
        // 要查询的index_
        StringBuilder index_ = new StringBuilder();
        index_.append(message.getYear()).append("|")
                .append("106@").append("|")
                .append(sexName).append("|")
                .append(message.getAge());
        // 根据sort 倒叙 第一个序号最大
        List<BaseData> dataList = baseDataService.findByIndex_(index_.toString());
        // 基本保额 = 数据库base_data中 符合查询条件(年限|106@|性别|年龄|)的序号是最大的数据的GP
        // 每条GP都是一样的
        if(CollectionUtil.isEmpty(dataList)){
            return Response.error(ResponseCode.DATA_NOT_FOUND,"未找到匹配数据");
        }
        BaseData minData = dataList.get(0);
        Double basePrice = ArithHelper.mul(ArithHelper.div(message.getPrice(),1000),minData.getGp());
        Map<String, Object> map = new HashMap<>();
        map.put("name", StringUtils.isNotBlank(message.getName()) ? message.getName() : " ");
        map.put("age", message.getAge());
        map.put("sex", message.getSex());
        map.put("sexName", rSexName);
        map.put("year", message.getYear());
        map.put("price", ArithHelper.round(message.getPrice(),2));
        map.put("basePrice", ArithHelper.round(basePrice,2));
        List<Map<String,Object>> list = new ArrayList<>();
        for (int i = 0; i < dataList.size(); i++) {
            int polYr = dataList.get(i).getPolYr();
            Map<String,Object> one = new HashMap<>();

            one.put("one",polYr);
            one.put("two",message.getAge() + polYr);
            if(polYr <= message.getYear()){
                one.put("three",message.getPrice());
                one.put("four",ArithHelper.mul(message.getPrice(),polYr));
            }else{
                one.put("three",0);
                one.put("four",message.getPrice()*message.getAge());
            }
            one.put("five","5");
            one.put("six","6");
            one.put("seven","7");
            list.add(one);
        }
        map.put("list",list);
        try {
            XWPFDocument doc = WordExportUtil.exportWord07("doc/test.docx", map);
            FileOutputStream fos = new FileOutputStream("E://excel/image.docx");
            doc.write(fos);
            fos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return Response.success(map);
    }
}
