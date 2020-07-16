package com.qooems.wechat.common.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.apache.poi.hwpf.HWPFDocument;
import org.apache.poi.hwpf.usermodel.*;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.xwpf.usermodel.BreakType;
import org.apache.poi.xwpf.usermodel.ParagraphAlignment;
import org.apache.poi.xwpf.usermodel.TextAlignment;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.apache.poi.xwpf.usermodel.XWPFTable;
import org.apache.poi.xwpf.usermodel.XWPFTableCell;
import org.apache.poi.xwpf.usermodel.XWPFTableRow;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.*;

public class APP {

    public static void main(String[] args) throws Exception {
        APP t = new APP();
        XWPFDocument doc = new XWPFDocument();
        t.testTableStyle(doc);
//        t.saveDocument(doc,
//                "e:/excel/sys_" + System.currentTimeMillis() + ".docx");

        t.readddd();
    }


    public void testTableStyle(XWPFDocument doc) {
        String[] colors = new String[] { "CCA6EF", "DD999D", "4FCEF0",
                "7A7A7A", "F3C917", "FFA932", "C7B571", "535354", "5FD2F1",
                "A6DBFF", "FEF8B6" };
        Random random = new Random();
        List<String> columnList = new ArrayList<String>();
        columnList.add("序号");
        columnList.add("姓名信息|姓甚|名谁");
        columnList.add("名刺信息|籍贯|营生");
        columnList.add("字");
        XWPFTable table = doc.createTable(7, 6);
        setTableWidth(table, "8000");

        XWPFTableRow firstRow = table.getRow(0);
        XWPFTableRow secondRow = table.getRow(1);
        firstRow.setHeight(400);
        secondRow.setHeight(400);
        XWPFTableCell firstCell = null;
        XWPFTableCell secondCell = null;
        int firstCellIndex = 0;
        for (String str : columnList) {
            if (str.indexOf("|") == -1) {
                firstCell = firstRow.getCell(firstCellIndex);
                setCellText(firstCell, str, 1600, true, 6, "CCCCCC");
                firstCellIndex++;
            } else {
                String[] strArr = str.split("\\|");
                for (int i = 1; i < strArr.length; i++) {
                    firstCell = firstRow.getCell(firstCellIndex);
                    setCellText(firstCell, strArr[0], 1600, true, 6, "CCCCCC");
                    secondCell = secondRow.getCell(firstCellIndex);
                    setCellText(secondCell, strArr[i], 1600, true, 6, "CCCCCC");
                    firstCellIndex++;
                }
            }
        }

        // 合并行(跨列)
        firstCellIndex = 0;
        for (String str : columnList) {
            if (str.indexOf("|") == -1) {
                firstCellIndex++;
            } else {
                String[] strArr = str.split("\\|");
                mergeCellsHorizontal(table, 0, firstCellIndex, firstCellIndex
                        + strArr.length - 2);
                firstCellIndex += strArr.length - 1;
            }
        }

        // 合并列(跨行)
        firstCellIndex = 0;
        for (String str : columnList) {
            if (str.indexOf("|") == -1) {
                mergeCellsVertically(table, firstCellIndex, 0, 1);
                firstCellIndex++;
            } else {
                String[] strArr = str.split("\\|");
                firstCellIndex += strArr.length - 1;
            }
        }

        int k = 0;
        // 数据
        for (int i = 2; i < 7; i++) {
            firstRow = table.getRow(i);
            firstRow.setHeight(380);
            for (int j = 0; j < 6; j++) {
                firstCell = firstRow.getCell(j);
                setCellText(firstCell, "测试", 1600, true, k % 38,
                        colors[Math.abs(random.nextInt(colors.length))]);
                k++;
            }
        }
    }

    public void setCellText(XWPFTableCell cell, String text, int width,
                            boolean isShd, int shdValue, String shdColor) {
        CTTc cttc = cell.getCTTc();
        CTTcPr ctPr = cttc.isSetTcPr() ? cttc.getTcPr() : cttc.addNewTcPr();
        CTShd ctshd = ctPr.isSetShd() ? ctPr.getShd() : ctPr.addNewShd();
        ctPr.addNewTcW().setW(BigInteger.valueOf(width));
        if (isShd) {
            if (shdValue > 0 && shdValue <= 38) {
                ctshd.setVal(STShd.Enum.forInt(shdValue));
            }
            if (shdColor != null) {
                ctshd.setColor(shdColor);
            }
        }

        ctPr.addNewVAlign().setVal(STVerticalJc.CENTER);
        cttc.getPList().get(0).addNewPPr().addNewJc().setVal(STJc.CENTER);
        cell.setText(text);
    }
    /**
     * @Description: 跨列合并
     */
    public void mergeCellsHorizontal(XWPFTable table, int row, int fromCell,
                                     int toCell) {
        for (int cellIndex = fromCell; cellIndex <= toCell; cellIndex++) {
            XWPFTableCell cell = table.getRow(row).getCell(cellIndex);
            if (cellIndex == fromCell) {
                cell.getCTTc().addNewTcPr().addNewHMerge()
                        .setVal(STMerge.RESTART);
            } else {
                cell.getCTTc().addNewTcPr().addNewHMerge()
                        .setVal(STMerge.CONTINUE);
            }
        }
    }

