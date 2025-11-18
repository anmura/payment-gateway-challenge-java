package com.checkout.payment.gateway.controller;


import com.checkout.payment.gateway.mapper.PaymentMapper;
import com.checkout.payment.gateway.model.data.PaymentRecord;
import com.checkout.payment.gateway.repository.PaymentsRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.UUID;

import static com.checkout.payment.gateway.testutil.PaymentDataFactory.createPaymentRecord;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class PaymentGatewayControllerTest {

	@Autowired
	private MockMvc mvc;
	@Autowired
	PaymentsRepository paymentsRepository;
	@Autowired
	PaymentMapper paymentMapper;

	@Test
	void getPaymentById_whenPaymentExists_returns200WithPaymentDetails() throws Exception {
		PaymentRecord payment = createPaymentRecord();

		paymentsRepository.add(payment);

		mvc.perform(MockMvcRequestBuilders.get("/payment/" + payment.getId()))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.status").value(payment.getStatus().getName()))
				.andExpect(jsonPath("$.cardNumberLastFour").value(payment.getCardNumberLastFour()))
				.andExpect(jsonPath("$.expiryMonth").value(payment.getExpiryMonth()))
				.andExpect(jsonPath("$.expiryYear").value(payment.getExpiryYear()))
				.andExpect(jsonPath("$.currency").value(payment.getCurrency()))
				.andExpect(jsonPath("$.amount").value(payment.getAmount()));
	}

	@Test
	void getPaymentById_whenPaymentDoesNotExist_returns500WithErrorMessage() throws Exception {
		UUID id = UUID.randomUUID();
		mvc.perform(MockMvcRequestBuilders.get("/payment/" + id))
				.andExpect(status().isInternalServerError())
				.andExpect(jsonPath("$.message").value("Payment not found: " + id));
	}
}
