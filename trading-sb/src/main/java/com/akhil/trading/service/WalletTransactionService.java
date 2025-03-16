package com.akhil.trading.service;

import com.akhil.trading.domain.WalletTransactionType;
import com.akhil.trading.model.Wallet;
import com.akhil.trading.model.WalletTransaction;

import java.math.BigDecimal;
import java.util.List;

public interface WalletTransactionService {
    void createTransaction(Wallet wallet,
                           WalletTransactionType type,
                           String transferId,
                           String purpose,
                           BigDecimal amount
    );

    List<WalletTransaction> getTransactions(Wallet wallet, WalletTransactionType type);
}
