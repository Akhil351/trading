package com.akhil.trading.model;

import com.akhil.trading.domain.VerificationType;
import jakarta.persistence.Entity;

import jakarta.persistence.Id;
import lombok.Data;

@Entity
@Data
public class ForgotPasswordToken {
    @Id
    private String id;

    private Long userId;

    private String otp;

    private VerificationType verificationType;

    private String sendTo;

}
