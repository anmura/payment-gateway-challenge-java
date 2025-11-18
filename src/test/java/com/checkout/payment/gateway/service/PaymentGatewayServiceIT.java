package com.checkout.payment.gateway.service;


import com.checkout.payment.gateway.enums.PaymentStatus;
import com.checkout.payment.gateway.exception.EventProcessingException;
import com.checkout.payment.gateway.model.PostPaymentRequest;
import com.checkout.payment.gateway.model.PostPaymentResponse;
import com.checkout.payment.gateway.model.bank.BankRequest;
import com.checkout.payment.gateway.model.bank.BankResponse;
import com.checkout.payment.gateway.model.data.PaymentRecord;
import com.checkout.payment.gateway.repository.PaymentsRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.List;

import static com.checkout.payment.gateway.testutil.PaymentDataTestHelper.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
public class PaymentGatewayServiceIT {
	@Autowired
	private PaymentGatewayService paymentGatewayService;

	@Autowired
	private PaymentsRepository paymentsRepository;

	@MockBean
	private BankClientService bankClientService;

	@MockBean
	private PaymentValidationService paymentValidationService;

	@Test
	void processPayment_whenBankAuthorizes_returnsAuthorizedResponse() throws Exception {
		List<PaymentRecord> recordsBefore = paymentsRepository.getAll();
		when(bankClientService.sendPayment(any(BankRequest.class))).thenReturn(createBankResponse());

		PostPaymentResponse response = paymentGatewayService.processPayment(createPostPaymentRequest());

		assertNotNull(response.getId());
		assertEquals(PaymentStatus.AUTHORIZED, response.getStatus());

		List<PaymentRecord> recordsAfter = paymentsRepository.getAll();
		PaymentRecord savedRecord = getNewRecord(recordsBefore, recordsAfter);
		assertEquals(PaymentStatus.AUTHORIZED, savedRecord.getStatus());
		assertEquals(response.getId(), savedRecord.getId());
	}

	@Test
	void processPayment_whenBankDeclines_throwsExceptionAndSetsDeclinedStatus() {
		List<PaymentRecord> recordsBefore = paymentsRepository.getAll();
		BankResponse declinedResponse = createBankResponse();
		declinedResponse.setAuthorized(false);
		when(bankClientService.sendPayment(any(BankRequest.class))).thenReturn(declinedResponse);

		assertThrows(EventProcessingException.class, () -> {
			paymentGatewayService.processPayment(createPostPaymentRequest());
		});

		List<PaymentRecord> recordsAfter = paymentsRepository.getAll();
		PaymentRecord savedRecord = getNewRecord(recordsBefore, recordsAfter);
		assertEquals(PaymentStatus.DECLINED, savedRecord.getStatus());

	}

	@Test
	void processPayment_whenValidationFails_throwsValidationExceptionAndSetsRejectedStatus() {
		List<PaymentRecord> recordsBefore = paymentsRepository.getAll();

		doThrow(new IllegalArgumentException("Card number format invalid."))
				.when(paymentValidationService).validatePostPaymentRequest(any(PostPaymentRequest.class));

		assertThrows(IllegalArgumentException.class, () -> {
			paymentGatewayService.processPayment(createPostPaymentRequest());
		});

		verify(bankClientService, times(0)).sendPayment(any(BankRequest.class));

		List<PaymentRecord> recordsAfter = paymentsRepository.getAll();
		PaymentRecord savedRecord = getNewRecord(recordsBefore, recordsAfter);
		assertEquals(PaymentStatus.REJECTED, savedRecord.getStatus());
	}
}
