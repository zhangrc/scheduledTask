package com.yinhai.sheduledTask.frame.util;

import com.yinhai.sheduledTask.frame.exception.SysLevelException;
import org.apache.commons.lang.time.DateUtils;

import java.sql.Time;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by zrc on 2016/11/28.
 */
public class DateUtil extends DateUtils {
    public static final String TIMESTAMPFORMAT = "yyyy-MM-dd HH:mm:ss";
    public static final String TIMEFORMAT = "HH:mm:ss";
    public static final String DATEFORMAT = "yyyy-MM-dd";
    public static final String DATETIMEFORMAT = "yyyy-MM-dd HH:mm:ss";
    public static final int COMPUTE_YEAR = 1;
    public static final int COMPUTE_MONTH = 2;
    public static final int COMPUTE_DAY = 5;

    public DateUtil() {
    }

    public static String getFullCurrentTime() {
        String var0 = null;
        long var1 = System.currentTimeMillis();
        Date var3 = new Date(var1);
        var0 = DateFormat.getDateTimeInstance().format(var3);
        return var0;
    }

    public static String getCurrentTime() {
        String var0 = null;
        SimpleDateFormat var1 = new SimpleDateFormat("yyyyMMddHHmmss");
        Date var2 = new Date();
        var0 = var1.format(var2);
        return var0;
    }

    public static String getCurrentTime(String var0) {
        String var1 = null;
        SimpleDateFormat var2 = new SimpleDateFormat(var0);
        Date var3 = new Date();
        var1 = var2.format(var3);
        return var1;
    }

    public static Date getCurrentDateTime() {
        return new Date(System.currentTimeMillis());
    }

    public static int getWeekOfYear() {
        Calendar var0 = Calendar.getInstance();
        return var0.get(3);
    }

    public static int getMonth() {
        Calendar var0 = Calendar.getInstance();
        return var0.get(2) + 1;
    }

    public static String getCurDateTime() {
        Calendar var0 = Calendar.getInstance();
        SimpleDateFormat var1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String var2 = var1.format(var0.getTime());
        return var2;
    }

    public static Date getCurDate() {
        Calendar var0 = Calendar.getInstance();
        SimpleDateFormat var1 = new SimpleDateFormat("yyyy-MM-dd");
        String var2 = var1.format(var0.getTime());
        return java.sql.Date.valueOf(var2);
    }

    public static java.sql.Date getDate() {
        Calendar var0 = Calendar.getInstance();
        return getDate(var0.get(1), var0.get(2) + 1, var0.get(5));
    }

    public static java.sql.Date getDate(int var0, int var1, int var2) {
        if(!verityDate(var0, var1, var2)) {
            throw new IllegalArgumentException("This is illegimate date!");
        } else {
            Calendar var3 = Calendar.getInstance();
            var3.clear();
            var3.set(var0, var1 - 1, var2);
            return new java.sql.Date(var3.getTime().getTime());
        }
    }

    public static java.sql.Date getDate(String var0, String var1, String var2) {
        int var3 = Integer.parseInt(var0);
        int var4 = Integer.parseInt(var1);
        int var5 = Integer.parseInt(var2);
        return getDate(var3, var4, var5);
    }

    public static Date getDate(int var0, int var1, int var2, int var3, int var4, int var5) {
        Calendar var6 = Calendar.getInstance();

        try {
            var6.set(var2, var0 - 1, var1, var3, var4, var5);
        } catch (Exception var8) {
            return null;
        }

        return new Date(var6.getTime().getTime());
    }

    public static Date getDate(String var0, String var1, String var2, String var3, String var4, String var5) {
        int var6 = Integer.parseInt(var2);
        int var7 = Integer.parseInt(var0);
        int var8 = Integer.parseInt(var1);
        int var9 = Integer.parseInt(var3);
        int var10 = Integer.parseInt(var4);
        int var11 = Integer.parseInt(var5);
        return getDate(var7, var8, var6, var9, var10, var11);
    }

