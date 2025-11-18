package com.checkout.payment.gateway.model.data;


import com.checkout.payment.gateway.enums.PaymentStatus;
import lombok.Getter;
import lombok.Setter;
import java.util.UUID;

@Getter
@Setter
public class PaymentRecord {
  private UUID id;
  private PaymentStatus status;
  private String cardNumber;
  private Integer expiryMonth;
  private Integer expiryYear;
  private String currency;
  private Integer amount;

  public String getCardNumberLastFour() {
    return cardNumber.substring(cardNumber.length() - 4);
  }
}
