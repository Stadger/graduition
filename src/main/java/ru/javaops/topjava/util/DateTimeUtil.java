package ru.javaops.topjava.util;

import lombok.experimental.UtilityClass;

import java.time.*;

@UtilityClass
public class DateTimeUtil {
    public final static LocalTime DEADLINE = LocalTime.of(11,0);

    public static Clock createClock(LocalDate date, LocalTime time) {
        return Clock.fixed(LocalDateTime.of(date, time).atZone(ZoneId.systemDefault()).toInstant(), ZoneId.systemDefault());
    }
}
