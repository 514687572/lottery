package com.bootdo.common.utils;

import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * 日期处理
 */
public class DateUtil extends DateUtils {
    private final static Logger logger = LoggerFactory.getLogger(DateUtils.class);

    private static String[] parsePatterns = {
            "yyyy-MM-dd", "yyyy-MM-dd HH:mm:ss", "yyyy-MM-dd HH:mm", "yyyy-MM",
            "yyyy/MM/dd", "yyyy/MM/dd HH:mm:ss", "yyyy/MM/dd HH:mm", "yyyy/MM",
            "yyyy.MM.dd", "yyyy.MM.dd HH:mm:ss", "yyyy.MM.dd HH:mm", "yyyy.MM",
            "yyyyMMdd HH:mm:ss"};

    /**
     * 得到当前日期字符串 格式（yyyy-MM-dd）
     */
    public static String getDate() {
        return getDate("yyyy-MM-dd");
    }

    /**
     * 得到当前日期字符串 格式（yyyy-MM-dd） pattern可以为："yyyy-MM-dd" "HH:mm:ss" "E"
     */
    public static String getDate(String pattern) {
        return DateFormatUtils.format(new Date(), pattern);
    }

    /**
     * 得到日期字符串 默认格式（yyyy-MM-dd） pattern可以为："yyyy-MM-dd" "HH:mm:ss" "E"
     */
    public static String formatDate(Date date, Object... pattern) {
        String formatDate;
        if (pattern != null && pattern.length > 0) {
            formatDate = DateFormatUtils.format(date, pattern[0].toString());
        } else {
            formatDate = DateFormatUtils.format(date, "yyyy-MM-dd");
        }
        return formatDate;
    }

    /**
     * 得到日期时间字符串，转换格式（yyyy-MM-dd HH:mm:ss）
     */
    public static String formatDateTime(Date date) {
        return formatDate(date, "yyyy-MM-dd HH:mm:ss");
    }

    /**
     * 得到日期时间字符串，转换格式（yyyyMMddHHmmss）
     */
    public static String formatOrderCode(Date date) {
        return formatDate(date, "yyyyMMddHHmmss");
    }

    /**
     * 得到当前时间字符串 格式（HH:mm:ss）
     */
    public static String getTime() {
        return formatDate(new Date(), "HH:mm:ss");
    }

    /**
     * 得到当前日期和时间字符串 格式（yyyy-MM-dd HH:mm:ss）
     */
    public static String getDateTime() {
        return formatDate(new Date(), "yyyy-MM-dd HH:mm:ss");
    }

    /**
     * 得到当前年份字符串 格式（yyyy）
     */
    public static String getYear() {
        return formatDate(new Date(), "yyyy");
    }

    /**
     * 得到当前月份字符串 格式（MM）
     */
    public static String getMonth() {
        return formatDate(new Date(), "MM");
    }

    /**
     * 得到当天字符串 格式（dd）
     */
    public static String getDay() {
        return formatDate(new Date(), "dd");
    }

    /**
     * 得到当前星期字符串 格式（E）星期几
     */
    public static String getWeek() {
        return formatDate(new Date(), "E");
    }

    /**
     * 日期型字符串转化为日期 格式
     * { "yyyy-MM-dd", "yyyy-MM-dd HH:mm:ss", "yyyy-MM-dd HH:mm",
     * "yyyy/MM/dd", "yyyy/MM/dd HH:mm:ss", "yyyy/MM/dd HH:mm",
     * "yyyy.MM.dd", "yyyy.MM.dd HH:mm:ss", "yyyy.MM.dd HH:mm" }
     */
    public static Date parseDate(Object str) {
        if (str == null) {
            return null;
        }
        try {
            return parseDate(str.toString(), parsePatterns);
        } catch (ParseException e) {
            return null;
        }
    }

    /**
     * 获取过去的天数
     *
     * @param date
     * @return
     */
    public static long pastDays(Date date) {
        long t = new Date().getTime() - date.getTime();
        return t / (24 * 60 * 60 * 1000);
    }

    /**
     * 获取过去的小时
     *
     * @param date
     * @return
     */
    public static long pastHour(Date date) {
        long t = new Date().getTime() - date.getTime();
        return t / (60 * 60 * 1000);
    }

