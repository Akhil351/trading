package com.akhil.trading.repo;

import com.akhil.trading.model.Wallet;
import com.akhil.trading.model.WalletTransaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WalletTransactionRepo extends JpaRepository<WalletTransaction,Long> {
    List<WalletTransaction> findByWalletIdOrderByDateDesc(Long  walletId);
}
