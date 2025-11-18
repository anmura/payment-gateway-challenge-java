package com.checkout.payment.gateway.model;

import com.checkout.payment.gateway.enums.AllowedCurrency;
import com.checkout.payment.gateway.enums.PaymentStatus;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PostPaymentResponse {

  private UUID id;
  private PaymentStatus status;
  private String cardNumberLastFour;
  private Integer expiryMonth;
  private Integer expiryYear;
  private AllowedCurrency currency;
  private Integer amount;


  @Override
  public String toString() {
    return "GetPaymentResponse{" +
        "id=" + id +
        ", status=" + status +
        ", cardNumberLastFour=" + cardNumberLastFour +
        ", expiryMonth=" + expiryMonth +
        ", expiryYear=" + expiryYear +
        ", currency='" + currency + '\'' +
        ", amount=" + amount +
        '}';
  }
}
