package com.checkout.payment.gateway.model.bank;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class BankResponse {

  private Boolean authorized;

  @JsonProperty("authorization_code")
  private String authorizationCode;

  public boolean isValid() {
    return authorized && authorizationCode != null && !authorizationCode.isEmpty();
  }

}
