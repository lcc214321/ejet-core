package com.ejet.core.kernel.web.utils;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ValidUtil {

    /**
     * 判断日期格式是否相符
     *
     * @param str
     * @param pattern
     * @return
     */
    public static boolean isDate(String str, String pattern) {
        DateFormat dateFormat = new SimpleDateFormat(pattern);
        try {
            Date date = (Date)dateFormat.parse(str);
            return str.equals(dateFormat.format(date));
        }catch(Exception e){
        }
        return false;
    }

    /**
     * 判断字符串是否为数字,0-9重复0次或者多次
     *
     * @param strnum
     * @return
     */
    private static boolean isNumeric(String strnum) {
        Pattern pattern = Pattern.compile("[0-9]*");
        Matcher isNum = pattern.matcher(strnum);
        if (isNum.matches()) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 功能：判断字符串出生日期是否符合正则表达式：包括年月日，闰年、平年和每月31天、30天和闰月的28天或者29天
     *
     * @return
     */
    public static boolean isBirthDay(String strDate) {
        Pattern pattern = Pattern.compile(
                "^((\\d{2}(([02468][048])|([13579][26]))[\\-\\/\\s]?((((0?[13578])|(1[02]))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(3[01])))|(((0?[469])|(11))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(30)))|(0?2[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])))))|(\\d{2}(([02468][1235679])|([13579][01345789]))[\\-\\/\\s]?((((0?[13578])|(1[02]))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(3[01])))|(((0?[469])|(11))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(30)))|(0?2[\\-\\/\\s]?((0?[1-9])|(1[0-9])|(2[0-8]))))))?$");
        Matcher m = pattern.matcher(strDate);
        if (m.matches()) {
            return true;
        } else {
            return false;
        }
    }


    /*
     * 判断第18位校验码是否正确 第18位校验码的计算方式： 1. 对前17位数字本体码加权求和 公式为：S = Sum(Ai * Wi), i =
     * 0, ... , 16 其中Ai表示第i个位置上的身份证号码数字值，Wi表示第i位置上的加权因子，其各位对应的值依次为： 7 9 10 5 8 4
     * 2 1 6 3 7 9 10 5 8 4 2 2. 用11对计算结果取模 Y = mod(S, 11) 3. 根据模的值得到对应的校验码
     * 对应关系为： Y值： 0 1 2 3 4 5 6 7 8 9 10 校验码： 1 0 X 9 8 7 6 5 4 3 2
     */
    private static boolean isVarifyCode(String Ai, String IDStr) {
        String[] VarifyCode = { "1", "0", "X", "9", "8", "7", "6", "5", "4", "3", "2" };
        String[] Wi = { "7", "9", "10", "5", "8", "4", "2", "1", "6", "3", "7", "9", "10", "5", "8", "4", "2" };
        int sum = 0;
        for (int i = 0; i < 17; i++) {
            sum = sum + Integer.parseInt(String.valueOf(Ai.charAt(i))) * Integer.parseInt(Wi[i]);
        }
        int modValue = sum % 11;
        String strVerifyCode = VarifyCode[modValue];
        Ai = Ai + strVerifyCode;
        if (IDStr.length() == 18) {
            if (Ai.equals(IDStr) == false) {
                return false;
            }
        }
        return true;
    }


    /**
     * 验证身份证
     * @param cardId
     * @return null 代表合法的身份证 ,其他值代表错误信息
     */
    public static String isCardId(String cardId, String SEP) throws IOException {
        String tipInfo = null;// 记录错误信息
        String Ai = "";
        String birthday = null;
        String sex = null;
        SEP = (SEP==null ? "-" : SEP);
        if(null == cardId || cardId.trim().isEmpty()) {
            tipInfo = "身份证号码长度应该为15位或18位。";
            throw new IOException(tipInfo);
        }
        // 判断号码的长度 15位或18位
        if (cardId.length() != 15 && cardId.length() != 18) {
            tipInfo = "身份证号码长度应该为15位或18位。";
            throw new IOException(tipInfo);
        }
        // 18位身份证前17位位数字，如果是15位的身份证则所有号码都为数字
        //第15位代表性别，奇数为男，偶数为女。
        //第17位代表性别，奇数为男，偶数为女。
        if (cardId.length() == 18) {
            Ai = cardId.substring(0, 17);
            sex = cardId.substring(16, 17);
        } else if (cardId.length() == 15) { //年份补2位
            sex = cardId.substring(14, 15);
            Ai = cardId.substring(0, 6) + "19" + cardId.substring(6, 15);
        }
        if (isNumeric(Ai) == false) {
            tipInfo = "身份证15位号码都应为数字; 18位号码除最后一位外，都应为数字。";
            throw new IOException(tipInfo);
        }
        // 判断出生年月是否有效
        String strYear = Ai.substring(6, 10);// 年份
        String strMonth = Ai.substring(10, 12);// 月份
        String strDay = Ai.substring(12, 14);// 日期
        if (isBirthDay(strYear + "-" + strMonth + "-" + strDay) == false) {
            tipInfo = "身份证出生日期无效。";
            throw new IOException(tipInfo);
        }
        GregorianCalendar gc = new GregorianCalendar();
        SimpleDateFormat s = new SimpleDateFormat("yyyy-MM-dd");
        try {
            if ((gc.get(Calendar.YEAR) - Integer.parseInt(strYear)) > 200
                    || (gc.getTime().getTime() - s.parse(strYear + "-" + strMonth + "-" + strDay).getTime()) < 0) {
                tipInfo = "身份证生日不在有效范围。";
                throw new IOException(tipInfo);
            }
        } catch (NumberFormatException e) {
            throw new IOException(tipInfo);
        } catch (java.text.ParseException e) {
            throw new IOException(tipInfo);
        }
        if (Integer.parseInt(strMonth) > 12 || Integer.parseInt(strMonth) == 0) {
            tipInfo = "身份证月份无效";
            throw new IOException(tipInfo);
        }
        if (Integer.parseInt(strDay) > 31 || Integer.parseInt(strDay) == 0) {
            tipInfo = "身份证日期无效";
            throw new IOException(tipInfo);
        }
        birthday = strYear + SEP + strMonth + SEP + strDay;
//        // 判断地区码是否有效
//        Hashtable areacode = GetAreaCode();
//        // 如果身份证前两位的地区码不在Hashtable，则地区码有误
//        if (areacode.get(Ai.substring(0, 2)) == null) {
//            tipInfo = "身份证地区编码错误。";
//            throw new CoBusinessException(tipInfo);
//        }
        //判断第18位校验码是否正确
        if (isVarifyCode(Ai, cardId) == false) {
            tipInfo = "身份证校验码无效，不是合法的身份证号码";
            throw new IOException(tipInfo);
        }
        return birthday;
    }


}
