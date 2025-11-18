package com.checkout.payment.gateway.service;

import com.checkout.payment.gateway.model.bank.BankRequest;
import com.checkout.payment.gateway.model.bank.BankResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Slf4j
@Service
public class BankClientService {

  private final RestTemplate restTemplate;
  private final String baseUrl;

  public BankClientService(RestTemplate restTemplate,
      @Value("${bank.api.base-url}") String baseUrl) {
    this.restTemplate = restTemplate;
    this.baseUrl = baseUrl;
  }

  public BankResponse sendPayment(BankRequest request) {
    log.info("Sending payment request");
    return restTemplate.postForObject(baseUrl, request, BankResponse.class);
  }

}
