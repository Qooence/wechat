package com.qooems.wechat.common.util;

public class StrUtil {

    public static String subLastTwo(String str,String split){
        int i = str.lastIndexOf(split);
        int a = str.lastIndexOf(split,i-1);
        return str.substring(0,a);
    }
}