    public static boolean verityDate(int var0, int var1, int var2) {
        boolean var3 = false;
        if(var1 >= 1 && var1 <= 12 && var2 >= 1 && var2 <= 31) {
            if(var1 != 4 && var1 != 6 && var1 != 9 && var1 != 11) {
                if(var1 == 2) {
                    if((var0 % 100 == 0 || var0 % 4 != 0) && var0 % 400 != 0) {
                        if(var2 <= 28) {
                            var3 = true;
                        }
                    } else if(var2 <= 29) {
                        var3 = true;
                    }
                } else {
                    var3 = true;
                }
            } else if(var2 <= 30) {
                var3 = true;
            }
        }

        return var3;
    }

    public static String dateToString(Date var0) {
        if(var0 == null) {
            return null;
        } else {
            String var1 = "";
            SimpleDateFormat var2 = new SimpleDateFormat("yyyy-MM-dd");
            var1 = var2.format(var0);
            return var1;
        }
    }

    public static String datetimeToString(Date var0) {
        if(var0 == null) {
            return null;
        } else {
            String var1 = "";
            SimpleDateFormat var2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            var1 = var2.format(var0);
            return var1;
        }
    }

    public static String datetimeToString(Date var0, String var1) {
        if(var0 == null) {
            return null;
        } else {
            String var2 = "";
            SimpleDateFormat var3 = new SimpleDateFormat(var1);
            var2 = var3.format(var0);
            return var2;
        }
    }

    public static String timeToString(Date var0) {
        if(var0 == null) {
            return null;
        } else {
            String var1 = "";
            SimpleDateFormat var2 = new SimpleDateFormat("HH:mm:ss");
            var1 = var2.format(var0);
            return var1;
        }
    }

    public static Date stringToDate(String var0, String var1, String var2) {
        Date var3 = null;
        Date var4 = (new SimpleDateFormat(var1)).parse(var0, new ParsePosition(0));
        String var5 = null;
        if(var4 != null) {
            var5 = (new SimpleDateFormat(var2)).format(var4);
        }

        if(var5 != null) {
            var3 = (new SimpleDateFormat(var2)).parse(var5, new ParsePosition(0));
        }

        return var3;
    }

    public static Date stringToDate(String var0, String var1) {
        return (new SimpleDateFormat(var1)).parse(var0, new ParsePosition(0));
    }

    public static Date stringToDate(String var0) {
        return stringToDate(var0, "yyyy-MM-dd");
    }

    public static java.sql.Date utilDateToSqlDate(Date var0) {
        return var0 == null?null:new java.sql.Date(var0.getTime());
    }

    public static Time utilDateToSqlTime(Date var0) {
        return var0 == null?null:new Time(var0.getTime());
    }

    public static Timestamp utilDateToSqlTimestamp(Date var0) {
        return var0 == null?null:new Timestamp(var0.getTime());
    }

    public static java.sql.Date stringToSqlDate(String var0) {
        return stringToSqlDate(var0, "yyyy-MM-dd");
    }

    public static java.sql.Date stringToSqlDate(String var0, String var1) {
        return stringToSqlDate(var0, var1, "yyyy-MM-dd");
    }

    public static java.sql.Date stringToSqlDate(String var0, String var1, String var2) {
        return utilDateToSqlDate(stringToDate(var0, var1, var2));
    }

    public static Time stringToSqlTime(String var0) {
        return stringToSqlTime(var0, "HH:mm:ss");
    }

    public static Time stringToSqlTime(String var0, String var1) {
        return stringToSqlTime(var0, var1, "HH:mm:ss");
    }

    public static Time stringToSqlTime(String var0, String var1, String var2) {
        return utilDateToSqlTime(stringToDate(var0, var1, var2));
    }

    public static Timestamp stringToSqlTimestamp(String var0) {
        return stringToSqlTimestamp(var0, "yyyy-MM-dd HH:mm:ss");
    }

    public static Timestamp stringToSqlTimestamp(String var0, String var1) {
        return stringToSqlTimestamp(var0, var1, "yyyy-MM-dd HH:mm:ss");
    }

