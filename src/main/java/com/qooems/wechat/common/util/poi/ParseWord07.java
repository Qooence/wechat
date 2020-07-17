package com.qooems.wechat.common.util.poi;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.xwpf.usermodel.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ParseWord07 {

    private void changeValues(XWPFParagraph paragraph, XWPFRun currentRun, String currentText, List<Integer> runIndex, Map<String, Object> map) throws Exception {
        Object obj =PoiPublicUtil.getRealValue(currentText, map);
        currentText = obj.toString();
        PoiPublicUtil.setWordText(currentRun, currentText);
        for(int k = 0; k < runIndex.size(); ++k) {
            (paragraph.getRuns().get(runIndex.get(k))).setText("", 0);
        }
        runIndex.clear();
    }

    private void parseThisParagraph(XWPFParagraph paragraph, Map<String, Object> map) throws Exception {
        XWPFRun currentRun = null;
        String currentText = "";
        Boolean isfinde = false;
        List<Integer> runIndex = new ArrayList();

        for(int i = 0; i < paragraph.getRuns().size(); ++i) {
            XWPFRun run = paragraph.getRuns().get(i);
            String text = run.getText(0);
            if (!StringUtils.isEmpty(text)) {
                if (isfinde) {
                    currentText = currentText + text;
                    if (currentText.indexOf("{{") == -1) {
                        isfinde = false;
                        runIndex.clear();
                    } else {
                        runIndex.add(i);
                    }

                    if (currentText.indexOf("}}") != -1) {
                        this.changeValues(paragraph, currentRun, currentText, runIndex, map);
                        currentText = "";
                        isfinde = false;
                    }
                } else if (text.indexOf("{{") >= 0) {
                    currentText = text;
                    isfinde = true;
                    currentRun = run;
                } else {
                    currentText = "";
                }

                if (currentText.indexOf("}}") != -1) {
                    this.changeValues(paragraph, currentRun, currentText, runIndex, map);
                    isfinde = false;
                }
            }
        }

    }

    private void parseAllParagraphic(List<XWPFParagraph> paragraphs, Map<String, Object> map) throws Exception {
        for(int i = 0; i < paragraphs.size(); ++i) {
            XWPFParagraph paragraph = paragraphs.get(i);
            if (paragraph.getText().indexOf("{{") != -1) {
                this.parseThisParagraph(paragraph, map);
            }
        }

    }

    public void parseWordSetValue(XWPFDocument doc, Map<String, Object> map) throws Exception {
        this.parseAllParagraphic(doc.getParagraphs(), map);
    }
}
