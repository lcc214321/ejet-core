package com.ejet.core.kernel.utils;


import java.util.Date;
import java.util.HashSet;

/**
 * 日期格式化类
 */
public class DateFormatUtil {


    static HashSet<String> patterns = new HashSet<>();

    static {

        String d1 = "2018/7/24";
        String d2 = "2018/7/24 07-23";
        String d3 = "2018.07.18";
        String d4 = "2018年7月18日";
        String d5 = "2018-7-18";
        String d6 = "2018年7月18日";

        patterns.add("yyyy/M/dd");
        patterns.add("yyyy/MM/dd");
        patterns.add("yyyy/M/dd MM-dd");
        patterns.add("yyyy/MM/dd MM-dd");
        patterns.add("yyyy年M月dd日");
        patterns.add("yyyy年mm月dd日");
        patterns.add("yyyy.MM.dd");
        patterns.add("yyyy-MM-dd HH:mm:sss");
        patterns.add("yyyy-MM-dd HH:mm:sss");
        patterns.add("yyyy-MM-dd HH:mm:sss");
    }


    public static Date parse(String dateStr, String pattern) {
        Date date = null;
        try {
            return DateUtil.parse(dateStr, pattern);
        } catch (Exception e) {
            //throw Exceptions.unchecked(e);
        }
        return date;
    }

    /**
     * 格式日期输出
     * @param dateStr
     * @return
     */
    public static Date formatToDate(String dateStr) {
        Date date = new Date();
        for(String pattern : patterns) {
            Date temp = parse(dateStr, pattern);
            if(temp!=null) {
                date = temp;
                break;
            }
        }
        return date;
    }

    public static String format(String dateStr) {
        return DateUtil.format(formatToDate(dateStr), DateUtil.PATTERN_DATETIME_LONG);
    }


    public static void main(String[] args) {



        String d1 = "2018/7/25";
        String d2 = "2018/7/24 07-23";
        String d3 = "2018.07.18";
        String d4 = "2018年7月19日";
        String d5 = "2018-7-16";
        String d6 = "2018年7月28日";

        System.out.println(format(d1));
        System.out.println(format(d2));
        System.out.println(format(d3));
        System.out.println(format(d4));
        System.out.println(format(d5));
        System.out.println(format(d6));
    }


}
