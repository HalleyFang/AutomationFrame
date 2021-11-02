package core.utils;

import com.google.gson.JsonObject;
import core.data.TestCaseType;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.formula.functions.T;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ReadExcel {

    private static String uiFilePath = System.getProperty("user.dir")
            + File.separator + "src"
            + File.separator + "main"
            + File.separator + "resources"
            + File.separator + "ui.xls";

    private static String apiFilePath = System.getProperty("user.dir")
            + File.separator + "src"
            + File.separator + "main"
            + File.separator + "resources"
            + File.separator + "api.xls";

    public static Object[][] readExcelObject(String sheetName, TestCaseType type) {
        if(TestCaseType.UI_CASE.equals(type)) {
            return readExcelObject(uiFilePath, sheetName);
        }
        return readExcelObject(apiFilePath, sheetName);
    }

        public static Object[][] readExcelObject(String filePath,String sheetName) {
        List<List<Object>> list = readExcel(filePath,sheetName);
        List<Object> tl = list.get(0);
        int length = tl.size();
        Object[][] objects = new Object[list.size()][length];
        for (int i = 0; i < list.size(); i++) {
            Object[] data = list.get(i).toArray();
            for (int j = 0; j < data.length; j++) {
                objects[i][j] = data[j];
            }
        }
        return objects;
    }

    public static List<List<Object>> readExcel(String sheetName,TestCaseType type) {
        if(TestCaseType.UI_CASE.equals(type)) {
            return readExcel(uiFilePath, sheetName);
        }
        return readExcel(apiFilePath,sheetName);
    }

    public static List<List<Object>> readExcel(String filePath, String sheetName) {
        List<List<Object>> list = new ArrayList<>();
        if (StringUtils.isEmpty(filePath)) {
            return null;
        }
        String extString = filePath.substring(filePath.lastIndexOf("."));
        InputStream is = null;
        try {
            is = new FileInputStream(filePath);
            if (".xls".equals(extString)) {
                list = analysisXls(new HSSFWorkbook(is), sheetName);
            } else if (".xlsx".equals(extString)) {
                list = analysisXlsx(new XSSFWorkbook(is), sheetName);
            } else {
                return null;
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return list;
    }

    public static List<List<Object>> analysisXls(HSSFWorkbook wb, String sheetName) {
        List<List<Object>> list = new ArrayList<>();
        HSSFSheet sheet = wb.getSheetAt(0);
        if (sheetName != null && !sheetName.isEmpty()) {
            sheet = wb.getSheet(sheetName);
        }
        //获取行数
        int rows = sheet.getPhysicalNumberOfRows();
        //默认第一行为列头，数据大于1行时才处理
        if (rows > 1) {
            List<String> head = new ArrayList<>();
            HSSFRow row0 = sheet.getRow(0);
            //20列，基本上是够用的
            for (int k = 0; k < 20; k++) {
                HSSFCell cell = row0.getCell(k);
                if (cell == null) {
                    break;
                }
                head.add(cell.getStringCellValue());
            }
            if (head.size() == 0) {
                return null;
            }
            // 从第2行开始遍历行,默认第一行是表头
            for (int i = 1; i < rows; i++) {
                List<Object> tmp = new ArrayList<>();
                HSSFRow row = sheet.getRow(i);
                //遍历列
                for (int j = 0; j <= head.size(); j++) {
                    HSSFCell cell = row.getCell(j);
                    if (cell == null) {
                        continue;
                    }
                    CellType cellType = cell.getCellType();
                    if (cellType == CellType.NUMERIC) {
                        tmp.add(cell.getNumericCellValue());
                    } else if (cellType == CellType.BOOLEAN) {
                        tmp.add(cell.getBooleanCellValue());
                    } else {
                    tmp.add(cell.getStringCellValue());
                    }
                }
                if (tmp.size()>0) {
                    list.add(tmp);
                }
            }
        }
        return list;
    }

    public static List<List<Object>> analysisXlsx(XSSFWorkbook wb, String sheetName) {
        List<List<Object>> list = new ArrayList<>();
        XSSFSheet sheet = wb.getSheetAt(0);
        if (sheetName != null && !sheetName.isEmpty()) {
            sheet = wb.getSheet(sheetName);
        }
        //获取行数
        int rows = sheet.getPhysicalNumberOfRows();
        //默认第一行为列头，数据大于1行时才处理
        if (rows > 1) {
            List<String> head = new ArrayList<>();
            XSSFRow row0 = sheet.getRow(0);
            //20列，基本上是够用的
            for (int k = 0; k < 20; k++) {
                XSSFCell cell = row0.getCell(k);
                if (cell == null) {
                    break;
                }
                head.add(cell.getStringCellValue());
            }
            if (head.size() == 0) {
                return null;
            }
            // 从第2行开始遍历行,默认第一行是表头
            for (int i = 1; i < rows; i++) {
                List<Object> tmp = new ArrayList<>();
                XSSFRow row = sheet.getRow(i);
                //遍历列
                for (int j = 0; j <= head.size(); j++) {
                    XSSFCell cell = row.getCell(j);
                    if (cell == null) {
                        continue;
                    }
                    CellType cellType = cell.getCellType();
                    if (cellType == CellType.NUMERIC) {
                        tmp.add(cell.getNumericCellValue());
                    } else if (cellType == CellType.BOOLEAN) {
                        tmp.add(cell.getBooleanCellValue());
                    } else {
                        tmp.add(cell.getStringCellValue());
                    }
                }
                if (tmp.size()>0) {
                    list.add(tmp);
                }
            }
        }
        return list;
    }

}
