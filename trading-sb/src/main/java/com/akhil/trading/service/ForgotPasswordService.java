package com.akhil.trading.service;

import com.akhil.trading.domain.VerificationType;
import com.akhil.trading.model.ForgotPasswordToken;
import com.akhil.trading.model.User;

public interface ForgotPasswordService {
    ForgotPasswordToken createToken(User user, String id, String otp
            , VerificationType verificationType, String sendTo);

    ForgotPasswordToken findById(String id);


    ForgotPasswordToken findByUser(Long userId);

    void deleteToken(ForgotPasswordToken token);
}
