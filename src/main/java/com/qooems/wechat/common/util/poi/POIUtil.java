package com.qooems.wechat.common.util.poi;

import org.apache.poi.xwpf.usermodel.*;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.*;

import java.math.BigInteger;
import java.util.List;
import java.util.Map;

public class POIUtil {

    /**
     *
     * @param table 表格对象
     * @param dataList 数据集合
     * @param startIndex 数据写入开始行
     * @param year 以多少年合计
     */
    public static void testTableStyle(XWPFTable table, List<Map<String,Object>> dataList, int startIndex,Integer year) {
        XWPFTableRow header = table.getRow(startIndex -1);
        header.setHeight(480);
        for (int i = 0; i < dataList.size(); i++) {
            XWPFTableRow targetRow = table.insertNewTableRow(startIndex + i);
            targetRow.setHeight(320);
            Map<String,Object> map = dataList.get(i);
            for(Map.Entry<String, Object> entry : map.entrySet()){
                String key = entry.getKey();
                String value = String.valueOf(entry.getValue());
                XWPFTableCell Cell = targetRow.addNewTableCell();
                boolean setBorder = false;
                String excludeBorders = "ALL";
                if(("six".equals(key))){
                    setBorder = true;
                    excludeBorders = "RIGHT";
                }else if("seven".equals(key)){
                    setBorder = true;
                    excludeBorders = "LEFT";
                }
                boolean redText = i == year && "three".equals(key) ||
                        i != 0 && (i+1)%10 == 0 && setBorder;
                setCellText(Cell, value, 1600, i%2 == 0, "DAEEF3", redText || "one".equals(key), redText,excludeBorders);
            }
        }
    }

    /**
     * 设置单元格式
     * @param cell 单元格
     * @param text 文字
     * @param width 宽度
     * @param isShd 是否填充颜色
     * @param shdColor 填充颜色值 RGB 十六进制
     * @param isBold 是否加粗
     * @param redText 是否给文字设置红色
     * @param excludeBorders 设置红框排除的边 ALL | LEFT | RIGHT ==> 全部排除(不设置红框) | 排除左边 | 排除右边
     */
    public static void setCellText(XWPFTableCell cell, String text, int width, boolean isShd,
                                   String shdColor,boolean isBold,boolean redText,String excludeBorders) {
        CTTc cttc = cell.getCTTc();
        CTTcPr ctPr = cttc.addNewTcPr();
        CTShd ctshd = ctPr.addNewShd();

        ctPr.addNewTcW().setW(BigInteger.valueOf(width));
        ctshd.setColor("auto");
        ctshd.setVal(STShd.CLEAR);
        if(isShd){
            ctshd.setFill(shdColor);
        }
        ctPr.addNewVAlign().setVal(STVerticalJc.CENTER);
        cttc.getPList().get(0).addNewPPr().addNewJc().setVal(STJc.CENTER);

        XWPFRun cellR = cell.getParagraphs().get(0).createRun();
        cellR.setBold(isBold);
        if(redText){
            cellR.setColor("FF0000");
            if(!"ALL".equals(excludeBorders)){
                CTTcBorders borders = ctPr.addNewTcBorders();

                // 上
                CTBorder tBorder = borders.addNewTop();
                tBorder.setVal(STBorder.Enum.forString("single"));
                tBorder.setSz(new BigInteger("10"));
                tBorder.setColor("FF0000");

                // 下
                CTBorder bBorder = borders.addNewBottom();
                bBorder.setVal(STBorder.Enum.forString("single"));
                bBorder.setSz(new BigInteger("10"));
                bBorder.setColor("FF0000");

                if("LEFT".equals(excludeBorders)){
                    // 右
                    CTBorder rBorder = borders.addNewRight();
                    rBorder.setVal(STBorder.Enum.forString("single"));
                    rBorder.setSz(new BigInteger("10"));
                    rBorder.setColor("FF0000");
                }
                if("RIGHT".equals(excludeBorders)){
                    // 左
                    CTBorder lBorder = borders.addNewLeft();
                    lBorder.setVal(STBorder.Enum.forString("single"));
                    lBorder.setSz(new BigInteger("10"));
                    lBorder.setColor("FF0000");
                }
                ctPr.setTcBorders(borders);
            }
        }
        cellR.setFontSize(10);
        cellR.setText(text);
        cellR.setFontFamily("Arial");
        cell.getCTTc().setTcPr(ctPr);
    }

