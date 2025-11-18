package com.checkout.payment.gateway.service;

import static com.checkout.payment.gateway.enums.AllowedCurrency.getAllowedCurrency;
import static com.checkout.payment.gateway.helper.DateHelper.monthYearAreInTheFuture;
import static java.util.stream.Collectors.joining;

import com.checkout.payment.gateway.exception.ValidationException;
import com.checkout.payment.gateway.model.PostPaymentRequest;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class PaymentValidationService {

  private final Validator validator;

  public void validatePostPaymentRequest(PostPaymentRequest paymentRequest) {

    Set<ConstraintViolation<PostPaymentRequest>> violations = validator.validate(paymentRequest);

    if (!violations.isEmpty()) {
      String message = violations.stream()
          .map(ConstraintViolation::getMessage)
          .collect(joining(", "));
      throw new ValidationException(message);
    }

    if (!monthYearAreInTheFuture(paymentRequest.getExpiryMonth(), paymentRequest.getExpiryYear())) {
      throw new ValidationException("Expiry month and year must be in the future");
    }

    if (!getAllowedCurrency().contains(paymentRequest.getCurrency())) {
      throw new ValidationException("Unsupported currency: " + paymentRequest.getCurrency());
    }
  }
}
