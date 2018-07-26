package com.flx.administrator.mvcproject.utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

/**
 * Created by zhaoxuesong on 2017/7/21.
 */

public class TimeUtil {

    private final static long MS_OF_DAY = 24 * 3600 * 1000;
    public final static String DATE_FORMAT = "yyyy-MM-dd";
    public final static String DATE_TIME_FORMAT = "yyyy-MM-dd HH:mm:ss";

    public static String getChatTimeStr(long timeStamp){
        if (timeStamp==0) return "";
        Calendar inputTime = Calendar.getInstance();
        inputTime.setTimeInMillis(timeStamp*1000);
        Date currenTimeZone = inputTime.getTime();
        Calendar calendar = Calendar.getInstance();

        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        if (calendar.before(inputTime)){
            SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
            return sdf.format(currenTimeZone);
        }
        if (!calendar.after(inputTime)){
            //当前时间在输入时间之前
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日");
            return sdf.format(currenTimeZone);
        }
        calendar.add(Calendar.DAY_OF_MONTH,-1);
        if (calendar.before(inputTime)){
            SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
            return "昨天 "+sdf.format(currenTimeZone);
        }else{
            calendar.set(Calendar.DAY_OF_MONTH, 1);
            calendar.set(Calendar.MONTH, Calendar.JANUARY);
            if (calendar.before(inputTime)){
                SimpleDateFormat sdf = new SimpleDateFormat("M月d日HH:mm");
                return sdf.format(currenTimeZone);
            }else{
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日HH:mm");
                return sdf.format(currenTimeZone);
            }

        }
    }

    public static Date dateFormString(final String timeStr) {
        if (null != timeStr) {
            String format = DATE_FORMAT;
            if (timeStr.contains(" ") && timeStr.contains(":")) {
                format = DATE_TIME_FORMAT;
            }
            return dateFormString(timeStr, format);
        }
        return new Date();
    }

    public static Date dateFormString(final String timeStr, final String format) {
        if (null != timeStr) {
            try {
                return new SimpleDateFormat(format).parse(timeStr);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return new Date();
    }
    public static String friendlyTime(final String date) {
        return friendlyTime(dateFormString(date));
    }
    public static int countPassDay(final Date date, final int max) {
        if (new Date().getTime() - date.getTime() > max * MS_OF_DAY) {
            return max;
        }
        final Calendar c1 = Calendar.getInstance();
        c1.setTimeZone(TimeZone.getTimeZone("GMT+8:00"));
        c1.setTime(new Date());
        int year1 = c1.get(Calendar.YEAR);
        int day1 = c1.get(Calendar.DAY_OF_YEAR);

        final Calendar c2 = Calendar.getInstance();
        c2.setTimeZone(TimeZone.getTimeZone("GMT+8:00"));
        c2.setTime(date);
        int year2 = c2.get(Calendar.YEAR);
        int day2 = c2.get(Calendar.DAY_OF_YEAR);

        int pass = 0;
        if (year1 == year2) {
            pass = day1 - day2;
        } else {
            pass = 365 - day2 + day1;
        }
        return pass >= 0 ? pass : 0;
    }
    /**
     * @param date
     * @return <li>一天内：显示 时分"XX:XX"；</li>
     * <li>昨天：仅显示昨天；</li>
     * <li>一周内：仅显示周几；</li>
     * <li>超过一周：仅显示月日“XX-XX”;</li>
     * <li>超过一年：仅显示年月日"XX-xx-xx;</li>
     */
    public static String friendlyTime(final Date date) {
        int passDay = TimeUtil.countPassDay(date, 365);
        if (passDay <= 0) {
            return FormatUtil.formatDate(date, "HH:mm");
        } else if (passDay == 1) {
            return "昨天 ";
        } else if (passDay < 7) {
            return chineseWeek(dayOfWeek(date));
        } else if (passDay < 30) {
            return FormatUtil.formatDate(date, "MM-dd");
        }
        return FormatUtil.formatDate(date, "yyyy-MM-dd");
    }

    private final static String[] weekCN = {
            "周日", "周一", "周二", "周三", "周四", "周五", "周六",
    };

    public static int dayOfWeek(final Date date) {
        final Calendar c = Calendar.getInstance();
        c.setTimeZone(TimeZone.getTimeZone("GMT+8:00"));
        c.setTime(date);
        return c.get(Calendar.DAY_OF_WEEK) - 1;
    }

    public static String chineseWeek(int dayOfWeek) {
        if (dayOfWeek < 0) {
            dayOfWeek = 0;
        } else if (dayOfWeek >= weekCN.length) {
            dayOfWeek = weekCN.length - 1;
        }
        return weekCN[dayOfWeek];
    }
}