    /**
     * 获取过去的分钟
     *
     * @param date
     * @return
     */
    public static long pastMinutes(Date date) {
        long t = new Date().getTime() - date.getTime();
        return t / (60 * 1000);
    }

    /**
     * 转换为时间（天,时:分:秒.毫秒）
     *
     * @param timeMillis
     * @return
     */
    public static String formatDateTime(long timeMillis) {
        long day = timeMillis / (24 * 60 * 60 * 1000);
        long hour = (timeMillis / (60 * 60 * 1000) - day * 24);
        long min = ((timeMillis / (60 * 1000)) - day * 24 * 60 - hour * 60);
        long s = (timeMillis / 1000 - day * 24 * 60 * 60 - hour * 60 * 60 - min * 60);
        long sss = (timeMillis - day * 24 * 60 * 60 * 1000 - hour * 60 * 60 * 1000 - min * 60 * 1000 - s * 1000);
        return (day > 0 ? day + "," : "") + hour + ":" + min + ":" + s + "." + sss;
    }

    /**
     * 计算两个日期之间的差
     *
     * @param minDate
     * @param maxDate
     * @return
     */
    public static int getDateBetween(Date minDate, Date maxDate) {
        Calendar min = Calendar.getInstance();
        Calendar max = Calendar.getInstance();

        min.setTime(minDate);
        max.setTime(maxDate);

        //设置时间为0时
        max.set(Calendar.HOUR_OF_DAY, 0);
        max.set(Calendar.MINUTE, 0);
        max.set(Calendar.SECOND, 0);
        min.set(Calendar.HOUR_OF_DAY, 0);
        min.set(Calendar.MINUTE, 0);
        min.set(Calendar.SECOND, 0);
        //得到两个日期相差的天数
        return ((int) (max.getTime().getTime() / 1000) - (int) (min.getTime().getTime() / 1000)) / 3600 / 24;
    }

    /**
     * date格式日期，只获取y-m-d
     *
     * @param nowDate
     * @return
     */
    public static Date getYmdDate(Date nowDate) {
        String strDate = formatDate(nowDate, "yyyy-MM-dd");
        return parseDate(strDate);
    }


