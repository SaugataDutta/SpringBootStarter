package com.tgt.mkt.cam.utils;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

public class DateUtils {
    private static final String CONSUMER_DATE_FORMAT = "yyyy-MM-dd'T'HH:mm:ssZ";
    public static void main(String[] args) {
        DateTimeFormatter format = DateTimeFormatter.ofPattern(CONSUMER_DATE_FORMAT);
        ZonedDateTime currentInIndia = ZonedDateTime.now();
        System.out.println(currentInIndia);
        ZoneId centralZoneId = ZoneId.of("America/Chicago");
        ZonedDateTime currentInMpls = currentInIndia.withZoneSameInstant(centralZoneId);
        System.out.println(currentInMpls);
        System.out.println(currentInMpls.format(format));
        System.out.println(currentInMpls.toLocalDateTime());
    }
}
