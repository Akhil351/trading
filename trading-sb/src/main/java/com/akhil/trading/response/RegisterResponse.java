package com.akhil.trading.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RegisterResponse {
    private String jwt;
    @Builder.Default
    private String message="register SuccessFully";
    private String session;
    @Builder.Default
    private Boolean twoFactorAuthEnabled=false;
}
