package com.example.fogostore.common.utils;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateTimeUtils {
    public static String getLocalTime(Date time) {
        try {
            return new SimpleDateFormat("dd/MM/yyyy HH:mm").format(time);
        } catch (Exception e) {
            return "";
        }
    }
}
