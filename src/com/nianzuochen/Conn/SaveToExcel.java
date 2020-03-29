package com.nianzuochen.Conn;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFSheet;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * date:20160622
 * author:nianzuochen
 * fun:将json 数据存储在Excel表格中
 */
public class SaveToExcel {
    private HSSFWorkbook workbook = null;
    private HSSFSheet sheet = null;
    private HSSFRow row = null;
    private int rowIndex  = 0;

    public SaveToExcel() {
        workbook = new HSSFWorkbook();
        sheet = workbook.createSheet("工作表1");
    }

    public void saveToExcel(String path, List<String> infos) {
        createHead();
        saveJSONToExcle(path, infos);
    }

    private void createHead() {
        row = sheet.createRow(rowIndex);
        row.createCell(0).setCellValue("username");
        row.createCell(1).setCellValue("fans");
        row.createCell(2).setCellValue("status");
        row.createCell(3).setCellValue("avatar");
        row.createCell(4).setCellValue("nickname");
        row.createCell(5).setCellValue("签名");
        row.createCell(6).setCellValue("博文");
        row.createCell(7).setCellValue("排名");
    }

    private void saveJSONToExcle(String path, List<String> infos) {
        List<String> fans = new ArrayList<>();
        List<String> photos = new ArrayList<>();

        for (String json : infos) {
            JSONObject jsonObject = JSONObject.fromObject(json);
            JSONObject jo = jsonObject.getJSONObject("data");
            JSONArray array = jo.getJSONArray("list");
            int cellIndex = 0;
            for (int i = 0; i < array.size(); i++) {
                JSONObject user = array.getJSONObject(i);
                //将每一列的信息写入表格中
                row = sheet.createRow(++rowIndex);
                row.createCell(0).setCellValue(user.getString("username"));
                row.createCell(1).setCellValue(user.getString("fans"));
                fans.add(user.getString("fans"));
                row.createCell(2).setCellValue(user.getString("status"));
                row.createCell(3).setCellValue(user.getString("avatar"));
                photos.add(user.getString("avatar"));
                row.createCell(4).setCellValue(user.getString("nickname"));
            }
        }
        saveListToExcle(Connect.getFanInfo(fans));

        try {
            FileOutputStream outputStream = new FileOutputStream(path);
            //将信息写入输出流，将输出流中信息写入文件
            workbook.write(outputStream);
            outputStream.close();
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        GetPhoto.getPhotos(photos);
    }

    // 将 list 中的信息添加到 Excel 中
    private void saveListToExcle (List<String> list) {
        rowIndex = 1;
        int index = 0;
        while (index < list.size()) {
            // 这里不能是 createRow ，否则会将此行以后的数据全部清空，应该是获取此行
            row = sheet.getRow(rowIndex++);
            row.createCell(5).setCellValue(list.get(index++));
            row.createCell(6).setCellValue(list.get(index++));
            row.createCell(7).setCellValue(list.get(index++));
        }
    }
}
