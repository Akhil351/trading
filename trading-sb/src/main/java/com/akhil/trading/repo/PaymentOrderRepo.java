package com.akhil.trading.repo;

import com.akhil.trading.model.PaymentOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PaymentOrderRepo extends JpaRepository<PaymentOrder,Long> {
}
