package com.checkout.payment.gateway.model;

import static com.checkout.payment.gateway.helper.CardHelper.getCardNumberLastFour;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import java.io.Serializable;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class PostPaymentRequest implements Serializable {

  @NotBlank(message = "Card number is required")
  @Pattern(
      regexp = "\\d{14,19}",
      message = "Card number must contain only numeric characters and be between 14 and 19 characters long"
  )
  @JsonProperty("card_number")
  private String cardNumber;

  @NotNull(message = "Expiry month is required")
  @Min(value = 1, message = "Expiry month cannot be less than 1")
  @Max(value = 12, message = "Expiry month cannot be greater than 12")
  @JsonProperty("expiry_month")
  private Integer expiryMonth;

  @NotNull(message = "Expiry year is required")
  @JsonProperty("expiry_year")
  private Integer expiryYear;

  @NotNull(message = "Currency is required")
  @Size(min = 3, max = 3, message = "Currency must be a 3-letter code")
  private String currency;

  @NotNull(message = "Amount is required")
  @Positive(message = "Amount must be a positive integer in minor currency units")
  private Integer amount;

  @NotBlank(message = "CVV is required")
  @Pattern(
      regexp = "\\d{3,4}",
      message = "CVV must be 3 or 4 digits long"
  )
  private String cvv;

  @JsonProperty("expiry_date")
  public String getExpiryDate() {
    return String.format("%d/%d", expiryMonth, expiryYear);
  }

  @Override
  public String toString() {
    return "PostPaymentRequest{" +
        "cardNumberLastFour=" + getCardNumberLastFour(cardNumber) +
        ", expiryMonth=" + expiryMonth +
        ", expiryYear=" + expiryYear +
        ", currency='" + currency + '\'' +
        ", amount=" + amount +
        '}';
  }
}
