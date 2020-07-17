package com.qooems.wechat.common.util.poi;

import org.apache.poi.xwpf.usermodel.*;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.*;

import java.math.BigInteger;
import java.util.List;
import java.util.Map;

public class POIUtil {

    public static void testTableStyle(XWPFTable table, List<Map<String,Object>> dataList, int startIndex) {
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
                setCellText(Cell, value, 1600, i%2 == 0, "DAEEF3", "one".equals(key));
            }
        }
    }

    public static void setCellText(XWPFTableCell cell, String text, int width, boolean isShd, String shdColor,boolean isBold) {
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
        cellR.setFontSize(10);
        cellR.setText(text);
        cellR.setFontFamily("Arial");
        cell.getCTTc().setTcPr(ctPr);
    }

    // 复制一行
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

    //表格自定义边框
    public static void setBorder(XWPFTable table) throws Exception {
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

    public static void deleteRow(XWPFTable table,int deleteIndex){
        table.removeRow(deleteIndex);
    }
}
