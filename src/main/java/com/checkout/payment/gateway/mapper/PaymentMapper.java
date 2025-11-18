package com.checkout.payment.gateway.mapper;

import static com.checkout.payment.gateway.helper.CardHelper.getCardNumberLastFour;

import com.checkout.payment.gateway.model.PostPaymentRequest;
import com.checkout.payment.gateway.model.PostPaymentResponse;
import com.checkout.payment.gateway.model.bank.BankRequest;
import com.checkout.payment.gateway.model.data.PaymentRecord;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

@Mapper(componentModel = "spring")
public interface PaymentMapper {

  @Mapping(
      target = "expiryDate",
      expression = "java(postPaymentRequest.getExpiryDate())"
  )
  BankRequest toBankRequest(PostPaymentRequest postPaymentRequest);

  @Mapping(
      target = "cardNumberLastFour",
      source = "cardNumber",
      qualifiedByName = "lastFour"
  )
  PostPaymentResponse toResponse(PaymentRecord paymentRecord);

  @Mapping(target = "status", ignore = true)
  @Mapping(target = "id", ignore = true)
  PaymentRecord toRecord(PostPaymentRequest request);


  @Named("lastFour")
  default String extractLastFour(String cardNumber) {
    return getCardNumberLastFour(cardNumber);
  }

}