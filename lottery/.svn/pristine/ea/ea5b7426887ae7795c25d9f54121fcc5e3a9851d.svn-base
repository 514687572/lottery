package com.lottery.net.utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Map;

/**
 * 
 * 
 * 日期工具
 * 
 */
public class DateUtils {

	public static final int MIN_SCE = 60;

	public static final int MIN_HOUR = 60;

	public static final int MIN_DAY = 24 * 60;

	public static final int SECONDS_DAY = 24 * 60 * 60;

	public static final int MILLISECONDS_PER_DAY = 24 * 60 * 60 * 1000;
	
	public static final int MILLISECONDS_PER_WEEK = 7 * MILLISECONDS_PER_DAY;

	public static final int MILLISECONDS_PER_HOUR = 60 * 60 * 1000;

	public static final int MILLISECONDS_PER_MIN = 60 * 1000;

	public static final int MILLISECONDS_PER_5_MIN = 5 * 60 * 1000;

	public static final int SECONDS_PER_HOUR = 60 * 60;

	public static final int MILLISECONDS = 1000;

	public static final int SECONDS_HALF_DAY = 12 * 60 * 60;

	public static final int HOUR_8_MIN = 8 * 60;// 8小时分钟数

	private static final Map<String, DateFormat> formats = new HashMap<String, DateFormat>();

	private static final java.text.SimpleDateFormat dateFormat = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	private static final java.text.SimpleDateFormat dateShortFormat = new java.text.SimpleDateFormat("yyyy-MM-dd");

	private static final java.text.SimpleDateFormat dayDateFormat = new java.text.SimpleDateFormat("yyyyMMdd");
	private static final java.util.Date date = new java.util.Date();
	private static final java.util.Date nowDate = new java.util.Date();

	/**
	 * 根据传入的描述样式格式化日期对象
	 * 
	 * @param date
	 * @param pattern
	 * @return String
	 */
	public static String chatFormateDate(Date date) {
		return formateDate(date, "MM-dd HH:mm");
	}

	/**
	 * 根据传入的描述样式格式化日期对象
	 * 
	 * @param date
	 * @param pattern
	 * @return String
	 */
	public static String formateDate(Date date) {
		return formateDate(date, null);
	}

	/**
	 * 根据传入的描述样式格式化日期对象
	 * 
	 * @param date
	 * @param pattern
	 * @return String
	 */
	public static String formateDate(Date date, String pattern) {
		if (pattern == null || "".equals(pattern)) {
			pattern = "yyyy-MM-dd HH:mm:ss";
		}
		DateFormat format = formats.get(pattern);
		if (format == null) {
			format = new SimpleDateFormat(pattern);
			formats.put(pattern, format);
		}
		return format.format(date);
	}

	/**
	 * 将字符串转换成日期对象
	 * 
	 * @param d
	 * @return
	 * @throws ParseException
	 */
	public static Date parseDate(String d) {
		try {
			return parseDate(d, null);
		} catch (ParseException e) {

			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 将字符串转换成日期对象
	 * 
	 * @param d
	 * @param pattern
	 * @return
	 * @throws ParseException
	 */
	public static Date parseDate(String d, String pattern) throws ParseException {
		if (pattern == null || "".equals(pattern)) {
			pattern = "yyyy-MM-dd HH:mm:ss";
		}
		DateFormat format = formats.get(pattern);
		if (format == null) {
			format = new SimpleDateFormat(pattern);
			formats.put(pattern, format);
		}
		return format.parse(d);
	}

	/**
	 * 计算两个日期相差秒
	 * 
	 * @param begin
	 *            开始日期
	 * @param end
	 *            结束日期
	 * @return 相差秒
	 */
	public static int dateDiffSec(long begin, long end) {
		long diff = end - begin;
		return (int) (diff / 1000);
	}

	/**
	 * 计算两个日期相差分数
	 * 
	 * @param begin
	 *            开始日期
	 * @param end
	 *            结束日期
	 * @return 相差天数
	 */
	public static int dateDiffMin(long begin, long end) {
		long diff = end - begin;
		return (int) (diff / MILLISECONDS_PER_MIN);
	}

	/**
	 * 计算两个日期相差天数 <font color=red>这里包含了小时和分</font>
	 * 
	 * @param begin
	 *            开始日期
	 * @param end
	 *            结束日期
	 * @return 相差天数
	 */
	public static int dateDiffDay(long begin, long end) {
		long diff = end - begin;
		return (int) (diff / MILLISECONDS_PER_DAY);
	}

	public static Date getDate() {
		Calendar cal = Calendar.getInstance();
		return cal.getTime();
	}

	public static Date getDate(long timeMils) {
		Calendar cal = Calendar.getInstance();
		cal.setTimeInMillis(timeMils);
		return cal.getTime();
	}

	/**
	 * 增加天数
	 */
	public static Date nextDay(int day) {
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.DAY_OF_YEAR, day);
		return calendar.getTime();
	}

	/**
	 * 增加秒数
	 */
	public static Date nextDate(int sec) {
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.SECOND, sec);
		return calendar.getTime();
	}

