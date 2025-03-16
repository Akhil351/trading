package com.akhil.trading.service;

import com.akhil.trading.model.PaymentDetails;

public interface PaymentDetailService {
    public PaymentDetails addPaymentDetails(String accountNumber,String accountHolderName,
                                            String ifsc,String bankName,Long userId);

    public PaymentDetails getUserPaymentDetails(Long userId);
}
