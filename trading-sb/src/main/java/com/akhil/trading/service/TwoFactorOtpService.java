package com.akhil.trading.service;

import com.akhil.trading.model.TwoFactorOTP;

public interface TwoFactorOtpService {
    TwoFactorOTP createTwoFactorOtp(Long userId,String otp,String jwt);

    TwoFactorOTP findByUser(Long userId);

    TwoFactorOTP findById(String id);

    boolean verifyTwoFactorOtp(TwoFactorOTP twoFactorOTP,String otp);

    void deleteTwoFactorOtp(TwoFactorOTP twoFactorOTP);



}
