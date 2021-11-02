package core.utils;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 读取csv文件
 *
 * @author Halley.Fang
 */
public class ReadCsv {
    public static Integer paramNum(File file, String la) throws IOException {
        Integer a = 0;
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader(file));//换成你的文件名
            //reader.readLine();//第一行信息，为标题信息，不用,如果需要，注释掉
            String line = null;
            List<String> csvlist = new ArrayList<String>();
            while ((line = reader.readLine()) != null) {
                String item[] = line.split(",");//CSV格式文件为逗号分隔符文件，这里根据逗号切分
                //String last = item[item.length-1];//这就是你要的数据了
                //int value = Integer.parseInt(last);//如果是数值，可以转化为数值
                //System.out.println(Arrays.toString(item));
                //System.out.println(item.length);
                String str = Arrays.toString(item);
                csvlist.add(str.substring(1, str.length() - 1));
            }
            /*
            for(int i=0;i<csvlist.size();i++){
                System.out.println(csvlist.get(i));
            }
            */
            String[] aa = csvlist.get(0).split(",");
            for (int i = 0; i < aa.length; i++) {
                if (la.equalsIgnoreCase(aa[i].replace(" ", ""))) {
                    a = i;//参数化变量引用的参数名位置
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            reader.close();
        }
        return a;//参数化变量引用的参数名位置
    }

    public static List<String> readCsvForList(String filepath) throws IOException {
        List<String> csvlist = new ArrayList<String>();
        BufferedReader reader = null;
        Resource resource = new ClassPathResource(filepath);
        InputStream is = null;
        InputStreamReader isr = null;
        try {
            is = resource.getInputStream();
            isr = new InputStreamReader(is);
            reader = new BufferedReader(isr);
//            reader = new BufferedReader(new FileReader(file));//换成你的文件名
            //String line = reader.readLine();//第一行信息，为标题信息，不用;如果需要，注释掉
            String line = null;
            while ((line = reader.readLine()) != null) {
                String item[] = line.split(",");//CSV格式文件为逗号分隔符文件，这里根据逗号切分
                String str = Arrays.toString(item);
                csvlist.add(str.substring(1, str.length() - 1));//参数化参数值信息，按行依次读取
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            reader.close();
            isr.close();
            is.close();
        }
        return csvlist;
    }

    public static Object[][] readCsv(String filepath) throws IOException {
        List<String> list = readCsvForList(filepath);
        int length = list.get(0).split(",").length;
        Object[][] csvList = new Object[list.size() - 1][length];
        for (int i = 1; i < list.size(); i++) {
            String[] data = list.get(i).split(",");
            for (int j = 0; j < data.length; j++) {
                csvList[i - 1][j] = data[j].trim();
            }
        }
        return csvList;
    }

}