    public static Timestamp stringToSqlTimestamp(String var0, String var1, String var2) {
        return utilDateToSqlTimestamp(stringToDate(var0, var1, var2));
    }

    public static String toTimeString(int var0, int var1, int var2) {
        String var3;
        if(var0 < 10) {
            var3 = "0" + var0;
        } else {
            var3 = "" + var0;
        }

        String var4;
        if(var1 < 10) {
            var4 = "0" + var1;
        } else {
            var4 = "" + var1;
        }

        String var5;
        if(var2 < 10) {
            var5 = "0" + var2;
        } else {
            var5 = "" + var2;
        }

        return var2 == 0?var3 + ":" + var4:var3 + ":" + var4 + ":" + var5;
    }

    public static java.sql.Date getDayStart(java.sql.Date var0) {
        return getDayStart(var0, 0);
    }

    public static java.sql.Date getDayStart(java.sql.Date var0, int var1) {
        Calendar var2 = Calendar.getInstance();
        var2.setTime(new Date(var0.getTime()));
        var2.set(var2.get(1), var2.get(2), var2.get(5), 0, 0, 0);
        var2.add(5, var1);
        return new java.sql.Date(var2.getTime().getTime());
    }

    public static java.sql.Date getNextDayStart(java.sql.Date var0) {
        return getDayStart(var0, 1);
    }

    public static java.sql.Date getDayEnd(java.sql.Date var0) {
        return getDayEnd(var0, 0);
    }

    public static java.sql.Date getDayEnd(java.sql.Date var0, int var1) {
        Calendar var2 = Calendar.getInstance();
        var2.setTime(new Date(var0.getTime()));
        var2.set(var2.get(1), var2.get(2), var2.get(5), 23, 59, 59);
        var2.add(5, var1);
        return new java.sql.Date(var2.getTime().getTime());
    }

    public static java.sql.Date monthBegin() {
        Calendar var0 = Calendar.getInstance();
        var0.set(5, 1);
        var0.set(11, 0);
        var0.set(12, 0);
        var0.set(13, 0);
        var0.set(9, 0);
        return new java.sql.Date(var0.getTime().getTime());
    }

    public static String getDateTimeDisp(String var0) {
        if(var0 != null && !var0.equals("")) {
            DateFormat var1 = DateFormat.getDateTimeInstance(2, 2);
            long var2 = Long.parseLong(var0);
            return var1.format(new Date(var2));
        } else {
            return "";
        }
    }

    public static boolean isYear(String var0) {
        return var0 == null?false:(var0.length() != 4?false:StringUtil.isNumeric(var0));
    }

    public static boolean isMonth(String var0) {
        if(var0 == null) {
            return false;
        } else if(var0.length() != 1 && var0.length() != 2) {
            return false;
        } else if(!StringUtil.isNumeric(var0)) {
            return false;
        } else {
            int var1 = Integer.parseInt(var0);
            return var1 >= 1 && var1 <= 12;
        }
    }

    public static boolean isDay(String var0) {
        if(var0 == null) {
            return false;
        } else if(var0.length() != 1 && var0.length() != 2) {
            return false;
        } else if(!StringUtil.isNumeric(var0)) {
            return false;
        } else {
            int var1 = Integer.parseInt(var0);
            return var1 >= 1 && var1 <= 31;
        }
    }

    public static boolean isHour(String var0) {
        if(var0 == null) {
            return false;
        } else if(var0.length() != 1 && var0.length() != 2) {
            return false;
        } else if(!StringUtil.isNumeric(var0)) {
            return false;
        } else {
            int var1 = Integer.parseInt(var0);
            return var1 >= 0 && var1 <= 23;
        }
    }

    public static boolean isMinute(String var0) {
        if(var0 == null) {
            return false;
        } else if(var0.length() != 1 && var0.length() != 2) {
            return false;
        } else if(!StringUtil.isNumeric(var0)) {
            return false;
        } else {
            int var1 = Integer.parseInt(var0);
            return var1 >= 0 && var1 <= 59;
        }
    }

