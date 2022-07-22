package com.anymv.util;

import com.google.common.hash.Hashing;

import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class Utils {

    public static String RFC3339_PATTERN = "yyyy-MM-dd'T'HH:mm:ssXXX";

    public static int findOffset(int page, int size) {
        if (page == 0) {
            page = 1;
        }

        if (size == 0) {
            size = 1;
        }

        return ((size* page) - size);
    }

    public static String hashSha256(String input) {
        return Hashing.sha256().hashString(input, StandardCharsets.UTF_8).toString();
    }

    public static Date stringToDateUTC(String stringDate,String pattern, ZoneId zoneId) {
        LocalDateTime ldt = LocalDateTime.parse(stringDate, DateTimeFormatter.ofPattern(pattern));

        ZonedDateTime timeWithCurrentTimeZone = ldt.atZone(zoneId);
        ZonedDateTime gmtTime = timeWithCurrentTimeZone.withZoneSameInstant(ZoneId.of("UTC"));

        return Date.from(gmtTime.toInstant());
    }
}
