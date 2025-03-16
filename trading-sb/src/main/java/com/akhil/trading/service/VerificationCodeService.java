package com.akhil.trading.service;

import com.akhil.trading.domain.VerificationType;
import com.akhil.trading.model.User;
import com.akhil.trading.model.VerificationCode;

public interface VerificationCodeService {
    VerificationCode sendVerificationCode(Long userId, VerificationType verificationType);
    VerificationCode getVerificationCodeById(Long id);
    VerificationCode getVerificationCodeByUser(Long userId);
    void deleteVerificationCodeById(VerificationCode verificationCode);
}