    public static boolean isSecond(String var0) {
        if(var0 == null) {
            return false;
        } else if(var0.length() != 1 && var0.length() != 2) {
            return false;
        } else if(!StringUtil.isNumeric(var0)) {
            return false;
        } else {
            int var1 = Integer.parseInt(var0);
            return var1 >= 0 && var1 <= 59;
        }
    }

    public static boolean isDate(String var0, String var1, String var2) {
        if(!isYear(var0)) {
            return false;
        } else if(!isMonth(var1)) {
            return false;
        } else if(!isDay(var2)) {
            return false;
        } else {
            int var3 = Integer.parseInt(var0);
            int var4 = Integer.parseInt(var1);
            int var5 = Integer.parseInt(var2);
            return verityDate(var3, var4, var5);
        }
    }

    public static boolean isDate(String var0) {
        if(StringUtil.isEmpty(var0)) {
            return false;
        } else {
            byte var1 = 45;
            int var2 = var0.indexOf(var1);
            if(var2 < 0) {
                return false;
            } else {
                String var3 = var0.substring(0, var2);
                int var4 = var0.indexOf(var1, var2 + 1);
                if(var4 < 0) {
                    return false;
                } else {
                    String var5 = var0.substring(var2 + 1, var4);
                    String var6 = var0.substring(var4 + 1);
                    return isDate(var3, var5, var6);
                }
            }
        }
    }

    private static int compute(String var0, String var1, int var2, String var3, boolean var4) {
        Date var5 = null;
        Date var6 = null;
        SimpleDateFormat var7 = null;
        if(var3 != null && var3.trim().length() > 0) {
            var7 = new SimpleDateFormat(var3);
        } else {
            var7 = new SimpleDateFormat("yyyy-MM-dd");
        }

        try {
            var5 = var7.parse(var0);
            var6 = var7.parse(var1);
            return compute(var5, var6, var2, var4);
        } catch (Exception var9) {
            throw new SysLevelException(var9.getMessage());
        }
    }

    private static int compute(Date var0, Date var1, int var2, boolean var3) {
        Calendar var4 = Calendar.getInstance();
        var4.setTime(var0);
        var4.set(10, 0);
        var4.set(12, 0);
        var4.set(13, 0);
        var4.set(14, 0);
        Calendar var5 = Calendar.getInstance();
        var5.setTime(var1);
        var5.set(10, 0);
        var5.set(12, 0);
        var5.set(13, 0);
        var5.set(14, 0);
        int var6 = 0;
        if(var4.after(var5)) {
            while(var4.after(var5)) {
                var4.add(var2, -1);
                --var6;
            }

            if(var3) {
                if(2 == var2 && var4.get(5) != var5.get(5)) {
                    ++var6;
                }

                if(1 == var2) {
                    if(var4.get(2) != var5.get(2)) {
                        ++var6;
                    } else if(var4.get(5) != var5.get(5)) {
                        ++var6;
                    }
                }
            }

            return -var6;
        } else if(!var4.before(var5)) {
            return 0;
        } else {
            while(var4.before(var5)) {
                var4.add(var2, 1);
                ++var6;
            }

            if(var3) {
                if(2 == var2 && var4.get(5) != var5.get(5)) {
                    --var6;
                }

                if(1 == var2) {
                    if(var4.get(2) > var5.get(2)) {
                        --var6;
                    } else if(var4.get(5) != var5.get(5)) {
                        --var6;
                    }
                }
            }

            return -var6;
        }
    }

    public static int computeYear(String var0, String var1, String var2, boolean var3) {
        return compute(var0, var1, 1, var2, var3);
    }

    public static int computeYear(Date var0, Date var1, boolean var2) {
        return compute(var0, var1, 1, var2);
    }

    public static int computeMonth(String var0, String var1, String var2, boolean var3) {
        return compute(var0, var1, 2, var2, var3);
    }

