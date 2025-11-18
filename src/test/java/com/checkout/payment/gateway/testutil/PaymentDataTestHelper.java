package com.checkout.payment.gateway.testutil;

import com.checkout.payment.gateway.enums.PaymentStatus;
import com.checkout.payment.gateway.model.PostPaymentRequest;
import com.checkout.payment.gateway.model.bank.BankResponse;
import com.checkout.payment.gateway.model.data.PaymentRecord;
import java.util.List;
import java.util.UUID;

public class PaymentDataTestHelper {

  public static PostPaymentRequest createPostPaymentRequest() {
    PostPaymentRequest request = new PostPaymentRequest();
    request.setAmount(100);
    request.setCurrency("EUR");
    request.setCardNumber("2222405343248877");
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

  public static BankResponse createBankResponse() {
    return new BankResponse(true, "test");
  }

  public static PaymentRecord getNewRecord(List<PaymentRecord> before, List<PaymentRecord> after) {
    List<PaymentRecord> newRecords = after.stream()
        .filter(record -> before.stream().noneMatch(b -> b.getId().equals(record.getId())))
        .toList();

    return newRecords.get(0);
  }
}
