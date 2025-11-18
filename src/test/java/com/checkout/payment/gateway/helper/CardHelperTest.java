package com.checkout.payment.gateway.helper;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.Test;

class CardHelperTest {

  @Test
  void testEmptyCardNumber() {
    assertEquals("", CardHelper.getCardNumberLastFour(""),
        "Should return an empty string if the input is an empty string.");
  }

  @Test
  void testNullCardNumber() {
    assertNull(CardHelper.getCardNumberLastFour(null),
        "Should return null if the input card number is null.");
  }

  @Test
  void testStandardCardNumber() {
    String cardNumber = "1234567890123456";
    String expectedLastFour = "3456";
    assertEquals(expectedLastFour, CardHelper.getCardNumberLastFour(cardNumber),
        "Should return the last four digits of a standard 16-digit card number.");
  }

  @Test
  void testFourDigitCardNumber() {
    String cardNumber = "9999";
    String expectedLastFour = "9999";
    assertEquals(expectedLastFour, CardHelper.getCardNumberLastFour(cardNumber),
        "Should return the full string if it is exactly four digits long.");
  }

  @Test
  void testShortCardNumber() {
    String cardNumber = "123";
    String expectedLastFour = "123";
    assertEquals(expectedLastFour, CardHelper.getCardNumberLastFour(cardNumber),
        "Should return the full string if it is less than four digits long.");
  }
}