    /**
     * @Description: 跨行合并
     */
    public void mergeCellsVertically(XWPFTable table, int col, int fromRow,
                                     int toRow) {
        for (int rowIndex = fromRow; rowIndex <= toRow; rowIndex++) {
            XWPFTableCell cell = table.getRow(rowIndex).getCell(col);
            if (rowIndex == fromRow) {
                cell.getCTTc().addNewTcPr().addNewVMerge()
                        .setVal(STMerge.RESTART);
            } else {
                cell.getCTTc().addNewTcPr().addNewVMerge()
                        .setVal(STMerge.CONTINUE);
            }
        }
    }

    public void setTableWidth(XWPFTable table, String width) {
        CTTbl ttbl = table.getCTTbl();
        CTTblPr tblPr = ttbl.getTblPr() == null ? ttbl.addNewTblPr() : ttbl
                .getTblPr();
        CTTblWidth tblWidth = tblPr.isSetTblW() ? tblPr.getTblW() : tblPr
                .addNewTblW();
        CTJc cTJc = tblPr.addNewJc();
        cTJc.setVal(STJc.Enum.forString("center"));
        tblWidth.setW(new BigInteger(width));
        tblWidth.setType(STTblWidth.DXA);
    }


    /**
     * 为表格插入数据，行数不够添加新行
     *
     * @param table     需要插入数据的表格
     * @param tableList 插入数据集合
     */
    public void insertTable(XWPFTable table, List<String> tableList,int pos) {
        //table.addNewRowBetween 没实现，官网文档也说明，只有函数名，但没具体实现，但很多文章还介绍如何使用这个函数，真是害人
        //table.insertNewTableRow 本文用这个可以，但是要创建 cell，否则不显示数据
        //table.addRow() 在表格最后加一行
        // table.addRow(XWPFTableRow row, int pos) 没试过，你可以试试。
        //table.createRow() 在表格最后一加行

//        for (int i = 0; i < tableList.size(); i++) {//遍历要添加的数据的list
            XWPFTableRow newRow = table.insertNewTableRow(pos+1);//为表格添加行
//            String[] strings =  tableList.get(i);//获取list中的字符串数组
            for (int j = 0; j < tableList.size(); j++) {//遍历list中的字符串数组
                String strings1 =  tableList.get(j);
                newRow.createCell();//在新增的行上面创建cell
                newRow.getCell(j).setText(strings1);//给每个cell赋值。
            }
//        }
    }

    public void readddd(){
        XWPFDocument doc = null;
        try {
            FileInputStream in = new FileInputStream("E:/excel/image.docx");
            doc = new XWPFDocument(in);
            FileOutputStream out = new FileOutputStream("E:/excel/image.docx");
            List<XWPFTable> tables = doc.getTables();
            List<String> list = new ArrayList<>();
            list.add("one");
            list.add("two");
            list.add("three");
            list.add("four");
            list.add("five");
            list.add("six");
            list.add("seven");

            testTableStyle(doc);

            insertRow(tables.get(0), 3, 5);//此方法在下方，直接复制拿走

            doc.write(out);
            out.flush();
            out.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * insertRow 在word表格中指定位置插入一行，并将某一行的样式复制到新增行
     * @param copyrowIndex 需要复制的行位置
     * @param newrowIndex 需要新增一行的位置
     * */
    public void insertRow(XWPFTable table, int copyrowIndex, int newrowIndex) {
        // 在表格中指定的位置新增一行
        XWPFTableRow targetRow = table.insertNewTableRow(newrowIndex);
        // 获取需要复制行对象
        XWPFTableRow copyRow = table.getRow(copyrowIndex);
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
                    cellR.setColor("4747d1");
                    cellR.setFontSize(10);
                    cellR.setText("3333");
                    cellR.setFontFamily("Arial");
                }
            }
        }
    }
}