	/**
	 * 计算两个日期相差天数
	 * 
	 * @param begin
	 *            开始日期
	 * @param end
	 *            结束日期
	 * @return 相差天数
	 */
	public static int dateDiffDay(Date begin, Date end) {
		return Math.abs(dateDiffDay(begin.getTime(), end.getTime()));
	}

	/**
	 * 是否是同一天
	 * 
	 * @param src
	 * @param dest
	 * @return
	 */
	public static boolean isSameDay(Date src, Date dest) {
		String DATE_FORMAT = "yyyy-MM-dd";
		java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat(DATE_FORMAT);
		String date1Str = sdf.format(src);
		String date2Str = sdf.format(dest);
		return date1Str.equals(date2Str);
	}


	/**
	 * 是否同一周 
	 */
	public static boolean isSameWeek(long time1,long time2){
		Calendar c1 = Calendar.getInstance();
		c1.setTimeInMillis(time1 - MILLISECONDS_PER_DAY);
		Calendar c2 = Calendar.getInstance();
		c2.setTimeInMillis(time2 - MILLISECONDS_PER_DAY);
		return c1.get(Calendar.YEAR) == c2.get(Calendar.YEAR) && c1.get(Calendar.WEEK_OF_YEAR) == c2.get(Calendar.WEEK_OF_YEAR);
	}
	
	/**
	 * 相差几周 
	 * @throws ParseException 
	 */
	public static int diffWeek(long begin,long end) throws ParseException{
		Calendar c1 = Calendar.getInstance();
		c1.setTimeInMillis(begin);
		Calendar c2 = Calendar.getInstance();
		c2.setTimeInMillis(end);
		int diff = getDiffDays(c1.getTime(), c2.getTime());
		int week = diff / 7;
		int day = diff % 7;
		int weekday = c1.get(Calendar.DAY_OF_WEEK);//java中的星期要快当前的1天	1 2 3 4 5 6 7   
		weekday = weekday - 1;
		if(weekday == 0){
			weekday = 7;
		}
		int tmp = weekday + day;
		int temp = tmp > 7 ? tmp - 7 : tmp;				//                        	  1 2 3 4 5 6 7
		if(weekday > temp){
			return week + 1;
		}
		return week;
	}
	
