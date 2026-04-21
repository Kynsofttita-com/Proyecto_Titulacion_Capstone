package com.kynsoft.msauth.service;

import com.kynsoft.msauth.dto.request.ForgotPasswordRequest;
import com.kynsoft.msauth.dto.request.LoginRequest;
import com.kynsoft.msauth.dto.request.ResetPasswordRequest;
import com.kynsoft.msauth.dto.response.LoginResponse;

public interface AuthService {
    LoginResponse login(LoginRequest request);
    void forgotPassword(ForgotPasswordRequest request);
    void resetPassword(ResetPasswordRequest request);
    void logout(String userId);
}
