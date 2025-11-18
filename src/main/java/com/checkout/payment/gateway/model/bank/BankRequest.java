package com.checkout.payment.gateway.model.bank;

import com.checkout.payment.gateway.enums.AllowedCurrency;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@NoArgsConstructor
public class BankRequest {

  @JsonProperty("card_number")
  private String cardNumber;

  @JsonProperty("expiry_date")
  private String expiryDate;

  private AllowedCurrency currency;

  private Integer amount;

  private String cvv;

}
