package com.qooems.wechat.common.util;

import com.qooems.wechat.common.util.poi.POICacheManager;
import com.qooems.wechat.common.util.poi.POIUtil;
import com.qooems.wechat.common.util.poi.ParseWord07;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFTable;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;
import java.util.List;
import java.util.Map;

@Slf4j
public class MyWordExportUtil {

    /**
     * 导出word 07格式
     * @param response
     * @param fileName 文件名
     * @param path 模板路径
     * @param dataList 数据集
     * @param data 传入参数
     */
    public static void exportWord07(HttpServletResponse response,String fileName ,String path, List<Map<String,Object>> dataList, Map<String,Object> data){
        try {
            XWPFDocument doc = getXWPFDocument(path);
            XWPFTable table = doc.getTables().get(0);
            (new ParseWord07()).parseWordSetValue(doc, data);
            POIUtil.testTableStyle(table,dataList,2,(Integer) data.get("year"));
            POIUtil.insertRow(table,1,19);
            POIUtil.setBorder(table);
            // 删除最后两行
            POIUtil.deleteRow(table,table.getRows().size());
            if(dataList.size() < 63){
                POIUtil.deleteRow(table,table.getRows().size()-1);
            }
            downLoadWord(response,fileName,doc);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void downLoadWord(HttpServletResponse response,String fileName,XWPFDocument document) {
        try {
            response.setCharacterEncoding("UTF-8");
            response.setHeader("content-Type", "application/vnd.ms-excel;charset=utf-8");
            response.setHeader("Content-Disposition",
                    "attachment;filename=" + URLEncoder.encode(fileName, "UTF-8"));
            document.write(response.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static XWPFDocument getXWPFDocument(String url) {
        InputStream is = null;
        try {
            is = POICacheManager.getFile(url);
            XWPFDocument doc = new XWPFDocument(is);
            return doc;
        } catch (Exception var13) {
            log.error(var13.getMessage(), var13);
        } finally {
            try {
                is.close();
            } catch (Exception var12) {
                log.error(var12.getMessage(), var12);
            }
        }
        return null;
    }
}
