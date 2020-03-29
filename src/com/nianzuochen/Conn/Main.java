package com.nianzuochen.Conn;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import java.io.IOException;
import java.util.List;

/**
 * date:20160622
 * author:nianzuochen
 * fun:爬取 CSDN 的粉丝的信息
 */
public class Main {
    public static void main(String[] args) throws IOException {
        Connect connect = new Connect();
        List<String> infos = connect.gerJSONInfo();
        SaveToExcel saveToExcel = new SaveToExcel();
        saveToExcel.saveToExcel("fansinfo.xls", infos);
    }
}
