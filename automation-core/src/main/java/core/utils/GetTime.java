package core.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class GetTime {

    public static String getCurrentTime() {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
        return df.format(new Date());
        //System.out.println();//
    }

    public static String getCurrentTimeM() {
        SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");//设置日期格式
        return df.format(new Date());
        //System.out.println();//
    }

    public static String getCurrentDate() {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");//设置日期格式
        return df.format(new Date());
        //System.out.println();//
    }

    public static String timeToDateStr(Long time) {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
        return df.format(time);
    }

    public static long dateStrToTime(String date) {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
        long result = 0;
        try {
            df.parse(date).getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return result;
    }

    public static long getTimeStamp(){
        Date date = new Date();
        return date.getTime();
        //System.out.println();//
    }
}
