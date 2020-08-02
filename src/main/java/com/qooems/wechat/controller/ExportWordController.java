package com.qooems.wechat.controller;

import com.alibaba.fastjson.JSONObject;
import com.qooems.wechat.common.util.ArithHelper;
import com.qooems.wechat.common.util.MyWordExportUtil;
import com.qooems.wechat.common.util.StrUtil;
import com.qooems.wechat.common.util.cuFive;
import com.qooems.wechat.model.DataZI;
import com.qooems.wechat.model.DataZX;
import com.qooems.wechat.model.Message;
import com.qooems.wechat.service.DataZIService;
import com.qooems.wechat.service.DataZXService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.util.*;

@RequestMapping("export")
@RestController
@Slf4j
public class ExportWordController {

    @Autowired
    DataZIService dataZIService;

    @Autowired
    DataZXService dataZXService;

    @GetMapping("zi")
    public void export_zi(Message message, HttpServletResponse response){
        log.info("传入的数据:[{}]", JSONObject.toJSON(message));
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
        List<DataZI> dataList = dataZIService.findByIndex_(index_.toString());
        // 基本保额 = 数据库base_data中 符合查询条件(年限|106@|性别|年龄|)的序号是最大的数据的GP
        // 每条GP都是一样的
        DataZI minData = dataList.get(0);
        Double basePrice = ArithHelper.mul(ArithHelper.div(message.getPrice(),1000),minData.getGp());
        String name = "  ";
        String fileName;
        if(StringUtils.isNotBlank(message.getName())){
            name = message.getName();
            fileName = name + rSexName + "-阳光臻爱倍致保险计划书.docx";
        }else{
            fileName = message.getAge() + "岁"
                    + StrUtil.numberToChinese(message.getPrice())
                    + StrUtil.numberToChinese(message.getYear()) + "年"
                    + "-" + message.getSex() + "-计划书(旧).docx";
        }

        Map<String, Object> map = new HashMap<>();
        map.put("name", name);
        map.put("age", message.getAge());
        map.put("sex", message.getSex());
        map.put("sexName", rSexName);
        map.put("year", message.getYear());
        map.put("price", ArithHelper.format(message.getPrice(),"#.00"));
        map.put("basePrice", ArithHelper.format(basePrice,"#.00"));
        List<Map<String,Object>> list = new ArrayList<>();
        double prevSix = 0;
        for (int i = 0; i < dataList.size(); i++) {
            int polYr = dataList.get(i).getPolYr();
            Map<String,Object> one = new LinkedHashMap<>();
            one.put("one",polYr);
            int two = message.getAge() + polYr;
            one.put("two",two);
            int four;
            if(polYr <= message.getYear()){
                one.put("three",message.getPrice());
                four = message.getPrice()*polYr;
                one.put("four",four);
            }else{
                one.put("three",0);
                four = message.getPrice()*message.getYear();
                one.put("four",four);
            }
            double six = message.getPrice()/1000*dataList.get(i).getCv();
            one.put("five", cuFive.cu(polYr,two,four,six,message,basePrice));
            String sixFormat = ArithHelper.format(six,"#");
            one.put("six",sixFormat);
            if(i < 4){
                one.put("seven","-");
            }else{
                String seven = ArithHelper.format(((six-prevSix)/four)*100,"#.00") + "%";
                if(message.getYear() == 5){
                    if(i == 4){
                        seven = "3" + seven.substring(2);
                    }
                }
                one.put("seven",seven);
            }
            prevSix = Integer.parseInt(sixFormat);
            list.add(one);
        }
        MyWordExportUtil.exportWord07(response,fileName,"doc/template_zi.docx",list,map);
    }

    @GetMapping("zx")
    public void export_zx(Message message, HttpServletResponse response){
        log.info("传入的数据:[{}]", JSONObject.toJSON(message));
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
        List<DataZX> dataList = dataZXService.findByIndex_(index_.toString());
        // 基本保额 = 数据库base_data中 符合查询条件(年限|106@|性别|年龄|)的序号是最大的数据的GP
        // 每条GP都是一样的
        DataZX minData = dataList.get(0);
        Double basePrice = ArithHelper.mul(ArithHelper.div(message.getPrice(),1000),minData.getGp());
        String name = "  ";
        String fileName;
        if(StringUtils.isNotBlank(message.getName())){
            name = message.getName();
            fileName = name + rSexName + "-阳光臻鑫倍致保险计划书.docx";
        }else{
            fileName = message.getAge() + "岁"
                    + StrUtil.numberToChinese(message.getPrice())
                    + StrUtil.numberToChinese(message.getYear()) + "年"
                    + "-" + message.getSex() + "-计划书(新).docx";
        }

        Map<String, Object> map = new HashMap<>();
        map.put("name", name);
        map.put("age", message.getAge());
        map.put("sex", message.getSex());
        map.put("sexName", rSexName);
        map.put("year", message.getYear());
        map.put("price", ArithHelper.format(message.getPrice(),"#.00"));
        map.put("basePrice", ArithHelper.format(basePrice,"#.00"));
        List<Map<String,Object>> list = new ArrayList<>();
        double prevSix = 0;
        for (int i = 0; i < dataList.size(); i++) {
            int polYr = dataList.get(i).getPolYr();
            Map<String,Object> one = new LinkedHashMap<>();
            one.put("one",polYr);
            int two = message.getAge() + polYr;
            one.put("two",two);
            int four;
            if(polYr <= message.getYear()){
                one.put("three",message.getPrice());
                four = message.getPrice()*polYr;
                one.put("four",four);
            }else{
                one.put("three",0);
                four = message.getPrice()*message.getYear();
                one.put("four",four);
            }
            double six = message.getPrice()/1000*dataList.get(i).getCv();
            one.put("five", cuFive.cu(polYr,two,four,six,message,basePrice));
            String sixFormat = ArithHelper.format(six,"#");
            one.put("six",sixFormat);
            if(i < message.getYear()){
                one.put("seven","-");
            }else{
                String seven = ArithHelper.format(((six-prevSix)/four)*100,"#.0") + "%";
                one.put("seven",seven);
            }
            prevSix = Integer.parseInt(sixFormat);
            list.add(one);
        }
        MyWordExportUtil.exportWord07(response,fileName,"doc/template_zx.docx",list,map);
    }
}
