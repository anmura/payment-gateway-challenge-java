package com.checkout.payment.gateway.repository;

import com.checkout.payment.gateway.model.data.PaymentRecord;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.stereotype.Repository;

@Repository
public class PaymentsRepository {

  private final HashMap<UUID, PaymentRecord> payments = new HashMap<>();

  public UUID add(PaymentRecord payment) {
    UUID id = UUID.randomUUID();
    payment.setId(id);
    payments.put(id, payment);
    return id;
  }

  public Optional<PaymentRecord> get(UUID id) {
    return Optional.ofNullable(payments.get(id));
  }

  public List<PaymentRecord> getAll() {
    return new ArrayList<>(payments.values());
  }

}
