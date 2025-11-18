package com.checkout.payment.gateway.service;

import static com.checkout.payment.gateway.testutil.PaymentDataTestHelper.createPostPaymentRequest;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.checkout.payment.gateway.exception.ValidationException;
import com.checkout.payment.gateway.model.PostPaymentRequest;
import jakarta.validation.Validator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class PaymentValidationServiceTest {

  @InjectMocks
  private PaymentValidationService paymentValidationService;

  private PostPaymentRequest validRequest;

  @Mock
  private Validator validator;

  @BeforeEach
  void setUp() {
    validRequest = createPostPaymentRequest();
  }

  @Test
  void testAllValidationsPass() {
    assertDoesNotThrow(() -> paymentValidationService.validatePostPaymentRequest(validRequest));

  }

  @Test
  void testDateValidationFails() {
    validRequest.setExpiryYear(2020);
    assertThrows(ValidationException.class,
        () -> paymentValidationService.validatePostPaymentRequest(validRequest));
  }

  @Test
  void testCurrencyValidationFails() {
    validRequest.setCurrency("XYZ");

    assertThrows(ValidationException.class,
        () -> paymentValidationService.validatePostPaymentRequest(validRequest));
  }
}