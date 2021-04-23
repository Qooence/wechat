package com.qooems.excel.model.dsmc;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@Table(name = "mkt_numbers")
public class MktNumbers {

//    @ExcelProperty(value = "region_002(本地网)")
    @Column(name = "lan_id")
    @Id
    private String landId;

//    @ExcelProperty(value = "region_001(营业区)")
    @Column(name = "region_id")
    private String regionId;

//    @ExcelProperty(value = "person_043(用户号码)")
    @Column(name = "acc_nbr")
    private String accNbr;

//    @ExcelProperty(value = "product_019(天翼套餐名称)")
    @Column(name = "bt_offer")
    private String btOffer;

//    @ExcelProperty(value = "T_GXQ_TYTCDC(天翼套餐档次)")
    private String product;

//    @ExcelProperty(value = "T_GXQ_BTTCM(补贴套餐名称)")
    @Column(name = "tytcdc")
    private String tytcdc;

    private String operrtype;
}