    public static int computeMonth(Date var0, Date var1, boolean var2) {
        return compute(var0, var1, 2, var2);
    }

    public static int computeDay(String var0, String var1, String var2) {
        return compute(var0, var1, 5, var2, true);
    }

    public static int computeDay(Date var0, Date var1) {
        return compute(var0, var1, 5, true);
    }

    public static final String fnGetStr4Y2M(String var0) {
        String var1 = var0.replaceAll("[ \\|\\-:\\.]", "");
        var1 = var1.substring(0, 6);
        return var1;
    }

    public static int getIntervalMonth(String var0, String var1) {
        if(var0.length() > 6) {
            var0 = fnGetStr4Y2M(var0);
        }

        if(var1.length() > 6) {
            var1 = fnGetStr4Y2M(var1);
        }

        int var2 = Integer.parseInt(var0.substring(0, 4));
        boolean var3 = false;
        int var7;
        if(var0.substring(4, 5).equals("0")) {
            var7 = Integer.parseInt(var0.substring(5));
        }

        var7 = Integer.parseInt(var0.substring(4, 6));
        int var4 = Integer.parseInt(var1.substring(0, 4));
        boolean var5 = false;
        int var8;
        if(var1.substring(4, 5).equals("0")) {
            var8 = Integer.parseInt(var1.substring(5));
        }

        var8 = Integer.parseInt(var1.substring(4, 6));
        int var6 = var4 * 12 + var8 - (var2 * 12 + var7);
        return var6;
    }

    public static int getIntervalMonth(Date var0, Date var1) {
        return computeMonthOnly(var0, var1);
    }

    public static int getIntervalDay(java.sql.Date var0, java.sql.Date var1) {
        long var2 = var0.getTime();
        long var4 = var1.getTime();
        long var6 = var4 - var2;
        int var8 = (int)(var6 / 86400000L);
        return var8;
    }

    public static int getIntervalDay(Timestamp var0, Timestamp var1) {
        Calendar var2 = Calendar.getInstance();
        Calendar var3 = Calendar.getInstance();
        var2.setTimeInMillis(var0.getTime());
        var3.setTimeInMillis(var1.getTime());
        var2.set(var2.get(1), var2.get(2), var2.get(5), 0, 0, 0);
        var3.set(var3.get(1), var3.get(2), var3.get(5), 0, 0, 0);
        long var4 = var2.getTimeInMillis();
        long var6 = var3.getTimeInMillis();
        long var8 = var6 - var4;
        int var10 = (int)(var8 / 86400000L);
        return var10;
    }

    public static int computeMonthOnly(Date var0, Date var1) {
        Calendar var2 = Calendar.getInstance();
        var2.setTime(var0);
        var2.set(5, 1);
        var2.set(10, 0);
        var2.set(12, 0);
        var2.set(13, 0);
        var2.set(14, 0);
        Calendar var3 = Calendar.getInstance();
        var3.setTime(var1);
        var3.set(5, 1);
        var3.set(10, 0);
        var3.set(12, 0);
        var3.set(13, 0);
        var3.set(14, 0);
        return compute(var2.getTime(), var3.getTime(), 2, true);
    }

    public static int computeMonthOnly(Date var0, Date var1, boolean var2) {
        int var3 = computeMonthOnly(var0, var1);
        if(var2) {
            if(var0.after(var1)) {
                ++var3;
            } else if(var0.before(var1)) {
                --var3;
            } else {
                var3 = 1;
            }
        }

        return var3;
    }

    public static int computeMonthOnly(String var0, String var1, String var2) {
        SimpleDateFormat var3 = new SimpleDateFormat(var2);

        try {
            Calendar var4 = Calendar.getInstance();
            var4.setTime(var3.parse(var0));
            var4.set(5, 1);
            var4.set(10, 0);
            var4.set(12, 0);
            var4.set(13, 0);
            var4.set(14, 0);
            Calendar var5 = Calendar.getInstance();
            var5.setTime(var3.parse(var1));
            var5.set(5, 1);
            var5.set(10, 0);
            var5.set(12, 0);
            var5.set(13, 0);
            var5.set(14, 0);
            return compute(var4.getTime(), var5.getTime(), 2, true);
        } catch (Exception var6) {
            throw new SysLevelException(var6.getMessage());
        }
    }

