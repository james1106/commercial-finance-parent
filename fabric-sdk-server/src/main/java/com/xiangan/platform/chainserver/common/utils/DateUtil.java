package com.xiangan.platform.chainserver.common.utils;

import org.joda.time.DateTime;

import java.util.Date;

/**
 * 时间日期处理工具类
 *
 * @Creater liuzhudong
 * @Date 2017/4/11 17:39
 * @Version 1.0
 * @Copyrights
 */
public class DateUtil {

    /**
     * 获取unix时间戳
     *
     * @param date
     * @return
     */
    public static long toUnixTime(Date date) {
        return date.getTime() / 1000L;
    }

    /**
     * 获取指定时间日期加指定天数
     *
     * @param date
     * @param day
     * @return
     */
    public static Date addDays(Date date, int day) {
        DateTime time = new DateTime(date);
        return time.plusDays(day).toDate();
    }

    /**
     * 格式化日期
     *
     * @param date
     * @param pattern
     * @return
     */
    public static String format(Date date, String pattern) {
        DateTime time = new DateTime(date);
        return time.toString(pattern);
    }

    public static void main(String[] args) {
        Date now = new Date();
        System.out.println(toUnixTime(now));
        System.out.println(addDays(now, 10));
//        System.out.println(format(now));
    }


}
