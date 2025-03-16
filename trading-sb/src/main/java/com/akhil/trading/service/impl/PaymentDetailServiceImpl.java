package com.akhil.trading.service.impl;

import com.akhil.trading.exception.ResourceNotFoundException;
import com.akhil.trading.model.PaymentDetails;
import com.akhil.trading.repo.PaymentDetailsRepo;
import com.akhil.trading.service.PaymentDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PaymentDetailServiceImpl implements PaymentDetailService {
    @Autowired
    private PaymentDetailsRepo paymentRepo;
    @Override
    public PaymentDetails addPaymentDetails(String accountNumber, String accountHolderName, String ifsc, String bankName, Long userId) {
        PaymentDetails paymentDetails=new PaymentDetails();
        paymentDetails.setAccountHolderName(accountHolderName);
        paymentDetails.setAccountNumber(accountNumber);
        paymentDetails.setBankName(bankName);
        paymentDetails.setIfsc(ifsc);
        paymentDetails.setUserId(userId);
        return paymentRepo.save(paymentDetails);
    }

    @Override
    public PaymentDetails getUserPaymentDetails(Long userId) {
        return paymentRepo.findByUserId(userId).orElseThrow(()->new ResourceNotFoundException("payment details not found"));
    }
}