	/**
	 * 取整数时间
	 * 
	 * @return
	 */
	public static Date get24HourDay() {
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);
		Date date = calendar.getTime();
		return date;
	}

	/**
	 * 取整数时间
	 * 
	 * @return
	 */
	public static Date get24HourDay(Date time) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(time);
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);
		Date date = calendar.getTime();
		return date;
	}

	/**
	 * 是否同一个月
	 * 
	 * @param src
	 * @param dest
	 * @return
	 */
	public static boolean isSameMonth(Date src, Date dest) {
		Calendar calsrc = Calendar.getInstance();
		calsrc.setTime(src);

		Calendar destsrc = Calendar.getInstance();
		destsrc.setTime(dest);

		return calsrc.get(Calendar.MONTH) == destsrc.get(Calendar.MONTH);
	}

	/**
	 * 判断是否为同一天
	 * 
	 * @param src
	 * @param dest
	 * @return
	 */
	public static boolean isSameDay(long src, long dest) {
		return isSameDay(new Date(src), new Date(dest));
	}

	/**
	 * 今天是星期几.
	 * 
	 * 星期天是0.
	 * 
	 * @return
	 */
	public static int getDayOfWeek() {
		return getDayOfWeek(Calendar.getInstance());
	}

	/**
	 * 今天是这个月的第几天
	 * 
	 * @return
	 */
	public static int getDayOfMonth() {
		Calendar cal = Calendar.getInstance();
		return cal.get(Calendar.DAY_OF_MONTH);
	}

	/**
	 * 今天是这个月的第几天
	 * 
	 * @return
	 */
	public static int getMonthOfYear() {
		return Calendar.getInstance().get(Calendar.MONTH) + 1;
	}

	public static int getHour() {
		return getHour(Calendar.getInstance());
	}

	public static int getMinutes() {
		return getMinutes(Calendar.getInstance());
	}

	public static int getSeconds() {
		return getSeconds(Calendar.getInstance());
	}

	public static int getCurrentSeconds() {
		return (int) (DateUtils.getDate().getTime() / 1000);
	}

	public static int getCurrentMin() {
		return (int) (DateUtils.getDate().getTime() / (60 * 1000));
	}

	public static int getDateMin(Date date) {
		return (int) (DateUtils.getDate().getTime() / (60 * 1000));
	}

	public static int getSeconds(Calendar cal) {
		return cal.get(Calendar.SECOND);
	}

	public static int getHour(Calendar cal) {
		return cal.get(Calendar.HOUR_OF_DAY);
	}

	public static int getMinutes(Calendar cal) {
		return cal.get(Calendar.MINUTE);
	}

	/**
	 * 指定日期是星期几.
	 * 
	 * 星期天是0.
	 * 
	 * @param cal
	 *            日历
	 * @return
	 */
	public static int getDayOfWeek(Calendar cal) {
		return cal.get(Calendar.DAY_OF_WEEK) - 1;
	}

	/**
	 * 指定日期是星期几.
	 * 
	 * 星期天是0.
	 * 
	 * @param date
	 *            日期
	 * @return
	 */
	public static int getDayOfWeek(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		return getDayOfWeek(cal);
	}

	/**
	 * 返回天时间
	 * 
	 * @return
	 */
	public static int getDayMin() {
		Calendar cal = Calendar.getInstance();
		int min = cal.get(Calendar.HOUR_OF_DAY) * 60 + cal.get(Calendar.MINUTE);
		return min;
	}

	/**
	 * 指定日期的当前分钟
	 * 
	 * @param date
	 *            日期
	 * @return
	 */
	public static int getMinutesOfDay(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		return getMinutes(cal);
	}

	/**
	 * 指定日期的当前小时.
	 * 
	 * @param date
	 *            日期
	 * @return
	 */
	public static int getHourOfDay(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		return getHour(cal);
	}

	/**
	 * 指定日期是星期几.
	 * 
	 * 星期天是0.
	 * 
	 * @param date
	 *            日期
	 * @return
	 */
	public static int getDayOfWeek(long timeMils) {
		Calendar cal = Calendar.getInstance();
		cal.setTimeInMillis(timeMils);
		return getDayOfWeek(cal);
	}

	/**
	 * 拷贝时间
	 * 
	 * @param date
	 * @return
	 */
	public static Date copyFrom(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		return cal.getTime();
	}

	/**
	 * 获取当天指定时、分、秒的时间
	 * 
	 * @param hour
	 * @param minute
	 * @param second
	 * @return
	 */
	public static Date getDateByTime(int hour, int minute, int second) {
		Calendar cal = Calendar.getInstance();
		cal.setTimeInMillis(System.currentTimeMillis());
		cal.set(Calendar.HOUR_OF_DAY, hour);
		cal.set(Calendar.MINUTE, minute);
		cal.set(Calendar.SECOND, second);
		cal.set(Calendar.MILLISECOND, 0);
		return cal.getTime();
	}

	/**
	 * 获取当天指定时、分、秒的时间
	 * 
	 * @param hour
	 * @param minute
	 * @param second
	 * @return
	 */
	public static Date getNextDateByTime(int hour, int minute, int second) {
		Calendar cal = Calendar.getInstance();
		cal.setTimeInMillis(System.currentTimeMillis());
		cal.set(Calendar.HOUR_OF_DAY, hour);
		cal.set(Calendar.MINUTE, minute);
		cal.set(Calendar.SECOND, second);
		cal.set(Calendar.MILLISECOND, 0);
		if (Calendar.getInstance().getTime().getTime() > cal.getTime().getTime()) {
			cal.add(Calendar.DAY_OF_YEAR, 1);
		}
		return cal.getTime();
	}

	/**
	 * 获取beforDay - 》 afterDay经过了多少天:跨年都可以
	 * 
	 * @param beforDay
	 * @param afterDay
	 * @return 最大返回30天
	 */
	public static int getPassDay(long beforTime, long afterTime) {
		if (beforTime >= afterTime) {
			return 0;
		}
		Calendar beforcal = Calendar.getInstance();
		beforcal.setTimeInMillis(beforTime);
		Calendar aftercal = Calendar.getInstance();
		aftercal.setTimeInMillis(afterTime);
		int day = 0;
		while (day < 30 && (beforcal.get(Calendar.YEAR) < aftercal.get(Calendar.YEAR) || beforcal.get(Calendar.DAY_OF_YEAR) < aftercal.get(Calendar.DAY_OF_YEAR))) {
			beforcal.add(Calendar.DAY_OF_YEAR, 1);
			day++;
		}
		return day;
	}

	/**
	 * 获取beforDay - 》 afterDay经过了多少天:跨年都可以
	 * 
	 * @param beforDay
	 * @param afterDay
	 */
	public static int getPassDay(Calendar start, Calendar end) {
		if (start.getTimeInMillis() > end.getTimeInMillis())
			return 0;
		Calendar copyStart = Calendar.getInstance();
		copyStart.setTimeInMillis(start.getTimeInMillis());
		
		Calendar copyEnd = Calendar.getInstance();
		copyEnd.setTimeInMillis(end.getTimeInMillis());
		
		copyStart.set(Calendar.HOUR_OF_DAY, 0);
		copyStart.set(Calendar.MINUTE, 0);
		copyStart.set(Calendar.SECOND, 0);
		copyStart.set(Calendar.MILLISECOND, 0);
		
		copyEnd.set(Calendar.HOUR_OF_DAY, 0);
		copyEnd.set(Calendar.MINUTE, 0);
		copyEnd.set(Calendar.SECOND, 0);
		copyEnd.set(Calendar.MILLISECOND, 0);
		return dateDiffDay(copyStart.getTimeInMillis(), copyEnd.getTimeInMillis());
	}
	
	/**
	 * 增加秒数
	 */
	public static Date addDaySec(Date date, int sec) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.SECOND, sec);
		return calendar.getTime();
	}

	/**
	 * 增加天数
	 */
	public static Date addDay(Date date, int day) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.DAY_OF_YEAR, day);
		return calendar.getTime();
	}

	/**
	 * 获取指定时间到当天指定时间的间隔时间（单位：分钟）
	 * 
	 * @param beforTime
	 * @param hour
	 * @param minute
	 * @return 可能返回负数，表示在之前
	 */
	public static int getIntervalDay(long beforTime, int hour, int minute) {
		Calendar cal = Calendar.getInstance();
		cal.setTimeInMillis(beforTime);
		cal.set(Calendar.HOUR_OF_DAY, hour);
		cal.set(Calendar.MINUTE, minute);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);

		return (int) ((cal.getTimeInMillis() - beforTime) / 60000);
	}

	/**
	 * 与当前时间的秒差
	 * 
	 * @param date
	 * @return
	 */
	public static int getSecDay(Date date) {
		Calendar cal = Calendar.getInstance();
		return (int) ((cal.getTime().getTime() - date.getTime()) / 1000);
	}

	public static String getCurrentTime() {
		return getCurrentTime(System.currentTimeMillis());
	}

	public static String getCurrentTime(long time) {
		date.setTime(time);
		return dateFormat.format(date);
	}

	public static String getTimeShortString(long time) {
		date.setTime(time);
		return dateShortFormat.format(date);
	}

	public static int compareDay(long timeMillions) {
		nowDate.setTime(System.currentTimeMillis());
		date.setTime(timeMillions);

		String str1 = dayDateFormat.format(date);
		String str2 = dayDateFormat.format(nowDate);

		return str1.compareTo(str2);
	}

	public static String showMinSec(int sec) {
		int min = sec / 60;
		int s_sec = sec % 60;
		return min + ":" + s_sec;
	}

	/**
	 * 下一个小时
	 * 
	 * @return
	 */
	public static Date getNextHour() {

		GregorianCalendar gc = new GregorianCalendar();

		gc.set(gc.get(GregorianCalendar.YEAR), gc.get(GregorianCalendar.MONTH), gc.get(GregorianCalendar.DAY_OF_MONTH), gc.get(GregorianCalendar.HOUR_OF_DAY) + 1, 0, 0);

		return gc.getTime();

	}

	/**
	 * 
	 * @param hour
	 *            延迟的小时设置
	 * @param minute
	 *            延迟的分钟设置
	 * @return
	 */
	public static Date getNextDay(int hour, int minute) {

		GregorianCalendar gc = new GregorianCalendar();

		gc.set(gc.get(GregorianCalendar.YEAR), gc.get(GregorianCalendar.MONTH), gc.get(GregorianCalendar.DAY_OF_MONTH) + 1, hour, minute, 30);

		return gc.getTime();

	}

	/**
	 * 得到下个星期的第一天，星期天为下个星期的第一天
	 * 
	 * @return
	 */
	public static Date getNextWeek() {

		GregorianCalendar gc = new GregorianCalendar();

		gc.set(gc.get(GregorianCalendar.YEAR), gc.get(GregorianCalendar.MONTH),
				gc.get(GregorianCalendar.DAY_OF_MONTH) + 1 + (7 - GregorianCalendar.getInstance().get(GregorianCalendar.DAY_OF_WEEK)), 0, 0, 30);

		return gc.getTime();

	}

	
	/**
	 * 得到当前时间为星期几(1-7)
	 * @return
	 */
	public static int getLocalDayOfWeek () {
		Calendar now = Calendar.getInstance();
		//一周第一天是否为星期天
		boolean isFirstSunday = (now.getFirstDayOfWeek() == Calendar.SUNDAY);
		//获取周几
		int weekDay = now.get(Calendar.DAY_OF_WEEK);
		//若一周第一天为星期天，则-1
		if (isFirstSunday) {
			weekDay = weekDay - 1;
			if (weekDay == 0) {
				weekDay = 7;
			}
		}
		return weekDay;
	}

	/**
	 * 返回整小时时间
	 * 
	 * @param hour
	 * @return
	 */
	public static Date getDateHour(int hour) {
		return getDateByTime(hour, 0, 0);
	}
	
	/**
	 * 某个时期的持续几天以后的时间
	 * @param date
	 * @param days
	 * @return
	 */
	public static Date fewDaysLater(Date date, int days) {
		Calendar calendar = new GregorianCalendar();
		calendar.setTime(date);
		calendar.add(Calendar.DATE, days);// 把日期往后增加一天.整数往后推,负数往前移动
		date = calendar.getTime(); // 这个时间就是日期往后推一天的结果
		return date;
	}
	
	/**
	 * 是否在两个时间之内
	 * @param startDate
	 * @param closeDate
	 * @return
	 */
	public static boolean isWithInTime(Date startDate,Date closeDate) {
		long currentTime = System.currentTimeMillis();
		return currentTime > startDate.getTime() && currentTime < closeDate.getTime();
	}
	/**
	 * 判断周期活动开启与否
	 * @param openDay周期开始的第几天开启
	 * @param continued 持续天数
	 * @param revolution 持续周期
	 * @param startDate 周期开始时间
	 * @return
	 */
	public static boolean revolutionActivityIsopen(int openDay,int continued,int revolution,Date startDate){
		boolean isopen = false;
		Date currentDate = Calendar.getInstance().getTime();
		int day = DateUtils.dateDiffDay(startDate,currentDate)%revolution;
		
		if(startDate.getTime() > currentDate.getTime() && day != 0){
			return false;
		}
		if(day >= openDay && day < (continued+openDay)){
			isopen = true;
		}
		return isopen;
	}
	
	/**
	 * 得到一天的开始时间
	 * @param time
	 * @return
	 * @throws ParseException
	 */
	public static Date getStartTime(Date time) throws ParseException{
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		return sdf.parse(sdf.format(time));	
	}
	/**
	 * 得到日期相差的天数：不是精确的每秒，仅仅是日期之间:都将天转化成00点来计算
	 * @throws ParseException 
	 */
	public static int getDiffDays(Date start,Date end) throws ParseException{
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		start = sdf.parse(sdf.format(start));
		end = sdf.parse(sdf.format(end));
		return dateDiffDay(start, end);
	}

	/**
	 * 得到occupy下个周六
	 * @param occupy
	 * @return
	 */
	public static long getNextSat(long occupy) {
		Date date = new Date();
		Calendar calendar = Calendar.getInstance();
		do{
			occupy = occupy + MILLISECONDS_PER_DAY;
			date.setTime(occupy);
			calendar.setTime(date);
		}while(calendar.get(Calendar.DAY_OF_WEEK) != Calendar.SATURDAY);
		calendar.set(Calendar.HOUR_OF_DAY, 12);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		return calendar.getTimeInMillis();
	}
	
	/**
	 * 某个时间是否在两个时间点之间
	 */
	public static boolean isBetween(long start,long end, long time){
		return time > start && time < end;
	}
	
    /**获取当前时间的整点小时时间
     * @param date
     * @return
     */
    public static long getCurrHourTime(Date date){
        Calendar ca = Calendar.getInstance();
        ca.set(Calendar.MINUTE, 0);
        ca.set(Calendar.SECOND, 0);
        date = ca.getTime();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        return Long.valueOf(sdf.format(date));
    }
	
}
