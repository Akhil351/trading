package com.akhil.trading.model;

import com.akhil.trading.domain.WalletTransactionType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Data
public class WalletTransaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long walletId;
    private WalletTransactionType type;
    private LocalDateTime date;
    private String transferId;
    private String purpose;
    private BigDecimal amount;
}
