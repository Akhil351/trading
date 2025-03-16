package com.akhil.trading.service.impl;

import com.akhil.trading.domain.VerificationType;
import com.akhil.trading.model.ForgotPasswordToken;
import com.akhil.trading.model.User;
import com.akhil.trading.repo.ForgotPasswordRepo;
import com.akhil.trading.service.ForgotPasswordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ForgotPasswordServiceImpl implements ForgotPasswordService {
    @Autowired
    private ForgotPasswordRepo forgotPasswordRepo;
    @Override
    public ForgotPasswordToken createToken(User user, String id, String otp, VerificationType verificationType, String sendTo) {
        ForgotPasswordToken forgotPasswordToken=new ForgotPasswordToken();
        forgotPasswordToken.setUserId(user.getId());
        forgotPasswordToken.setSendTo(sendTo);
        forgotPasswordToken.setVerificationType(verificationType);
        forgotPasswordToken.setOtp(otp);
        forgotPasswordToken.setId(id);
        return forgotPasswordRepo.save(forgotPasswordToken);
    }

    @Override
    public ForgotPasswordToken findById(String id) {
        return forgotPasswordRepo.findById(id).orElse(null);
    }

    @Override
    public ForgotPasswordToken findByUser(Long userId) {
        return forgotPasswordRepo.findByUserId(userId).orElse(null);
    }

    @Override
    public void deleteToken(ForgotPasswordToken token) {
          forgotPasswordRepo.delete(token);
    }
}
