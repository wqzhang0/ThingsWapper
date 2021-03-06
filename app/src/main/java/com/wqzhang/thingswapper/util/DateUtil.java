package com.wqzhang.thingswapper.util;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by wqzhang on 16-12-30.
 * 日期时间转换工具类
 */

public class DateUtil {


    public static String FILE_NAME = "MMddHHmmssSSS";
    public static String DEFAULT_PATTERN = "yyyy-MM-dd";
    public static String DIR_PATTERN = "yyyy/MM/dd/";
    public static String TIMESTAMP_PATTERN = "yyyy-MM-dd HH:mm";
    public static String TIMES_PATTERN = "HH:mm:ss";
    public static String NOCHAR_PATTERN = "yyyyMMddHHmmss";
    public static String CHOICE_PATTERN = "yyyy年MM月dd日 HH mm";
    public static String LOOPVIEW_PATTERN = "yyyy年MM月dd日";

    /**
     * 获取当前时间戳
     *
     * @param
     * @return
     */
    public static String formatDefaultFileName() {
        return formatDateByFormat(new Date(), FILE_NAME);
    }

    /**
     * 日期转换为字符串
     *
     * @param date   日期
     * @param format 日期格式
     * @return 指定格式的日期字符串
     */
    public static String formatDateByFormat(Date date, String format) {
        String result = "";
        if (date != null) {
            try {
                SimpleDateFormat sdf = new SimpleDateFormat(format);
                result = sdf.format(date);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        return result;
    }

    /**
     * 转换为默认格式(yyyy-MM-dd)的日期字符串
     *
     * @param date
     * @return
     */
    public static String formatDefaultDate(Date date) {
        return formatDateByFormat(date, DEFAULT_PATTERN);
    }

    /**
     * 转换为目录格式(yyyy/MM/dd/)的日期字符串
     *
     * @param date
     * @return
     */
    public static String formatDirDate(Date date) {
        return formatDateByFormat(date, DIR_PATTERN);
    }

    /**
     * 转换为完整格式(yyyy-MM-dd HH:mm)的日期字符串
     *
     * @param date
     * @return
     */
    public static String formatTimesTampDate(Date date) {
        return formatDateByFormat(date, TIMESTAMP_PATTERN);
    }

    /**
     * 转换为时分秒格式(HH:mm:ss)的日期字符串
     *
     * @param date
     * @return
     */
    public static String formatTimesDate(Date date) {
        return formatDateByFormat(date, TIMES_PATTERN);
    }

    /**
     * 转换为时分秒格式(HH:mm:ss)的日期字符串
     *
     * @param date
     * @return
     */
    public static String formatNoCharDate(Date date) {
        return formatDateByFormat(date, NOCHAR_PATTERN);
    }

    /**
     * 日期格式字符串转换为日期对象
     *
     * @param strDate 日期格式字符串
     * @param pattern 日期对象
     * @return
     */
    public static Date parseDate(String strDate, String pattern) {
        try {
            SimpleDateFormat format = new SimpleDateFormat(pattern);
            Date nowDate = format.parse(strDate);
            return nowDate;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 字符串转换为默认格式(yyyy-MM-dd)日期对象
     *
     * @param date
     * @return
     * @throws Exception
     */
    public static Date parseDefaultDate(String date) {
        return parseDate(date, DEFAULT_PATTERN);
    }

    /**
     * 字符串转换为完整格式(yyyy-MM-dd HH:mm:ss)日期对象
     *
     * @param date
     * @return
     * @throws Exception
     */
    public static Date parseTimesTampDate(String date) {
        return parseDate(date, TIMESTAMP_PATTERN);
    }

    /**
     * 获得当前时间
     *
     * @return
     */
    public static Date getCurrentDate() {
        Calendar calendar = Calendar.getInstance();
        return calendar.getTime();
    }

    /**
     * sql Date 转 util Date
     *
     * @param date java.sql.Date日期
     * @return java.util.Date
     */
    public static Date parseUtilDate(java.sql.Date date) {
        return date;
    }

    /**
     * util Date 转 sql Date
     *
     * @param date java.sql.Date日期
     * @return
     */
    public static java.sql.Date parseSqlDate(Date date) {
        return new java.sql.Date(date.getTime());
    }

    /**
     * 获取年份
     *
     * @param date
     * @return
     */
    public static int getYear(Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        return c.get(Calendar.YEAR);
    }

    /**
     * 获取月份
     *
     * @param date
     * @return
     */
    public static int getMonth(Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        return c.get(Calendar.MONTH) + 1;
    }

    /**
     * 获取星期
     *
     * @param date
     * @return
     */
    public static int getWeek(Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        int dayOfWeek = c.get(Calendar.DAY_OF_WEEK);
        dayOfWeek = dayOfWeek - 1;
        if (dayOfWeek == 0) {
            dayOfWeek = 7;
        }
        return dayOfWeek;
    }

    /**
     * 获取星期
     *
     * @param date
     * @return
     */
    public static String getWeekChina(Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        int dayOfWeek = c.get(Calendar.DAY_OF_WEEK);
        dayOfWeek = dayOfWeek - 1;
        if (dayOfWeek == 0) {
            dayOfWeek = 7;
        }
        if (dayOfWeek == 1) {
            return "星期一";
        }
        if (dayOfWeek == 2) {
            return "星期二";
        }
        if (dayOfWeek == 3) {
            return "星期三";
        }
        if (dayOfWeek == 4) {
            return "星期四";
        }
        if (dayOfWeek == 5) {
            return "星期五";
        }
        if (dayOfWeek == 6) {
            return "星期六";
        }
        if (dayOfWeek == 7) {
            return "星期日";
        }
        return "";
    }


    /**
     * 获取星期 返回结果 带 今天和昨天
     *
     * @param date
     * @return
     */
    public static int getWeekHasToday(Date date) {
        Calendar c = Calendar.getInstance();

        c.setTime(date);

        c.add(Calendar.DATE, -1);

        int result = diffDate(new Date(), c.getTime());
        if (result == 0) {
            return -2;
        } else if (result == 1) {
            return -1;
        }
        int dayOfWeek = c.get(Calendar.DAY_OF_WEEK);
        dayOfWeek = dayOfWeek - 1;
        if (dayOfWeek == 0) {
            dayOfWeek = 7;
        }
        return dayOfWeek;
    }


    /**
     * 获取日期(多少号)
     *
     * @param date
     * @return
     */
    public static int getDay(Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        return c.get(Calendar.DAY_OF_MONTH);
    }

    /**
     * 获取当前时间(小时)
     *
     * @param date
     * @return
     */
    public static int getHour(Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        return c.get(Calendar.HOUR_OF_DAY);
    }

    /**
     * 获取当前时间(分)
     *
     * @param date
     * @return
     */
    public static int getMinute(Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        return c.get(Calendar.MINUTE);
    }

    /**
     * 获取当前时间(秒)
     *
     * @param date
     * @return
     */
    public static int getSecond(Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        return c.get(Calendar.SECOND);
    }

    /**
     * 获取当前毫秒
     *
     * @param date
     * @return
     */
    public static long getMillis(Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        return c.getTimeInMillis();
    }

    /**
     * 日期增加
     *
     * @param date Date
     * @param day  int
     * @return Date
     */
    public static Date addDate(Date date, int day) {
        java.util.Calendar c = java.util.Calendar.getInstance();
        c.setTimeInMillis(getMillis(date) + ((long) day) * 24 * 3600 * 1000);
        return c.getTime();
    }

    /**
     * 日期相减(返回天数)
     *
     * @param date  Date
     * @param date1 Date
     * @return int 相差的天数
     */
    public static int diffDate(Date date, Date date1) {
        return (int) ((getMillis(date) - getMillis(date1)) / (24 * 3600 * 1000));
    }

    /**
     * 日期相减(返回秒值)
     *
     * @param date  Date
     * @param date1 Date
     * @return int
     * @author
     */
    public static Long diffDateTime(Date date, Date date1) {
        return (Long) ((getMillis(date) - getMillis(date1)) / 1000);
    }

    /**
     * 时间对比   如果date>data1 返回true
     *
     * @param date
     * @param date1
     * @return
     */
    public static boolean leDateTime(Date date, Date date1) {
        if (date.getTime() > date1.getTime()) {
            return true;
        }
        return false;
    }

    private static String[] randomValues = new String[]{"0", "1", "2", "3",
            "4", "5", "6", "7", "8", "9", "a", "b", "c", "d", "e", "f", "g",
            "h", "i", "j", "k", "l", "m", "n", "u", "t", "s", "o", "x", "v",
            "p", "q", "r", "w", "y", "z"};

    public static String getRandomNumber(int lenght) {
        StringBuffer str = new StringBuffer();
        for (int i = 0; i < lenght; i++) {
            Double number = Math.random() * (randomValues.length - 1);
            str.append(randomValues[number.intValue()]);
        }
        return str.toString();
    }

    public static ArrayList<Date> sortAsc(List<Date> dateList) {
        ArrayList<Date> newDateList = new ArrayList<>();

//
//        for (int i = 1; i < dateList.size(); i++) {
//            int j = i - 1;
//            Date temp = dateList.get(i);
//            for (; j >= 0 && temp.getTime() < dateList.get(j).getTime(); j--) {
//                dateList.get(j + 1) = dateList.get(j);  //将大于temp的值整体后移一个单位
//            }
//            dateList.get(j + 1) = temp;
//        }

        return newDateList;
    }

    /**
     * 对比传入时间  和 当前时间的 小时,分,秒 判断是否超过时间
     *
     * @param date
     * @return
     */
    public static boolean reachCurrentTime(Date date) {
        boolean isReach = false;
        Date nowDate = new Date();
        if (getHour(date) > getHour(nowDate)) {
            //已经超过
            isReach = true;
        } else if (getHour(date) == getHour(nowDate)) {
            //继续对比分钟
            if (getMinute(date) > getMinute(nowDate)) {
                //超过时间
                isReach = true;
            } else if (getMinute(date) == getMinute(nowDate)) {
                //分钟相等,继续比较秒
                if (getMillis(date) > getMillis(nowDate)) {
                    isReach = true;
                } else {
                    //未超过
                }
            } else {
                //小于,未超过
            }
        } else {
            //未超过
        }
        return isReach;
    }

    /**
     * 采用今天的日期,和传入的时分秒
     *
     * @param date
     * @return
     */
    public static Date produceDete(Date date) {
        Calendar nowCalendar = Calendar.getInstance();
        nowCalendar.setTime(new Date());

        Calendar targetCalendar = Calendar.getInstance();
        targetCalendar.setTime(date);

        nowCalendar.set(Calendar.HOUR, targetCalendar.get(Calendar.HOUR));
        nowCalendar.set(Calendar.MINUTE, targetCalendar.get(Calendar.MINUTE));
        nowCalendar.set(Calendar.MILLISECOND, targetCalendar.get(Calendar.MILLISECOND));

        return nowCalendar.getTime();
    }
}
