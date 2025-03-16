package com.akhil.trading.service.impl;

import com.akhil.trading.model.TwoFactorAuth;
import com.akhil.trading.domain.VerificationType;
import com.akhil.trading.exception.ResourceNotFoundException;
import com.akhil.trading.model.User;
import com.akhil.trading.repo.UserRepo;
import com.akhil.trading.service.UserService;
import com.akhil.trading.utils.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepo userRepo;
    @Autowired
    private JwtUtils jwtUtils;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Override
    public User findUserProfileByJwt(String jwt) {
        String email=jwtUtils.getUserNameFromJwtToken(jwt);
        return userRepo.findByEmail(email).orElseThrow(()->new ResourceNotFoundException("user not found"));
    }

    @Override
    public User findUserByEmail(String email) {
        return  userRepo.findByEmail(email).orElseThrow(()->new ResourceNotFoundException("user not found"));
    }

    @Override
    public User findUserById(Long userId) {
        return  userRepo.findById(userId).orElseThrow(()->new ResourceNotFoundException("user not found"));
    }

    @Override
    public User enableTwoFactorAuthentication(VerificationType verificationType,String sendTo,Long userId) {
        User user=findUserById(userId);
        TwoFactorAuth twoFactorAuth=new TwoFactorAuth();
        twoFactorAuth.setEnabled(true);
        twoFactorAuth.setSendTo(verificationType);
        user.setTwoFactorAuth(twoFactorAuth);
        return userRepo.save(user);
    }

    @Override
    public void updatePassword(Long userId, String newPassword) {
        User user=this.findUserById(userId);
        user.setPassword(passwordEncoder.encode(newPassword));
        userRepo.save(user);
    }
}
