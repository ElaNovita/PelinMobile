package com.example.ela.pelinmobile.Helper;

import android.content.Context;
import android.util.Log;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

/**
 * Created by e on 4/05/16.
 */
public class CustomDateFormatter {

    private static final int SECOND_MILLIS = 1000;
    private static final int MINUTE_MILLIS = 60 * SECOND_MILLIS;
    private static final int HOUR_MILLIS = 60 * MINUTE_MILLIS;
    private static final int DAY_MILLIS = 24 * HOUR_MILLIS;

    public String format(String dates) throws ParseException{
//        Date date = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'").parse(dates);
//        return new SimpleDateFormat("yyyy-MM-dd'T'HH:mm").format(date);
        Date date = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'").parse(dates);

        return new SimpleDateFormat("dd MM yyyy").format(date);

    }

    public long toLong(String dates) {
        Date date = new Date();
        long ms;

        try {
            date = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSSSS'Z'").parse(dates);
        } catch (Exception e) {

        }

        ms = date.getTime();
        return ms;
    }

    public String getTimeAgo(long time) {
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
        } else if (diff < 2 * MINUTE_MILLIS){
            return "semenit yang lalu";
        } else if (diff < 50 * MINUTE_MILLIS) {
            return diff / MINUTE_MILLIS + " menit yang lalu";
        } else if (diff < 90 * MINUTE_MILLIS) {
            return "sejam yang lalu";
        } else if (diff < 48 * HOUR_MILLIS) {
            return diff / HOUR_MILLIS + " jam yang lalu";
        } else if (diff < 48 * HOUR_MILLIS) {
            return "kemarin";
        } else {
            return diff / DAY_MILLIS + " hari yang lalu";
        }
    }

    public String getTimeLater(String dates) {

        SimpleDateFormat d = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
        long time = 0;
        try {
            Date date = d.parse(dates);
            time = date.getTime();
        } catch (ParseException e) {

        }

        if (time < 1000000000000L) {
            time *= 1000;
        }

        long now = System.currentTimeMillis();
        if (time > now || time <= 0) {
            return null;
        }



        final long diff = time - now;
        Log.d("respon", "getTimeLater: " + diff);
        Log.d("respon", "getTimeLater: time " + time);
        Log.d("respon", "getTimeLater: now" + now);
        if (diff < MINUTE_MILLIS) {
            return "beberapa saat lagi";
        } else if (diff < 2 * MINUTE_MILLIS){
            return "semenit lagi";
        } else if (diff < 50 * MINUTE_MILLIS) {
            return diff / MINUTE_MILLIS + " menit lagi";
        } else if (diff < 90 * MINUTE_MILLIS) {
            return "1 jam lagi";
        } else if (diff < 48 * HOUR_MILLIS) {
            return diff / HOUR_MILLIS + " jam lagi";
        } else if (diff < 48 * HOUR_MILLIS) {
            return "yesterday";
        } else {
            return diff / DAY_MILLIS + " harilagi";
        }
    }


}
// string->
