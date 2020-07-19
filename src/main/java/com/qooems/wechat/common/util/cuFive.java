package com.qooems.wechat.common.util;
import com.qooems.wechat.model.Message;
public class cuFive {
    public static String cu(int one,int two,int four, double six, Message message,double basePrice){
        if(two > 106){
            return "";
        }else {
            if(two < 18){
                return Double.toString(Math.max(four,six));
            }else{
                double xs;
                if(two < 18){
                    xs = 1;
                }else if(two < 41){
                    xs = 1.6;
                }else if(two < 61){
                    xs = 1.4;
                }else{
                    xs = 1.2;
                }
                double r = four*xs;
                if(one < message.getYear()){
                    return ArithHelper.format(Math.max(r,six),"#");
                }else{
                     double fin = ArithHelper.mul(basePrice,Math.pow(1.033,one-1));
                    return ArithHelper.format(Math.max(Math.max(r,six),fin),"#");
                }
            }
        }
    }
}
