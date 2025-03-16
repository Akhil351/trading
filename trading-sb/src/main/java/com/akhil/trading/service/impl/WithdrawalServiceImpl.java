package com.akhil.trading.service.impl;

import com.akhil.trading.domain.WithdrawalStatus;
import com.akhil.trading.exception.ResourceNotFoundException;
import com.akhil.trading.model.Withdrawal;
import com.akhil.trading.repo.WithdrawalRepo;
import com.akhil.trading.service.WithdrawalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class WithdrawalServiceImpl implements WithdrawalService {
    @Autowired
    private WithdrawalRepo withdrawalRepo;
    @Override
    public Withdrawal requestWithdrawal(BigDecimal amount, Long userId) {
        Withdrawal withdrawal=new Withdrawal();
        withdrawal.setAmount(amount);
        withdrawal.setUserId(userId);
        withdrawal.setWithdrawalStatus(WithdrawalStatus.PENDING);
        return withdrawalRepo.save(withdrawal);
    }

    @Override
    public Withdrawal procedWithwithdrawal(Long withdrawalId, boolean accept) {
        Withdrawal withdrawal=withdrawalRepo.findById(withdrawalId).orElseThrow(()->new ResourceNotFoundException("withdrawal not found"));
        withdrawal.setDateTime(LocalDateTime.now());
        if(accept){ withdrawal.setWithdrawalStatus(WithdrawalStatus.SUCCESS);}
        else withdrawal.setWithdrawalStatus(WithdrawalStatus.DECLINE);
        return withdrawalRepo.save(withdrawal);
    }

    @Override
    public List<Withdrawal> getUsersWithdrawalHistory(Long userId) {
        return withdrawalRepo.findByUserId(userId);
    }

    @Override
    public List<Withdrawal> getAllWithdrawalRequest() {
        return withdrawalRepo.findAll();
    }
}
