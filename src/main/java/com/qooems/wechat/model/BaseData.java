package com.qooems.wechat.model;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

import javax.persistence.*;

@Data
@Table(name = "base_data")
public class BaseData {

    @Id
    @Column(name = "ID")
    @GeneratedValue(generator="JDBC")
    private Integer id;

    @Column(name = "INDEX_")
    @ExcelProperty(value = "Index")
    private String index_;

    @Column(name = "SORT")
    private Integer sort;

    @Column(name = "PLAN")
    @ExcelProperty(value = "Plan")
    private String plan;

    @Column(name = "VRSN")
    @ExcelProperty(value = "Vrsn")
    private Integer vrsn;

    @Column(name = "NP")
    @ExcelProperty(value = "NP")
    private Integer np;

    @Column(name = "NB")
    @ExcelProperty(value = "NB")
    private String nb;

    @Column(name = "SEX")
    @ExcelProperty(value = "Sex")
    private String sex;

    @Column(name = "AGE")
    @ExcelProperty(value = "Age")
    private Integer age;

    // 受保年  投保的第二年为受保的第一年
    @Column(name = "POL_YR")
    @ExcelProperty(value = "PolYr")
    private Integer polYr;

    @Column(name = "GP")
    @ExcelProperty(value = "GP")
    private Double gp;

    @Column(name = "TR")
    @ExcelProperty(value = "TR")
    private Double tr;

    @Column(name = "TRNP")
    @ExcelProperty(value = "TRNP")
    private Double trnp;

    @Column(name = "CV")
    @ExcelProperty(value = "CV")
    private Double cv;

    @Column(name = "CVNP")
    @ExcelProperty(value = "CVNP")
    private Double cvnp;

    @Column(name = "PUAMT")
    @ExcelProperty(value = "PUAMT")
    private Double puamt;

    @Column(name = "PUTR")
    @ExcelProperty(value = "PUTR")
    private Double putr;

    @Column(name = "DB")
    @ExcelProperty(value = "DB")
    private Double db;

    @Column(name = "SB")
    @ExcelProperty(value = "SB")
    private Double sb;

}
