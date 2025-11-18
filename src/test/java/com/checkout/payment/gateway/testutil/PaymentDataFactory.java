package com.checkout.payment.gateway.testutil;

import com.checkout.payment.gateway.enums.PaymentStatus;
import com.checkout.payment.gateway.model.PostPaymentRequest;
import com.checkout.payment.gateway.model.data.PaymentRecord;
import java.util.UUID;

public class PaymentDataFactory {

  public static PostPaymentRequest createPostPaymentRequest() {
    PostPaymentRequest request = new PostPaymentRequest();
    request.setAmount(100);
    request.setCurrency("EUR");
    request.setCardNumber("74625374321");
    request.setExpiryMonth(2);
    request.setExpiryYear(2029);
    request.setCvv("123");
    return request;

  }

  public static PaymentRecord createPaymentRecord() {
    PaymentRecord payment = new PaymentRecord();
    payment.setId(UUID.randomUUID());
    payment.setAmount(10);
    payment.setCurrency("USD");
    payment.setStatus(PaymentStatus.AUTHORIZED);
    payment.setExpiryMonth(12);
    payment.setExpiryYear(2024);
    payment.setCardNumber("74625374321");
    return payment;
  }
}
