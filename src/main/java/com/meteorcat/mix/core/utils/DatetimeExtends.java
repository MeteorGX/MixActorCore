package com.meteorcat.mix.core.utils;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Optional;

/**
 * 时间日期扩展
 * @author MeteorCat
 */
public class DatetimeExtends {

    /**
     * 毫秒
     */
    public static Long MILLIS_VALUE = 1000L;

    /**
     * 标准格式化时间
     */
    public static String DATETIME_FORMAT = "yyyy-MM-dd HH:mm:ss";


    /**
     * 获取Linux时间戳
     * @return int
     */
    public static int timestamp(){
        return (int) (System.currentTimeMillis()/MILLIS_VALUE);
    }


    /**
     * 针对时间戳转日期
     * @param format 格式化字符串
     * @param timestamp 时间戳
     * @return String
     */
    public static String date(String format,int timestamp){
        return date(format,timestamp * MILLIS_VALUE);
    }

    /**
     * 针对时间戳转日期
     * @param format 格式化字符串
     * @param millis 毫秒
     * @return String
     */
    public static String date(String format,long millis){
        SimpleDateFormat df = new SimpleDateFormat(format);
        return df.format(new Date(millis));
    }

    /**
     * 针对时间戳转日期
     * @param format 格式化字符串
     * @param date 毫秒
     * @return String
     */
    public static String date(String format,Date date){
        SimpleDateFormat df = new SimpleDateFormat(format);
        return df.format(date);
    }


    /**
     * 通过日期时间字符串转换成时间戳
     * @param dt 日期时间字符串
     * @return Long|Empty
     */
    public static Optional<Long> str2time(String dt){
        SimpleDateFormat df = new SimpleDateFormat(DATETIME_FORMAT);
        try{
            return Optional.of(df.parse(dt).getTime());
        }catch (ParseException exception){
            exception.printStackTrace();
            return Optional.empty();
        }
    }

}
