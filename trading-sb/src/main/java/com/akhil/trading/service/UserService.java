package com.akhil.trading.service;

import com.akhil.trading.domain.VerificationType;
import com.akhil.trading.model.User;

public interface UserService {
    public User findUserProfileByJwt(String jwt);
    public User findUserByEmail(String email);
    public User findUserById(Long userId);
    public User enableTwoFactorAuthentication(VerificationType verificationType,String sendTo,Long userId);
    public void updatePassword(Long userId, String newPassword);
}
