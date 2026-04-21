package com.kynsoft.auth.service;

import com.kynsoft.auth.dto.ForgotPasswordRequest;
import com.kynsoft.auth.dto.LoginRequest;
import com.kynsoft.auth.dto.LoginResponse;
import com.kynsoft.auth.dto.ResetPasswordRequest;

public interface AuthService {
    LoginResponse login(LoginRequest request);
    void forgotPassword(ForgotPasswordRequest request);
    void resetPassword(ResetPasswordRequest request);
    void logout(String userId);
}
