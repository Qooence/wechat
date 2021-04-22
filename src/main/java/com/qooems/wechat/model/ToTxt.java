package com.qooems.wechat.model;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

@Data
public class ToTxt {

    @ExcelProperty(value = "收银台支付订单号")
    private String 收银台支付订单号;
    @ExcelProperty(value = "结算流水号")
    private String 结算流水号;
    @ExcelProperty(value = "收费类型")
    private String 收费类型;
    @ExcelProperty(value = "系统来源")
    private String 系统来源;
    @ExcelProperty(value = "外部流水号")
    private String 外部流水号;
    @ExcelProperty(value = "客户订单编号")
    private String 客户订单编号;
    @ExcelProperty(value = "服务类型")
    private String 服务类型;
    @ExcelProperty(value = "客户名称")
    private String 客户名称;
    @ExcelProperty(value = "本地网编码")
    private String 本地网编码;
    @ExcelProperty(value = "工号ID")
    private String 工号ID;
    @ExcelProperty(value = "工号编码")
    private String 工号编码;
    @ExcelProperty(value = "工号名称")
    private String 工号名称;
    @ExcelProperty(value = "营业厅")
    private String 营业厅;
    @ExcelProperty(value = "实收费用")
    private String 实收费用;
    @ExcelProperty(value = "收费时间")
    private String 收费时间;
    @ExcelProperty(value = "收费方式")
    private String 收费方式;
    @ExcelProperty(value = "收费完成时间")
    private String 收费完成时间;
    @ExcelProperty(value = "客户ID")
    private String 客户ID;
    @ExcelProperty(value = "退款对应原外部流水号")
    private String 退款对应原外部流水号;
    @ExcelProperty(value = "客户订单ID")
    private String 客户订单ID;
    @ExcelProperty(value = "客户订单在线支付实收费用值")
    private String 客户订单在线支付实收费用值;
}
