package com.akhil.trading.service.impl;

import com.akhil.trading.domain.VerificationType;
import com.akhil.trading.exception.ResourceNotFoundException;
import com.akhil.trading.model.User;
import com.akhil.trading.model.VerificationCode;
import com.akhil.trading.repo.VerificationCodeRepo;
import com.akhil.trading.service.VerificationCodeService;
import com.akhil.trading.utils.OtpUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class VerificationCodeServiceImpl implements VerificationCodeService {
    @Autowired
    private VerificationCodeRepo verificationCodeRepo;
    @Override
    public VerificationCode sendVerificationCode(Long userId,VerificationType verificationType) {
        VerificationCode verificationCode=new VerificationCode();
        verificationCode.setOtp(OtpUtils.generateOtp());
        verificationCode.setVerificationType(verificationType);
        verificationCode.setUserId(userId);
        return verificationCodeRepo.save(verificationCode);
    }

    @Override
    public VerificationCode getVerificationCodeById(Long id) {
        return verificationCodeRepo.findById(id).orElseThrow(()->new ResourceNotFoundException("verification code not found"));
    }

    @Override
    public VerificationCode getVerificationCodeByUser(Long userId) {
        return verificationCodeRepo.findByUserId(userId).orElse(null);
    }

    @Override
    public void deleteVerificationCodeById(VerificationCode verificationCode) {
     verificationCodeRepo.delete(verificationCode);
    }
}