    /**
     * 根据今天获取昨天
     *
     * @param today
     * @return
     */
    public static Date getYesterday(Date today) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(today);
        calendar.add(Calendar.DAY_OF_MONTH, -1);
        return calendar.getTime();
    }

    /**
     * 根据今天获取本周日期列表
     *
     * @param today
     * @return
     */
    public static List<String> getThisWeek(Date today) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(today);
        // 判断要计算的日期是否是周日，如果是则减一天计算周六的，否则会出问题，计算到下一周去了
        int dayWeek = cal.get(Calendar.DAY_OF_WEEK);// 获得当前日期是一个星期的第几天
        if (1 == dayWeek) {
            cal.add(Calendar.DAY_OF_MONTH, -1);
        }
        // System.out.println("要计算日期为:" + sdf.format(cal.getTime())); // 输出要计算日期
        // 设置一个星期的第一天，按中国的习惯一个星期的第一天是星期一
        cal.setFirstDayOfWeek(Calendar.MONDAY);
        // 获得当前日期是一个星期的第几天
        int day = cal.get(Calendar.DAY_OF_WEEK);
        // 根据日历的规则，给当前日期减去星期几与一个星期第一天的差值
        cal.add(Calendar.DATE, cal.getFirstDayOfWeek() - day);

        List<String> timeList = new ArrayList<>();
        for (int i = 0; i < 7; i++) {
            timeList.add(DateUtil.formatDate(cal.getTime(), "yyyy-MM-dd"));
            cal.add(Calendar.DAY_OF_MONTH, 1);
            if (cal.getTime().getTime() > today.getTime()) {
                break;
            }
        }
        return timeList;
    }

    /**
     * 根据今天获取上周日期列表
     *
     * @param today
     * @return
     */
    public static List<String> getLastWeek(Date today) {
        Calendar calendar1 = Calendar.getInstance();
        calendar1.setTime(today);
        Calendar calendar2 = Calendar.getInstance();
        calendar2.setTime(today);
        int dayOfWeek = calendar1.get(Calendar.DAY_OF_WEEK) - 1;
        int offset1 = 1 - dayOfWeek;
        calendar1.add(Calendar.DATE, offset1 - 7);

        List<String> timeList = new ArrayList<>();
        for (int i = 0; i < 7; i++) {
            timeList.add(DateUtil.formatDate(calendar1.getTime(), "yyyy-MM-dd"));
            calendar1.add(Calendar.DAY_OF_MONTH, 1);
        }
        return timeList;
    }

    /**
     * 根据今天获取最近n天日期列表
     *
     * @param today
     * @return
     */
    public static List<String> getLastDayDate(Date today, int count) {
        Calendar calendar1 = Calendar.getInstance();
        calendar1.setTime(today);
        // 将时分秒,毫秒域清零
        calendar1.set(Calendar.HOUR_OF_DAY, 0);
        calendar1.set(Calendar.MINUTE, 0);
        calendar1.set(Calendar.SECOND, 0);
        calendar1.set(Calendar.MILLISECOND, 0);

        List<String> timeList = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            timeList.add(DateUtil.formatDate(calendar1.getTime(), "yyyy-MM-dd"));
            calendar1.add(Calendar.DAY_OF_MONTH, -1);
        }

        return timeList;
    }

    public static void main(String[] args) {
        System.out.println(getLastMonthDate(new Date() ,1));
    }

    /**
     * 根据某个时间获取最近的n个小时列表 yyyy-MM-dd HH
     *
     * @param today
     * @return
     */
    public static List<String> getLastHourDate(Date today, int count) {
        Calendar calendar1 = Calendar.getInstance();
        calendar1.setTime(today);
        // 将分秒,毫秒域清零
        calendar1.set(Calendar.MINUTE, 0);
        calendar1.set(Calendar.SECOND, 0);
        calendar1.set(Calendar.MILLISECOND, 0);

        List<String> timeList = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            timeList.add(DateUtil.formatDate(calendar1.getTime(), "yyyy-MM-dd HH:mm:ss"));
            calendar1.add(Calendar.HOUR_OF_DAY, -1);
        }

        return timeList;
    }

    /**
     * 获取某月的日期列表
     *
     * @param today
     * @param temp  标示  1表示不超过今天
     * @return
     */
    public static List<String> getLastMonthDate(Date today, int temp) {
        Calendar cal = Calendar.getInstance();
        cal.clear();
        cal.setTime(today);//month 为指定月份任意日期
        int year = cal.get(Calendar.YEAR);
        int m = cal.get(Calendar.MONTH) + 1 ;

        cal.set(Calendar.DAY_OF_MONTH, 1);// 从一号开始
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);

        int dayNumOfMonth = getDaysByYearMonth(year, m);

        List<String> timeList = new ArrayList<>();
        for (int i = 0; i < dayNumOfMonth; i++, cal.add(Calendar.DATE, 1)) {
            if (temp == 1) {
                Date now = new Date();
                Calendar nowCal = Calendar.getInstance();
                nowCal.setTime(now);
                int nowDay = nowCal.get(Calendar.DAY_OF_MONTH);

                int forList = cal.get(Calendar.DAY_OF_MONTH);

                if (nowDay < forList) {
                    break;
                }
            }
            timeList.add(DateUtil.formatDate(cal.getTime(), "yyyy-MM-dd"));
        }


        return timeList;
    }

    //获取指定月份的天数
    public static int getDaysByYearMonth(int year, int month) {

        Calendar a = Calendar.getInstance();
        a.set(Calendar.YEAR, year);
        a.set(Calendar.MONTH, month - 1);
        a.set(Calendar.DATE, 1);
        a.roll(Calendar.DATE, -1);
        int maxDate = a.get(Calendar.DATE);
        return maxDate;
    }

    /**
     * 获取某天距某天之间间隔日期列表
     *
     * @param startDate
     * @param endDate
     * @return
     */
    public static List<String> getBetweenDate(Date startDate, Date endDate) {
        int max = 90;//最大90天
        Calendar calendar1 = Calendar.getInstance();
        calendar1.setTime(startDate);
        // 将时分秒,毫秒域清零
        calendar1.set(Calendar.HOUR_OF_DAY, 0);
        calendar1.set(Calendar.MINUTE, 0);
        calendar1.set(Calendar.SECOND, 0);
        calendar1.set(Calendar.MILLISECOND, 0);
        Calendar calendar2 = Calendar.getInstance();
        calendar2.setTime(endDate);
        calendar2.set(Calendar.HOUR_OF_DAY, 0);
        calendar2.set(Calendar.MINUTE, 0);
        calendar2.set(Calendar.SECOND, 0);
        calendar2.set(Calendar.MILLISECOND, 0);

        List<String> timeList = new ArrayList<>();
        for (int i = 0; i < max; i++) {
            timeList.add(DateUtil.formatDate(calendar1.getTime(), "yyyy-MM-dd"));
            calendar1.add(Calendar.DAY_OF_MONTH, 1);
            if (calendar1.getTime().getTime() > calendar2.getTime().getTime()) {
                break;
            }
        }
        return timeList;
    }

    /**
     * @param args
     * @throws ParseException
     */
