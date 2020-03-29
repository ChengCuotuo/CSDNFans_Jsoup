package com.nianzuochen.Conn;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

public class GetPhoto {
    public static void getPhotos(List<String> urls) {
        for (String url : urls) {
            getImg(url.substring(url.lastIndexOf("/3_") + 3), url, "images");
        }
    }

    private static void getImg(String name, String url, String path) {
        // 图片的名字是 name + timw + encodingStyle;
        // 图片的编码格式
        String encodingStyle = url.substring(url.lastIndexOf("."));
        String fileName = name  + encodingStyle;
        File f = new File(path);
        if (!f.exists()) {
            f.mkdirs();
        }
        try {
            // 新建 URL 地址
            URL imgUrl = new URL(url);
            // 获取链接
            HttpURLConnection conn = (HttpURLConnection)imgUrl.openConnection();
            // 获取图片字节流
            InputStream is = conn.getInputStream();
            // 新建文件，存储图片
            File file = new File(path, fileName);
            FileOutputStream os = new FileOutputStream(file);
            // 每次读取 1024 个字节
            byte[] b = new byte[1024];
            int length = 0; //实际读到的字节数
            while((length = is.read(b)) > 0) {
                // 写入文件
                os.write(b, 0, length);
            }
            os.close();
            is.close();
        } catch (MalformedURLException ex) {
            ex.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
