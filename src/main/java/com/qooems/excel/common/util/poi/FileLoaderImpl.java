package com.qooems.excel.common.util.poi;

import lombok.extern.slf4j.Slf4j;
import org.apache.poi.util.IOUtils;

import java.io.*;
import java.net.URL;
import java.net.URLConnection;

@Slf4j
public class FileLoaderImpl implements IFileLoader {

    public FileLoaderImpl() {

    }

    public byte[] getFile(String url) {
        InputStream fileis = null;
        ByteArrayOutputStream baos = null;

        try {
            if (url.startsWith("http")) {
                URL urlObj = new URL(url);
                URLConnection urlConnection = urlObj.openConnection();
                urlConnection.setConnectTimeout(30);
                urlConnection.setReadTimeout(60);
                urlConnection.setDoInput(true);
                fileis = urlConnection.getInputStream();
            } else {
                try {
                    fileis = new FileInputStream(url);
                } catch (FileNotFoundException var11) {
                    fileis = FileLoaderImpl.class.getClassLoader().getResourceAsStream(url);
                }
            }

            baos = new ByteArrayOutputStream();
            byte[] buffer = new byte[1024];

            int len;
            while((len = ((InputStream)fileis).read(buffer)) > -1) {
                baos.write(buffer, 0, len);
            }

            baos.flush();
            byte[] var6 = baos.toByteArray();
            return var6;
        } catch (IOException var12) {
            log.error(var12.getMessage(), var12);
        } finally {
            IOUtils.closeQuietly((Closeable)fileis);
            IOUtils.closeQuietly(baos);
        }

        log.error(fileis + "这个路径文件没有找到,请查询");
        return null;
    }
}