    public static int computeMonthOnly(String var0, String var1, String var2, boolean var3) {
        int var4 = computeMonthOnly(var0, var1, var2);
        if(var3) {
            Date var5 = stringToDate(var0, var2);
            Date var6 = stringToDate(var1, var2);
            if(var5.after(var6)) {
                ++var4;
            } else if(var5.before(var6)) {
                --var4;
            } else {
                var4 = 1;
            }
        }

        return var4;
    }

    public static int computeYearOnly(Date var0, Date var1) {
        Calendar var2 = Calendar.getInstance();
        var2.setTime(var0);
        var2.set(5, 1);
        var2.set(2, 1);
        var2.set(10, 0);
        var2.set(12, 0);
        var2.set(13, 0);
        var2.set(14, 0);
        Calendar var3 = Calendar.getInstance();
        var3.setTime(var1);
        var3.set(5, 1);
        var3.set(2, 1);
        var3.set(10, 0);
        var3.set(12, 0);
        var3.set(13, 0);
        var3.set(14, 0);
        return compute(var2.getTime(), var3.getTime(), 1, true);
    }

    public static int computeYearOnly(Date var0, Date var1, boolean var2) {
        int var3 = computeYearOnly(var0, var1);
        if(var2) {
            if(var0.after(var1)) {
                ++var3;
            } else if(var0.before(var1)) {
                --var3;
            } else {
                var3 = 1;
            }
        }

        return var3;
    }

    public static int computeYearOnly(String var0, String var1, String var2) {
        SimpleDateFormat var3 = new SimpleDateFormat(var2);

        try {
            Calendar var4 = Calendar.getInstance();
            var4.setTime(var3.parse(var0));
            var4.set(5, 1);
            var4.set(2, 1);
            var4.set(10, 0);
            var4.set(12, 0);
            var4.set(13, 0);
            var4.set(14, 0);
            Calendar var5 = Calendar.getInstance();
            var5.setTime(var3.parse(var1));
            var5.set(5, 1);
            var5.set(2, 1);
            var5.set(10, 0);
            var5.set(12, 0);
            var5.set(13, 0);
            var5.set(14, 0);
            return compute(var4.getTime(), var5.getTime(), 1, true);
        } catch (Exception var6) {
            throw new SysLevelException(var6.getMessage());
        }
    }

    public static int computeYearOnly(String var0, String var1, String var2, boolean var3) {
        int var4 = computeYearOnly(var0, var1, var2);
        if(var3) {
            Date var5 = stringToDate(var0, var2);
            Date var6 = stringToDate(var1, var2);
            if(var5.after(var6)) {
                ++var4;
            } else if(var5.before(var6)) {
                --var4;
            } else {
                var4 = 1;
            }
        }

        return var4;
    }

    public static int computeDateOnly(Date var0, Date var1) {
        Calendar var2 = Calendar.getInstance();
        var2.setTime(var0);
        var2.set(10, 0);
        var2.set(12, 0);
        var2.set(13, 0);
        var2.set(14, 0);
        Calendar var3 = Calendar.getInstance();
        var3.setTime(var1);
        var3.set(10, 0);
        var3.set(12, 0);
        var3.set(13, 0);
        var3.set(14, 0);
        return compute(var2.getTime(), var3.getTime(), 5, true);
    }

    public static int computeDateOnly(Date var0, Date var1, boolean var2) {
        int var3 = computeDateOnly(var0, var1);
        if(var2) {
            if(var0.after(var1)) {
                ++var3;
            } else if(var0.before(var1)) {
                --var3;
            } else {
                var3 = 1;
            }
        }

        return var3;
    }

