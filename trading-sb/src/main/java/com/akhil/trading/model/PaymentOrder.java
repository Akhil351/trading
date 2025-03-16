package com.akhil.trading.model;

import com.akhil.trading.domain.PaymentMethod;
import com.akhil.trading.domain.PaymentOrderStatus;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;


import java.math.BigDecimal;

@Entity
@Data
public class PaymentOrder {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private BigDecimal amount;

    private PaymentOrderStatus paymentOrderStatus;

    private PaymentMethod paymentMethod;

    private Long userId;
}