//    public static void main(String[] args) {
//        System.err.println(formatDate(parseDate("2010/3/6")));
//        System.err.println(getDate("yyyy年MM月dd日 E"));
//
//        String nowDate = formatDate(new Date(), "yyyy-MM-dd");
//        System.out.println("当前日期：" + nowDate);
//        Date parseDate = parseDate(nowDate);
//        System.out.println("转化日期：" + parseDate);
//
//        // 传入参数date
//        Date minDate = parseDate("2018-02-02");
//        System.out.println("最小日期：" + minDate);
//        Date maxDate = parseDate("2018-07-02");
//        System.out.println("最大日期：" + maxDate);
//        Date jintian = parseDate("2018-06-08");
//        System.out.println("今天：" + jintian);
//
//        // 计算时间差
//        int between1 = getDateBetween(parseDate, minDate);
//        System.out.println("1111：" + between1);
//        int between2 = getDateBetween(parseDate, maxDate);
//        System.out.println("2222：" + between2);
//        int b = getDateBetween(parseDate, jintian);
//        System.out.println("3333：" + b);
//
//        Date today = new Date();
//        Calendar max = Calendar.getInstance();
//        max.setTime(today);
//
//        //设置时间为0时
//        max.add(Calendar.DAY_OF_MONTH, -1);
//
//        System.out.println(getLastMonthDate(new Date(), 1).toString());
//
//        System.out.println(max.getTime());
//    }

    /**
     * 日期增加天数
     *
     * @param date
     * @param iCount
     * @return
     */
    public static Date getAddDate(Date date, int iCount) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.DAY_OF_MONTH, iCount);
        return cal.getTime();
    }

    /**
     * 计算距离现在多久，非精确
     *
     * @param date
     * @return
     */
    public static String getTimeBefore(Date date) {
        Date now = new Date();
        long l = now.getTime() - date.getTime();
        long day = l / (24 * 60 * 60 * 1000);
        long hour = (l / (60 * 60 * 1000) - day * 24);
        long min = ((l / (60 * 1000)) - day * 24 * 60 - hour * 60);
        long s = (l / 1000 - day * 24 * 60 * 60 - hour * 60 * 60 - min * 60);
        String r = "";
        if (day > 0) {
            r += day + "天";
        } else if (hour > 0) {
            r += hour + "小时";
        } else if (min > 0) {
            r += min + "分";
        } else if (s > 0) {
            r += s + "秒";
        }
        r += "前";
        return r;
    }

    /**
     * 计算距离现在多久，精确
     *
     * @param date
     * @return
     */
    public static String getTimeBeforeAccurate(Date date) {
        Date now = new Date();
        long l = now.getTime() - date.getTime();
        long day = l / (24 * 60 * 60 * 1000);
        long hour = (l / (60 * 60 * 1000) - day * 24);
        long min = ((l / (60 * 1000)) - day * 24 * 60 - hour * 60);
        long s = (l / 1000 - day * 24 * 60 * 60 - hour * 60 * 60 - min * 60);
        String r = "";
        if (day > 0) {
            r += day + "天";
        }
        if (hour > 0) {
            r += hour + "小时";
        }
        if (min > 0) {
            r += min + "分";
        }
        if (s > 0) {
            r += s + "秒";
        }
        r += "前";
        return r;
    }
}
