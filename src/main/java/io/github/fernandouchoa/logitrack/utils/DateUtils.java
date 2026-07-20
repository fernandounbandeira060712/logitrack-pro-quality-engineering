package io.github.fernandouchoa.logitrack.utils;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public final class DateUtils {

    private static final DateTimeFormatter DISPLAY_DATE =
            DateTimeFormatter.ofPattern("dd/MM/yyyy");

    private static final DateTimeFormatter HTML_DATE =
            DateTimeFormatter.ISO_LOCAL_DATE;

    private DateUtils() {
    }

    public static String today() {
        return LocalDate.now().format(DISPLAY_DATE);
    }

    public static String daysFromNow(long days) {
        return LocalDate.now()
                .plusDays(days)
                .format(DISPLAY_DATE);
    }

    public static String isoToday() {
        return LocalDate.now().format(HTML_DATE);
    }

    public static String isoDaysFromNow(long days) {
        return LocalDate.now()
                .plusDays(days)
                .format(HTML_DATE);
    }
}