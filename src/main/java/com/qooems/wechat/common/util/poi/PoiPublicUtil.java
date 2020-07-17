package com.qooems.wechat.common.util.poi;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import java.util.Map;

public final class PoiPublicUtil {

    private PoiPublicUtil() {}

    public static void setWordText(XWPFRun currentRun, String currentText) {
        if (StringUtils.isNotEmpty(currentText)) {
            String[] tempArr = currentText.split("\r\n");
            int i = 0;
            for(int le = tempArr.length - 1; i < le; ++i) {
                currentRun.setText(tempArr[i], i);
                currentRun.addBreak();
            }
            currentRun.setText(tempArr[tempArr.length - 1], tempArr.length - 1);
        }
    }

    public static Object getRealValue(String currentText, Map<String, Object> map) throws Exception {
        Object obj;
        for(String params = ""; currentText.indexOf("{{") != -1; currentText = currentText.replace("{{" + params + "}}", obj.toString())) {
            params = currentText.substring(currentText.indexOf("{{") + 2, currentText.indexOf("}}"));
            obj = map.get(params);
        }
        return currentText;
    }
}

