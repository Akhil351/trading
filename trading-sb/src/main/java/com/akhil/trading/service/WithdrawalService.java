package com.akhil.trading.service;

import com.akhil.trading.model.Withdrawal;

import java.math.BigDecimal;
import java.util.List;

public interface WithdrawalService {
    Withdrawal requestWithdrawal(BigDecimal amount, Long userId);

    Withdrawal procedWithwithdrawal(Long withdrawalId,boolean accept);

    List<Withdrawal> getUsersWithdrawalHistory(Long userId);

    List<Withdrawal> getAllWithdrawalRequest();
}
