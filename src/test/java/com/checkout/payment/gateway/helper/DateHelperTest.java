package com.checkout.payment.gateway.helper;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.YearMonth;
import org.junit.jupiter.api.Test;

class DateHelperTest {

  private final YearMonth currentYearMonth = YearMonth.now();


  @Test
  void testFuture() {
    YearMonth farFuture = currentYearMonth.plusYears(10);
    assertTrue(DateHelper.monthYearAreInTheFuture(farFuture.getMonthValue(), farFuture.getYear()));
  }

  @Test
  void testCurrentMonthAndYear() {
    assertTrue(DateHelper.monthYearAreInTheFuture(currentYearMonth.getMonthValue(),
        currentYearMonth.getYear()));
  }

  @Test
  void testPastDate() {
    YearMonth pastMonth = currentYearMonth.minusYears(1).minusMonths(1);
    assertFalse(DateHelper.monthYearAreInTheFuture(pastMonth.getMonthValue(), pastMonth.getYear()));
  }

  @Test
  void testInvalidMonthTooHigh() {
    assertThrows(IllegalArgumentException.class, () ->
        DateHelper.monthYearAreInTheFuture(13, 2025));
  }

  @Test
  void testInvalidMonthTooLow() {
    assertThrows(IllegalArgumentException.class, () -> {
      DateHelper.monthYearAreInTheFuture(0, 2025);
    });
  }

  @Test
  void testInvalidYearZero() {
    assertThrows(IllegalArgumentException.class, () -> {
      DateHelper.monthYearAreInTheFuture(12, 0);
    });
  }

  @Test
  void testInvalidYearNegative() {
    assertThrows(IllegalArgumentException.class, () -> {
      DateHelper.monthYearAreInTheFuture(1, -500);
    });
  }

}