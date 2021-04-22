package com.qooems.wechat.model;

import com.alibaba.excel.annotation.ExcelProperty;

public class mktNumbers {

    @ExcelProperty(value = "region_002(本地网)")
    private String land;

    @ExcelProperty(value = "region_001(营业区)")
    private String region;

    @ExcelProperty(value = "person_043(用户号码)")
    private String person;

    @ExcelProperty(value = "product_019(天翼套餐名称)")
    private String product;

    @ExcelProperty(value = "T_GXQ_TYTCDC(天翼套餐档次)")
    private String tGxqTytcd;

    @ExcelProperty(value = "T_GXQ_BTTCM(补贴套餐名称)")
    private String tGxqBttcm;
}
