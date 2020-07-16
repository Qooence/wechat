package com.qooems.wechat.common.util;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.qooems.wechat.model.BaseData;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.xwpf.usermodel.*;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.*;

public class POI_07Test {
    public static void main(String[] args) throws Exception {
        POI_07Test t = new POI_07Test();
        FileInputStream in = new FileInputStream("E:/excel/image.docx");
        XWPFDocument doc = new XWPFDocument(in);
        List<XWPFTable> tables = doc.getTables();
        List<String []> dataList = new ArrayList<>();
        for(int i = 1;i<107;i++){
            String[] si = new String[]{i+ "","2","3","4","5","6","7"};
            dataList.add(si);
        }
        t.testTableStyle(tables.get(0),dataList,2);
        t.insertRow(tables.get(0),1,19);
        t.setBorder(tables.get(0));
        t.saveDocument(doc, "E:/excel/sys_color.docx");

        XWPFDocument document = new XWPFDocument();
        t.createSimpleTableWithBdColor(document);
        t.saveDocument(document, "E:/excel/sys_"+ System.currentTimeMillis() + ".docx");
    }

    public void testTableStyle(XWPFTable table, List<String []> dataList,int startIndex) {
        XWPFTableRow header = table.getRow(startIndex -1);
        header.setHeight(480);
        for (int i = 0; i < dataList.size(); i++) {
            XWPFTableRow targetRow = table.insertNewTableRow(startIndex + i);
            targetRow.setHeight(320);
            String [] data = dataList.get(i);
            for (int j = 0; j < data.length; j++) {
                XWPFTableCell Cell = targetRow.addNewTableCell();
                setCellText(Cell, data[j], 1600, i%2 == 0, "DAEEF3", j == 0);
            }
        }
    }

    public void setCellText(XWPFTableCell cell, String text, int width, boolean isShd, String shdColor,boolean isBold) {
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

    public void setBorder(XWPFTable table){
        CTTbl ttbl = table.getCTTbl();
        CTTblPr tblPr = ttbl.getTblPr() == null ? ttbl.addNewTblPr() : ttbl.getTblPr();
        CTTblWidth tblWidth = tblPr.isSetTblW() ? tblPr.getTblW() : tblPr.addNewTblW();
        CTJc cTJc=tblPr.addNewJc();
        cTJc.setVal(STJc.Enum.forString("center"));
        tblWidth.setW(new BigInteger("8000"));
        tblWidth.setType(STTblWidth.DXA);
    }

    public void insertRow(XWPFTable table, int copyRowIndex, int newRowIndex) {
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

    //表格自定义边框 请忽略这么丑的颜色样式,主要说明可以自定义样式
    public  void createSimpleTableWithBdColor(XWPFDocument doc) throws Exception {
        List<String> columnList = new ArrayList<String>();
        columnList.add("序号");
        columnList.add("姓名信息|姓甚|名谁");
        columnList.add("名刺信息|籍贯|营生");
        XWPFTable table = doc.createTable(2,5);

        CTTblBorders borders=table.getCTTbl().getTblPr().addNewTblBorders();
        CTBorder hBorder=borders.addNewInsideH();
        hBorder.setVal(STBorder.Enum.forString("dashed"));
        hBorder.setSz(new BigInteger("1"));
        hBorder.setColor("0000FF");

        CTBorder vBorder=borders.addNewInsideV();
        vBorder.setVal(STBorder.Enum.forString("dotted"));
        vBorder.setSz(new BigInteger("1"));
        vBorder.setColor("00FF00");

        CTBorder lBorder=borders.addNewLeft();
        lBorder.setVal(STBorder.Enum.forString("double"));
        lBorder.setSz(new BigInteger("1"));
        lBorder.setColor("3399FF");

        CTBorder rBorder=borders.addNewRight();
        rBorder.setVal(STBorder.Enum.forString("single"));
        rBorder.setSz(new BigInteger("1"));
        rBorder.setColor("F2B11F");

        CTBorder tBorder=borders.addNewTop();
        tBorder.setVal(STBorder.Enum.forString("thick"));
        tBorder.setSz(new BigInteger("1"));
        tBorder.setColor("C3599D");

        CTBorder bBorder=borders.addNewBottom();
        bBorder.setVal(STBorder.Enum.forString("wave"));
        bBorder.setSz(new BigInteger("1"));
        bBorder.setColor("BF6BCC");

        CTTbl ttbl = table.getCTTbl();
        CTTblPr tblPr = ttbl.getTblPr() == null ? ttbl.addNewTblPr() : ttbl.getTblPr();
        CTTblWidth tblWidth = tblPr.isSetTblW() ? tblPr.getTblW() : tblPr.addNewTblW();
        CTJc cTJc=tblPr.addNewJc();
        cTJc.setVal(STJc.Enum.forString("center"));
        tblWidth.setW(new BigInteger("8000"));
        tblWidth.setType(STTblWidth.DXA);

        XWPFTableRow firstRow=null;
        XWPFTableRow secondRow=null;
        XWPFTableCell firstCell=null;
        XWPFTableCell secondCell=null;

        for(int i=0;i<2;i++){
            firstRow=table.getRow(i);
            firstRow.setHeight(380);
            for(int j=0;j<5;j++){
                firstCell=firstRow.getCell(j);
                setCellText(firstCell, "测试", 200,false,"",false);
            }
        }

    }


    public void saveDocument(XWPFDocument document, String savePath)
            throws Exception {
        FileOutputStream fos = new FileOutputStream(savePath);
        document.write(fos);
        fos.close();
    }
}
