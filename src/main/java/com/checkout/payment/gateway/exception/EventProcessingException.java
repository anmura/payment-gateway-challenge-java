package com.checkout.payment.gateway.exception;

public class EventProcessingException extends RuntimeException{
  public EventProcessingException(String message, Exception e) {
    super(message + ": " + e.getMessage());
  }

  public EventProcessingException(String message) {
    super(message);
  }
}
