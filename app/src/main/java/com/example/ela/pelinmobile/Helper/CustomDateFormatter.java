package com.example.ela.pelinmobile.Helper;

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

    public String format(String dates) {
        Date date = new Date();
        try {
            date = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'").parse(dates);

        } catch (ParseException e) {

        }
        return new SimpleDateFormat("dd-MM-yyyy HH:mm").format(date);
    }

}
