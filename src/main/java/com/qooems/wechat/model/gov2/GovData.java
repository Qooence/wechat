package com.qooems.wechat.model.gov2;


import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

@Data
public class GovData {

    @ExcelProperty(value = "姓名")
    private String name;

    @ExcelProperty(value = "身份证号")
    private String idNumber;

    @ExcelProperty(value = "剩余工时")
    private Double govPrice;

    @ExcelProperty(value = "冻结工时")
    private Double freeingPrice;

    @ExcelProperty(value = "错误信息")
    private String errorMes;
}
