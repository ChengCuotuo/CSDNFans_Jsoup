package com.nianzuochen.Conn;

import com.sun.xml.bind.v2.runtime.output.NamespaceContextImpl;
import org.jsoup.Jsoup;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.jsoup.Connection;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class Connect {
    public List<String> gerJSONInfo() {
        List<String> info = new ArrayList<>();
        try {
            //只是获取一页
            for (int i = 1; i <= 8;i++ ) {
                info.add(getCookie(i));
            }
            Thread.sleep((int)(Math.random() * 2));
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }
        return info;
    }
    public String getCookie(int i){
        String url = "https://me.csdn.net/api/relation/index?pageno=" + i +"&pagesize=20&relation_type=fans";
        Connection connect = Jsoup.connect(url);
        connect.header("Host", "me.csdn.net");
        connect.header("User-Agent", "Mozilla/5.0 (Windows NT 6.1; Win64; x64; rv:67.0) Gecko/20100101 Firefox/67.0");
        connect.header("Accept", "application/json, text/plain, */*");
        connect.header("Accept-Language", "zh-CN,zh;q=0.8,zh-TW;q=0.7,zh-HK;q=0.5,en-US;q=0.3,en;q=0.2");
        connect.header("Accept-Encoding", "gzip, deflate, br");
        connect.header("Referer", "https://i.csdn.net/");
        connect.header("Origin", "https://i.csdn.net");
        connect.header("Connection", "keep-alive");
        connect.header("Cookie", "xxxxx");
        connect.header("TE", "Trailers");
        //请求url获取响应信息
        Connection.Response res = null;
        try {
            res = connect.ignoreContentType(true).method(Connection.Method.GET).execute();// 执行请求
        } catch (IOException ex) {
            System.out.println("获取 cookies 值异常");
            ex.printStackTrace();
        }
        // 获取返回的cookie
        return  res.body();
    }


    //根据得到的用户的 fans ，使用网址 https://me.csdn.net/ + fans 就是粉丝的主页了，然后获取一些信息
    public static List<String> getFanInfo(List<String> fans) {
        List<String> list = new ArrayList<>();
        try {
            for (String fan : fans) {
                String url = "https://me.csdn.net/" + fan;
                Connection connect = Jsoup.connect(url);
                Document doc = Jsoup.connect(url).get();
                Elements descr = doc.select(".description_detail");
                String desc = descr.text();
                Elements info = doc.getElementsByClass("me_chanel_det");
                Elements elements = info.get(0).getElementsByTag("div");
                String num = elements.get(2).text().split(" ")[1];
                String pai = elements.get(3).text().split(" ")[1];
                list.add(desc);
                list.add(num);
                list.add(pai);

                Thread.sleep((int)(Math.random() * 2));
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }
        return list;
    }
}
