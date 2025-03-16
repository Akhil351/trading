package com.akhil.trading.service.impl;

import com.akhil.trading.exception.ResourceNotFoundException;
import com.akhil.trading.model.TwoFactorOTP;
import com.akhil.trading.repo.TwoFactorOtpRepo;
import com.akhil.trading.service.TwoFactorOtpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class TwoFactorOtpServiceImpl implements TwoFactorOtpService {
    @Autowired
    private TwoFactorOtpRepo twoFactorOtpRepo;
    @Override
    public TwoFactorOTP createTwoFactorOtp(Long userId, String otp, String jwt) {
        TwoFactorOTP twoFactorOTP=new TwoFactorOTP();
        twoFactorOTP.setId(UUID.randomUUID().toString());
        twoFactorOTP.setJwt(jwt);
        twoFactorOTP.setOtp(otp);
        twoFactorOTP.setUserId(userId);
        return twoFactorOtpRepo.save(twoFactorOTP);
    }

    @Override
    public TwoFactorOTP findByUser(Long userId) {
        return twoFactorOtpRepo.findByUserId(userId).orElse(null);
    }

    @Override
    public TwoFactorOTP findById(String id) {
        return twoFactorOtpRepo.findById(id).orElseThrow(()->new ResourceNotFoundException("TwoFactorOTP not found"));
    }

    @Override
    public boolean verifyTwoFactorOtp(TwoFactorOTP twoFactorOTP, String otp) {
        return twoFactorOTP.getOtp().equals(otp);
    }

    @Override
    public void deleteTwoFactorOtp(TwoFactorOTP twoFactorOTP) {
       twoFactorOtpRepo.delete(twoFactorOTP) ;
    }
}
