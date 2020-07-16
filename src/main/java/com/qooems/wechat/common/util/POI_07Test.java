package com.qooems.wechat.common.util;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.qooems.wechat.model.BaseData;
import org.apache.poi.xwpf.usermodel.*;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTShd;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTTc;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTTcPr;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.STJc;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.STShd;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.STVerticalJc;

public class POI_07Test {
    public static void main(String[] args) throws Exception {
        POI_07Test t = new POI_07Test();
        FileInputStream in = new FileInputStream("E:/excel/image.docx");
        XWPFDocument doc = new XWPFDocument(in);
        List<XWPFTable> tables = doc.getTables();
        List<String []> dataList = new ArrayList<>();
        String[] s1 = new String[]{"1","2","3","4","5","6","7"};
        String[] s2 = new String[]{"1","2","3","4","5","6","7"};
        String[] s3 = new String[]{"1","2","3","4","5","6","7"};
        String[] s4 = new String[]{"1","2","3","4","5","6","7"};
        String[] s5 = new String[]{"1","2","3","4","5","6","7"};
        dataList.add(s1);
        dataList.add(s2);
        dataList.add(s3);
        dataList.add(s4);
        dataList.add(s5);
        t.testTableStyle(tables.get(0),dataList,2);
        t.saveDocument(doc,
                "E:/excel/sys_color.docx");
    }

    public void testTableStyle(XWPFTable table, List<String []> dataList,int startIndex) {
        for (int i = 0; i < dataList.size(); i++) {
            XWPFTableRow targetRow = table.insertNewTableRow(startIndex + i);
            targetRow.setHeight(380);
            String [] data = dataList.get(i);
            for (int j = 0; j < data.length; j++) {
                XWPFTableCell Cell = targetRow.addNewTableCell();
                setCellText(Cell, data[j], 1600, i%2 == 1, "DAEEF3", j == 0);
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

//        cell.setText(text);
    }



    public void saveDocument(XWPFDocument document, String savePath)
            throws Exception {
        FileOutputStream fos = new FileOutputStream(savePath);
        document.write(fos);
        fos.close();
    }
}
