package com.checkout.payment.gateway.service;

import com.checkout.payment.gateway.enums.PaymentStatus;
import com.checkout.payment.gateway.exception.EventProcessingException;
import com.checkout.payment.gateway.mapper.PaymentMapper;
import com.checkout.payment.gateway.model.PostPaymentRequest;
import com.checkout.payment.gateway.model.PostPaymentResponse;
import com.checkout.payment.gateway.model.bank.BankRequest;
import com.checkout.payment.gateway.model.bank.BankResponse;
import com.checkout.payment.gateway.model.data.PaymentRecord;
import com.checkout.payment.gateway.repository.PaymentsRepository;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class PaymentGatewayService {

  private final BankClientService bankClientService;
  private final PaymentValidationService paymentValidationService;
  private final PaymentsRepository paymentsRepository;

  private final PaymentMapper paymentMapper;

  public PostPaymentResponse getPaymentById(UUID id) {
    log.info("Requesting access to to payment with ID {}", id);
    return paymentsRepository.get(id).map(paymentMapper::toResponse)
        .orElseThrow(() -> {
          log.warn("Payment with ID {} not found", id);
          return new EventProcessingException("Payment not found: " + id);
        });
  }

  public PostPaymentResponse processPayment(PostPaymentRequest paymentRequest) throws Exception {
    log.info("Starting processing of payment request...");
    PaymentRecord record = paymentMapper.toRecord(paymentRequest);
    Exception exception = new EventProcessingException("Payment processing failed");

    try {
      paymentValidationService.validatePostPaymentRequest(paymentRequest);
      BankRequest bankRequest = paymentMapper.toBankRequest(paymentRequest);
      BankResponse bankResponse = bankClientService.sendPayment(bankRequest);

      if (bankResponse != null && bankResponse.isValid()) {
        processAuthorizedPayment(record);
        return paymentMapper.toResponse(record);
      }

      record.setStatus(PaymentStatus.DECLINED);

    } catch (Exception e) {
      exception = e;
      record.setStatus(PaymentStatus.REJECTED);
    }

    UUID id = this.paymentsRepository.add(record);
    log.error("{} | ID: {}", exception.getMessage(), id);

    throw exception;
  }

  private void processAuthorizedPayment(PaymentRecord record) {
    record.setStatus(PaymentStatus.AUTHORIZED);
    this.paymentsRepository.add(record);
    log.info("Successfully processed payment with ID {}", record.getId());
  }
}
