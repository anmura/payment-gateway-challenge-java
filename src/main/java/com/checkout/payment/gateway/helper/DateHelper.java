package com.checkout.payment.gateway.helper;

import java.time.YearMonth;

public class DateHelper {

  public static boolean monthYearAreInTheFuture(int month, int year) {
    validateMonthYear(month, year);

    YearMonth given = YearMonth.of(year, month);
    YearMonth current = YearMonth.now();

    return !given.isBefore(current);
  }

  private static void validateMonthYear(int month, int year) {
    if (month < 1 || month > 12) {
      throw new IllegalArgumentException("Expiry month must be between 1 and 12");
    }
    if (year < 1) {
      throw new IllegalArgumentException("Year must be greater than 0");
    }
  }

}
