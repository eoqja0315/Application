package com.dbhong.application.Utility;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Utility {

    public static String getCurrenTime() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        return simpleDateFormat.format(new Date(System.currentTimeMillis()));
    }
}
