package com.checkout.payment.gateway.helper;

public class CardHelper {

  public static String getCardNumberLastFour(String cardNumber) {
    if (cardNumber == null || cardNumber.length() <= 4) {
      return cardNumber;
    }
    return cardNumber.substring(cardNumber.length() - 4);
  }

}
