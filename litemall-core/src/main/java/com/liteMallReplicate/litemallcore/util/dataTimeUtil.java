package com.liteMallReplicate.litemallcore.util;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
public class dataTimeUtil {
    public static String getDataTimeDisplayString(LocalDateTime dateTime) {
        DateTimeFormatter dtf2 = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
        String strDate2 = dtf2.format(dateTime);

        return strDate2;
    }
}
