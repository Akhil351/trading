package com.akhil.trading.service;

import com.akhil.trading.request.LoginRequest;
import com.akhil.trading.request.RegisterRequest;

public interface AuthService {
    String register(RegisterRequest request);
    String login(LoginRequest request);
}
