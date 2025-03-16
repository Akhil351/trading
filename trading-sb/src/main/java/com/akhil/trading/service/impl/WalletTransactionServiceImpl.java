package com.akhil.trading.service.impl;




import com.akhil.trading.domain.WalletTransactionType;
import com.akhil.trading.model.Wallet;
import com.akhil.trading.model.WalletTransaction;
import com.akhil.trading.repo.WalletTransactionRepo;
import com.akhil.trading.service.WalletTransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;


import java.time.LocalDateTime;
import java.util.List;

@Service
public class WalletTransactionServiceImpl implements WalletTransactionService {

    @Autowired
     private WalletTransactionRepo walletTransactionRepo;


    @Override
    public void createTransaction(Wallet wallet,
                                  WalletTransactionType type,
                                  String transferId,
                                  String purpose,
                                  BigDecimal amount
    ) {
        WalletTransaction transaction = new WalletTransaction();
        transaction.setWalletId(wallet.getId());
        transaction.setDate(LocalDateTime.now());
        transaction.setType(type);
        transaction.setTransferId(transferId);
        transaction.setPurpose(purpose);
        transaction.setAmount(amount);

        walletTransactionRepo.save(transaction);
    }

    @Override
    public List<WalletTransaction> getTransactions(Wallet wallet, WalletTransactionType type) {
        return walletTransactionRepo.findByWalletIdOrderByDateDesc(wallet.getId());
    }
}
