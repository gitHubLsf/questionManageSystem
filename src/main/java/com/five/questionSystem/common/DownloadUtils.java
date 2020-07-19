package com.five.questionSystem.common;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.poi.poifs.filesystem.POIFSFileSystem;


/**
 * 下载
 */
public class DownloadUtils {

    public static void htmlToWord2(String url, String filename, String path) throws Exception {
        URLConnection connection = new URL(url).openConnection();
        InputStream bodyIs = connection.getInputStream();
        String body = getContent(bodyIs);
        String content = "<html><head><style></style></head><body>" + body + "</body></html>";
        InputStream is = new ByteArrayInputStream(content.getBytes(StandardCharsets.UTF_8));
        OutputStream os = new FileOutputStream(path + File.separator + filename);
        inputStreamToWord(is, os);
    }


    /**
     * 创建练习时,生成随机文件名
     */
    public static String getRandomFileName_() {
        return new SimpleDateFormat("yyyy_MM_dd_HH_mm_ss").format(new Date());
    }


    private static String getContent(InputStream... ises) throws IOException {
        if (ises != null) {
            StringBuilder result = new StringBuilder();
            BufferedReader br;
            String line;
            for (InputStream is : ises) {
                br = new BufferedReader(new InputStreamReader(is, "UTF-8"));
                while ((line = br.readLine()) != null) {
                    result.append(line);
                }
            }
            return result.toString();
        }
        return null;
    }


    private static void inputStreamToWord(InputStream is, OutputStream os) throws IOException {
        POIFSFileSystem fs = new POIFSFileSystem();
        fs.createDocument(is, "WordDocument");
        fs.writeFilesystem(os);
        os.close();
        is.close();
    }

}