    /**
     * 复制一行到指定位置
     * @param table 表格对象
     * @param copyRowIndex 被复制的行号
     * @param newRowIndex 指定行号
     */
    public static void insertRow(XWPFTable table, int copyRowIndex, int newRowIndex) {
        // 在表格中指定的位置新增一行
        XWPFTableRow targetRow = table.insertNewTableRow(newRowIndex);
        // 获取需要复制行对象
        XWPFTableRow copyRow = table.getRow(copyRowIndex);
        //复制行对象
        targetRow.getCtRow().setTrPr(copyRow.getCtRow().getTrPr());
        //或许需要复制的行的列
        List<XWPFTableCell> copyCells = copyRow.getTableCells();
        //复制列对象
        XWPFTableCell targetCell = null;
        for (int i = 0; i < copyCells.size(); i++) {
            XWPFTableCell copyCell = copyCells.get(i);
            targetCell = targetRow.addNewTableCell();
            targetCell.getCTTc().setTcPr(copyCell.getCTTc().getTcPr());
            if (copyCell.getParagraphs() != null && copyCell.getParagraphs().size() > 0) {
                targetCell.getParagraphs().get(0).getCTP().setPPr(copyCell.getParagraphs().get(0).getCTP().getPPr());
                if (copyCell.getParagraphs().get(0).getRuns() != null
                        && copyCell.getParagraphs().get(0).getRuns().size() > 0) {
                    XWPFRun cellR = targetCell.getParagraphs().get(0).createRun();
                    cellR.setBold(copyCell.getParagraphs().get(0).getRuns().get(0).isBold());
                    cellR.setText(copyCell.getParagraphs().get(0).getRuns().get(0).getText(0));
                }
            }
        }
    }

    /**
     * 表格自定义边框
     * @param table 表格对象
     */
    public static void setBorder(XWPFTable table){
        CTTblBorders borders=table.getCTTbl().getTblPr().addNewTblBorders();
        CTBorder hBorder=borders.addNewInsideH();
        hBorder.setVal(STBorder.Enum.forString("single"));
        hBorder.setSz(new BigInteger("1"));
        hBorder.setColor("92CDDC");

        CTBorder vBorder=borders.addNewInsideV();
        vBorder.setVal(STBorder.Enum.forString("single"));
        vBorder.setSz(new BigInteger("1"));
        vBorder.setColor("92CDDC");

        CTBorder lBorder=borders.addNewLeft();
        lBorder.setVal(STBorder.Enum.forString("single"));
        lBorder.setSz(new BigInteger("1"));
        lBorder.setColor("92CDDC");

        CTBorder rBorder=borders.addNewRight();
        rBorder.setVal(STBorder.Enum.forString("single"));
        rBorder.setSz(new BigInteger("1"));
        rBorder.setColor("92CDDC");

        CTBorder tBorder=borders.addNewTop();
        tBorder.setVal(STBorder.Enum.forString("single"));
        tBorder.setSz(new BigInteger("1"));
        tBorder.setColor("92CDDC");

        CTBorder bBorder=borders.addNewBottom();
        bBorder.setVal(STBorder.Enum.forString("single"));
        bBorder.setSz(new BigInteger("1"));
        bBorder.setColor("92CDDC");
    }

    /**
     * 删除行
     * @param table 表格对象
     * @param deleteIndex 行号
     */
    public static void deleteRow(XWPFTable table,int deleteIndex){
        table.removeRow(deleteIndex);
    }
}
