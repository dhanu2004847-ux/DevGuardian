package com.devguardian.auth;

public interface AuthService {
    AuthResponseDTO register(RegisterRequestDTO dto);
    AuthResponseDTO login(LoginRequestDTO dto);
    AuthResponseDTO refreshToken(TokenRefreshRequestDTO dto);
    void logout(String token);
    Object getCurrentUserProfile();
}