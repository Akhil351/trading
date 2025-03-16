package com.akhil.trading.model;

import com.akhil.trading.domain.WithdrawalStatus;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Withdrawal {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private WithdrawalStatus withdrawalStatus;

    private BigDecimal amount;

    private Long userId;

    @Builder.Default
    private LocalDateTime dateTime=LocalDateTime.now();
}
