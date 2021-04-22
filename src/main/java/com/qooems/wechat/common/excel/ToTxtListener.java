package com.qooems.wechat.common.excel;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.qooems.wechat.common.util.StrUtil;
import com.qooems.wechat.model.DataZI;
import com.qooems.wechat.model.ToTxt;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ToTxtListener extends AnalysisEventListener<ToTxt> {

    List<ToTxt> dataList = new ArrayList<>(); // 要导入的数据

    @Override
    public void invokeHeadMap(Map<Integer, String> headMap, AnalysisContext context) {
        System.out.println("表头"+headMap);
    }

    //一行一行读取excel内容
    @Override
    public void invoke(ToTxt data, AnalysisContext analysisContext) {
        System.out.println("解析到一条数据:{}" + JSON.toJSONString(data));
        reflect(data);
        dataList.add(data);
    }

    public static void reflect(Object o){
        //获取参数类
        Class cls = o.getClass();
        //将参数类转换为对应属性数量的Field类型数组（即该类有多少个属性字段 N 转换后的数组长度即为 N）
        Field[] fields = cls.getDeclaredFields();
        for(int i = 0;i < fields.length; i ++){
            Field f = fields[i];
            f.setAccessible(true);
            try {
                //f.getName()得到对应字段的属性名，f.get(o)得到对应字段属性值,f.getGenericType()得到对应字段的类型
//                System.out.println("属性名："+f.getName()+"；属性值："+f.get(o)+";字段类型：" + f.getGenericType());
                if(f.get(o) == null){
                    f.set(o, " ");
                }
            } catch (IllegalArgumentException | IllegalAccessException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }

    //读取完成后要做的操作
    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {
        System.out.println("数据读取完成");
    }

    public List<ToTxt> getDataList() {
        return dataList;
    }
}

