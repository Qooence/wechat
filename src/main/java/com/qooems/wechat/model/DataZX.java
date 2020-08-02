package com.qooems.wechat.model;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@Table(name = "data_zx")
public class DataZX {

    @Id
    @Column(name = "ID")
    @GeneratedValue(generator="JDBC")
    private Integer id;

    @Column(name = "INDEX_")
    @ExcelProperty(value = "Index")
    private String index_;

    @Column(name = "SORT")
    private Integer sort;


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

    @Column(name = "CV")
    @ExcelProperty(value = "CV")
    private Double cv;

}
