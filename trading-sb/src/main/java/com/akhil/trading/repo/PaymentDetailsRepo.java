package com.akhil.trading.repo;

import com.akhil.trading.model.PaymentDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PaymentDetailsRepo extends JpaRepository<PaymentDetails,Long> {
    Optional<PaymentDetails> findByUserId(Long userId);
}
