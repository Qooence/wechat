package com.qooems.wechat.controller;

import cn.hutool.core.date.DateUnit;
import cn.hutool.core.date.DateUtil;
import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.annotation.ExcelProperty;
import com.qooems.wechat.common.excel.ExcelListener;
import com.qooems.wechat.common.excel.ToTxtListener;
import com.qooems.wechat.model.DataZI;
import com.qooems.wechat.model.ToTxt;
import lombok.Data;
import org.springframework.core.io.ClassPathResource;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;

@Data
public class ExcelToTxt {

    public void read() throws IOException {
        InputStream in = null;
        try {
            ClassPathResource classPathResource = new ClassPathResource("/doc/3.xlsx");
            in = classPathResource.getInputStream();
            //获取data对象
            ToTxtListener listener = new ToTxtListener();
            EasyExcel.read(in, ToTxt.class, listener).sheet(0).headRowNumber(1).doRead();

            FileOutputStream fileOutputStream = null;
            File file = new File("C:\\Users\\Administrator\\Desktop\\20200921\\payportal"+ DateUtil.format(DateUtil.yesterday(),"yyyyMMdd") + ".txt");
            try {
                if(file.exists()){
                    //判断文件是否存在，如果不存在就新建一个txt
                    file.createNewFile();
                }
                fileOutputStream = new FileOutputStream(file);
                String one = "收银台支付订单号||&||结算流水号||&||收费类型||&||系统来源||&||外部流水号||&||客户订单编号||&||服务类型||&||客户名称||&||本地网编码||&||工号ID||&||工号编码||&||工号名称||&||营业厅||&||实收费用（单位分）||&||收费时间||&||收费方式||&||收费完成时间||&||客户ID||&||退款对应原外部流水号||&||客户订单ID||&||客户订单在线支付实收费用值（单位分）\n";
                fileOutputStream.write(one.getBytes());
                if(listener.getDataList() != null && listener.getDataList().size()>0){
                    fileOutputStream.write((listener.getDataList().size() + "\n").getBytes());
                    for (ToTxt toTxt : listener.getDataList()) {
                        StringBuffer s = new StringBuffer();
                        s.append(toTxt.get收银台支付订单号()).append("||&||");
                        s.append(toTxt.get结算流水号()).append("||&||");
                        s.append(toTxt.get收费类型()).append("||&||");
                        s.append(toTxt.get系统来源()).append("||&||");
                        s.append(toTxt.get外部流水号()).append("||&||");
                        s.append(toTxt.get客户订单编号()).append("||&||");
                        s.append(toTxt.get服务类型()).append("||&||");
                        s.append(toTxt.get客户名称()).append("||&||");
                        s.append(toTxt.get本地网编码()).append("||&||");
                        s.append(toTxt.get工号ID()).append("||&||");
                        s.append(toTxt.get工号编码()).append("||&||");
                        s.append(toTxt.get工号名称()).append("||&||");
                        s.append(toTxt.get营业厅()).append("||&||");
                        s.append(toTxt.get实收费用()).append("||&||");
                        s.append(toTxt.get收费时间()).append("||&||");
                        s.append(toTxt.get收费方式()).append("||&||");
                        s.append(toTxt.get收费完成时间()).append("||&||");
                        s.append(toTxt.get客户ID()).append("||&||");
                        s.append(toTxt.get退款对应原外部流水号()).append("||&||");
                        s.append(toTxt.get客户订单ID()).append("||&||");
                        s.append(toTxt.get客户订单在线支付实收费用值()).append("\n");
                        fileOutputStream.write(s.toString().getBytes());
                    }
                }
                fileOutputStream.flush();
            } catch (Exception e) {
                e.printStackTrace();
            }finally {
                fileOutputStream.close();
            }
        } catch (IOException ex) {
//            log.error("import excel to db fail", ex);
        } finally {
            in.close();
        }
    }

    public static void main(String[] args) throws Exception{
        ExcelToTxt to = new ExcelToTxt();
        to.read();
    }
}
