package com.devguardian.auth;
@lombok.Data
@lombok.AllArgsConstructor
public class AuthResponseDTO { private String accessToken; private String refreshToken; }