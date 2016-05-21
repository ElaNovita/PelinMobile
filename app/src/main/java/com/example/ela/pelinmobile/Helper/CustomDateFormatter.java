package com.example.ela.pelinmobile.Helper;

import android.content.Context;
import android.util.Log;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

/**
 * Created by e on 4/05/16.
 */
public class CustomDateFormatter {

    private static final int SECOND_MILLIS = 1000;
    private static final int MINUTE_MILLIS = 60 * SECOND_MILLIS;
    private static final int HOUR_MILLIS = 60 * MINUTE_MILLIS;
    private static final int DAY_MILLIS = 24 * HOUR_MILLIS;
    long daysToGo, hoursToGo, minutesToGo, secondToGo;

    public String format(String dates) throws ParseException {
//        Date date = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'").parse(dates);
//        return new SimpleDateFormat("yyyy-MM-dd'T'HH:mm").format(date);
        Date date = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSSSS").parse(dates);



        return new SimpleDateFormat("EEE, d MMM yyyy HH:mm").format(date);

    }

    public String formatTime(String dates) throws ParseException {

        Date date = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSSSS").parse(dates);

        return new SimpleDateFormat("HH:mm").format(date);

    }

    public Date toDate(String dates) throws ParseException {

        Date date = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSSSS").parse(dates);

        date.getTime();
        return date;

    }

    public String getTimeAgo(String dates) throws ParseException{

        SimpleDateFormat d = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        Date date = d.parse(dates);
        long time = date.getTime();

        if (time < 1000000000000L) {
            time *= 1000;
        }


        long now = System.currentTimeMillis();
        if (time > now || time <= 0) {
            return null;
        }

        final long diff = now - time;
        if (diff < MINUTE_MILLIS) {
            return "baru saja";
        } else if (diff < 2 * MINUTE_MILLIS) {
            return "semenit yang lalu";
        } else if (diff < 50 * MINUTE_MILLIS) {
            return diff / MINUTE_MILLIS + " menit yang lalu";
        } else if (diff < 90 * MINUTE_MILLIS) {
            return "sejam yang lalu";
        } else if (diff < 24 * HOUR_MILLIS) {
            return diff / HOUR_MILLIS + " jam yang lalu";
        } else if (diff < 48 * HOUR_MILLIS) {
            return "kemarin";
        } else if (diff < 72) {
            return "2 hari yang lalu";
        } else {
            return new SimpleDateFormat("EEE, d MMM yyyy ").format(date);
        }
    }

    public String getTimeLater(String dates) throws ParseException {

        SimpleDateFormat d = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        Date date = d.parse(dates);
        long time = date.getTime();

        if (time < 1000000000000L) {
            time *= 1000;
        }

        long now = System.currentTimeMillis();
        if (time > now || time <= 0) {
            final long diff = time - now;
            if (diff < MINUTE_MILLIS) {
                return "beberapa saat lagi";
            } else if (diff < 2 * MINUTE_MILLIS) {
                return "semenit lagi";
            } else if (diff < 50 * MINUTE_MILLIS) {
                return diff / MINUTE_MILLIS + " menit lagi";
            } else if (diff < 90 * MINUTE_MILLIS) {
                return "1 jam lagi";
            } else if (diff < 48 * HOUR_MILLIS) {
                return diff / HOUR_MILLIS + " jam lagi";
            } else if (diff < 48 * HOUR_MILLIS) {
                return "yesterday";
            } else if (diff < 72 * HOUR_MILLIS) {
                return "2 hari lagi";
            } else {
                return new SimpleDateFormat("EEE, d MMM yyyy HH:mm").format(date);
            }
        } else {
            final long diff = now - time;
            if (diff < MINUTE_MILLIS) {
                return "beberapa saat lalu";
            } else if (diff < 2 * MINUTE_MILLIS) {
                return "semenit lau";
            } else if (diff < 50 * MINUTE_MILLIS) {
                return diff / MINUTE_MILLIS + " menit lalu";
            } else if (diff < 90 * MINUTE_MILLIS) {
                return "1 jam lagi";
            } else if (diff < 48 * HOUR_MILLIS) {
                return diff / HOUR_MILLIS + " jam lalu";
            } else if (diff < 48 * HOUR_MILLIS) {
                return "yesterday";
            } else if (diff < 96 * HOUR_MILLIS) {
                return diff / DAY_MILLIS + " hari yang lalu";
            } else {
                return new SimpleDateFormat("EEE, d MMM yyyy HH:mm").format(date);
            }
        }
    }

    public long getToday(String dates) throws ParseException{

        SimpleDateFormat d = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        Date date = d.parse(dates);
        long time = date.getTime();

        if (time < 1000000000000L) {
            time *= 1000;
        }

        long now = System.currentTimeMillis();

        return time - now;
    }

    public long getTimeRemain(String dates) {
        long ms = 0;

        try {
            ms = getToday(dates);
        } catch (ParseException e) {
            Log.e("respon", "onCreate: ", e);
        }

        if (ms > DAY_MILLIS) {
            daysToGo = ms / DAY_MILLIS;
            ms %= DAY_MILLIS;
        }
        if (ms > HOUR_MILLIS) {
            hoursToGo = ms / HOUR_MILLIS;
            ms %= HOUR_MILLIS;
        }
        if (ms > MINUTE_MILLIS) {
            minutesToGo = ms / MINUTE_MILLIS;
            ms %= MINUTE_MILLIS;
        }
        if (ms > SECOND_MILLIS) {
            secondToGo = ms / SECOND_MILLIS;
            ms %= SECOND_MILLIS;
        }

        return secondToGo * 1000 + minutesToGo * 1000 * 60 + hoursToGo * 1000 * 60 * 60 + daysToGo * 1000 * 60 * 60 * 24;
    }
}
