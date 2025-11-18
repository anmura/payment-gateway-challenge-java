package com.checkout.payment.gateway.enums;

import java.util.Arrays;
import java.util.List;

public enum AllowedCurrency {
  EUR, USD, CNY;

  public static List<String> getAllowedCurrency() {
    return Arrays.stream(AllowedCurrency.values())
        .map(Enum::name)
        .toList();
  }
}
