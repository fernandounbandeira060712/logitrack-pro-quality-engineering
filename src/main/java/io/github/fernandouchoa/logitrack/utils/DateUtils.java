package io.github.fernandouchoa.logitrack.utils;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public final class DateUtils {

    private static final DateTimeFormatter DATE_FORMAT =
            DateTimeFormatter.ofPattern("dd/MM/yyyy");

    private DateUtils() {
    }

    public static String today() {
        return LocalDate.now().format(DATE_FORMAT);
    }

    public static String daysFromNow(long days) {
        return LocalDate.now().plusDays(days).format(DATE_FORMAT);
    }
}