    public static int computeDateOnly(String var0, String var1, String var2) {
        SimpleDateFormat var3 = new SimpleDateFormat(var2);

        try {
            Calendar var4 = Calendar.getInstance();
            var4.setTime(var3.parse(var0));
            var4.set(10, 0);
            var4.set(12, 0);
            var4.set(13, 0);
            var4.set(14, 0);
            Calendar var5 = Calendar.getInstance();
            var5.setTime(var3.parse(var1));
            var5.set(10, 0);
            var5.set(12, 0);
            var5.set(13, 0);
            var5.set(14, 0);
            return compute(var4.getTime(), var5.getTime(), 5, true);
        } catch (Exception var6) {
            throw new SysLevelException(var6.getMessage());
        }
    }

    public static int computeDateOnly(String var0, String var1, String var2, boolean var3) {
        int var4 = computeDateOnly(var0, var1, var2);
        if(var3) {
            Date var5 = stringToDate(var0, var2);
            Date var6 = stringToDate(var1, var2);
            if(var5.after(var6)) {
                ++var4;
            } else if(var5.before(var6)) {
                --var4;
            } else {
                var4 = 1;
            }
        }

        return var4;
    }

    public static Date nowDate() {
        return new Date();
    }

    public static Date toDate(int var0, int var1, int var2, int var3, int var4, int var5) {
        Calendar var6 = Calendar.getInstance();

        try {
            var6.set(var2, var0 - 1, var1, var3, var4, var5);
        } catch (Exception var8) {
            return null;
        }

        return new Date(var6.getTime().getTime());
    }

    public static Date toDate(String var0, String var1, String var2, String var3, String var4, String var5) {
        int var6;
        int var7;
        int var8;
        int var9;
        int var10;
        int var11;
        try {
            var6 = Integer.parseInt(var0);
            var7 = Integer.parseInt(var1);
            var8 = Integer.parseInt(var2);
            var9 = Integer.parseInt(var3);
            var10 = Integer.parseInt(var4);
            var11 = Integer.parseInt(var5);
        } catch (Exception var13) {
            return null;
        }

        return toDate(var6, var7, var8, var9, var10, var11);
    }

    public static int getDaysInMonth(Calendar var0) {
        return getDaysInMonth(var0.get(2), var0.get(1));
    }

    public static int getDaysInMonth(int var0, int var1) {
        return var0 != 1 && var0 != 3 && var0 != 5 && var0 != 7 && var0 != 8 && var0 != 10 && var0 != 12?(var0 != 4 && var0 != 6 && var0 != 9 && var0 != 11?((var1 % 4 != 0 || var1 % 100 == 0) && var1 % 400 != 0?28:29):30):31;
    }

    public static int getLastDayOfWeek(Calendar var0) {
        int var1 = var0.getFirstDayOfWeek();
        return var1 == 1?7:(var1 == 2?1:(var1 == 3?2:(var1 == 4?3:(var1 == 5?4:(var1 == 6?5:6)))));
    }

    public static boolean isGregorianDate(int var0, int var1, int var2) {
        if(var0 >= 0 && var0 <= 11) {
            int[] var3 = new int[]{31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};
            if(var0 != 1) {
                if(var1 < 0 || var1 > var3[var0]) {
                    return false;
                }
            } else {
                byte var4 = 28;
                if(var2 % 4 == 0 && var2 % 100 != 0 || var2 % 400 == 0) {
                    var4 = 29;
                }

                if(var1 < 0 || var1 > var4) {
                    return false;
                }
            }

            return true;
        } else {
            return false;
        }
    }

    public static boolean isJulianDate(int var0, int var1, int var2) {
        if(var0 >= 0 && var0 <= 11) {
            int[] var3 = new int[]{31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};
            if(var0 == 1) {
                byte var4 = 28;
                if(var2 % 4 == 0) {
                    var4 = 29;
                }

                if(var1 < 0 || var1 > var4) {
                    return false;
                }
            } else if(var1 < 0 || var1 > var3[var0]) {
                return false;
            }

            return true;
        } else {
            return false;
        }
    }

    public static void main(String[] var0) {
    }
}
