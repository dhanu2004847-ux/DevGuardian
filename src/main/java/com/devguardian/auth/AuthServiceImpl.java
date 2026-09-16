package com.devguardian.auth;

import com.devguardian.user.Role;
import com.devguardian.user.User;
import com.devguardian.user.UserRepository;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;

@Service
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider tokenProvider;
    private final AuthenticationManager authenticationManager;
    private final RefreshTokenRepository refreshTokenRepository;

    public AuthServiceImpl(
            UserRepository userRepository,
            PasswordEncoder passwordEncoder,
            JwtTokenProvider tokenProvider,
            AuthenticationManager authenticationManager,
            RefreshTokenRepository refreshTokenRepository) {

        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.tokenProvider = tokenProvider;
        this.authenticationManager = authenticationManager;
        this.refreshTokenRepository = refreshTokenRepository;
    }

    @Override
    @Transactional
    public AuthResponseDTO register(RegisterRequestDTO dto) {

        User user = new User();

        user.setEmail(dto.getEmail());

        // Use email as username
        user.setUsername(dto.getEmail());

        user.setPassword(passwordEncoder.encode(dto.getPassword()));

        user.setRole(Role.DEVELOPER);

        userRepository.save(user);

        return issueTokens(user.getEmail());
    }

    @Override
    public AuthResponseDTO login(LoginRequestDTO dto) {

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        dto.getEmail(),
                        dto.getPassword()
                )
        );

        User user = userRepository
                .findByEmail(dto.getEmail())
                .orElseThrow();

        return issueTokens(user.getEmail());
    }

    @Override
    @Transactional
    public AuthResponseDTO refreshToken(TokenRefreshRequestDTO dto) {

        // Validate signature and expiry first
        String email = tokenProvider.validateTokenAndGetEmail(
                dto.getRefreshToken()
        );

        // Check whether refresh token exists in database
        RefreshToken stored = refreshTokenRepository
                .findByToken(dto.getRefreshToken())
                .orElseThrow(() ->
                        new IllegalArgumentException(
                                "Refresh token not recognized"
                        )
                );

        // Check expiration
        if (stored.getExpiryDate().isBefore(Instant.now())) {

            refreshTokenRepository.delete(stored);

            throw new IllegalArgumentException(
                    "Refresh token expired"
            );
        }

        String newAccessToken =
                tokenProvider.generateAccessToken(email);

        return new AuthResponseDTO(
                newAccessToken,
                dto.getRefreshToken()
        );
    }

    @Override
    @Transactional
    public void logout(String token) {

        // Header may arrive as:
        // Bearer <token>
        // or as the raw refresh token

        String value = token.startsWith("Bearer ")
                ? token.substring(7)
                : token;

        refreshTokenRepository.deleteByToken(value);
    }

    @Override
    public Object getCurrentUserProfile() {

        Object principal = SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getPrincipal();

        String email;

        if (principal instanceof org.springframework.security.core.userdetails.UserDetails ud) {
            email = ud.getUsername();
        } else {
            email = principal.toString();
        }

        return userRepository
                .findByEmail(email)
                .map(u -> new Object() {

                    public final Long id = u.getId();
                    public final String email = u.getEmail();
                    public final Role role = u.getRole();

                })
                .orElseThrow(() ->
                        new IllegalArgumentException(
                                "User not found"
                        )
                );
    }

    private AuthResponseDTO issueTokens(String email) {

        String accessToken =
                tokenProvider.generateAccessToken(email);

        String refreshTokenValue =
                tokenProvider.generateRefreshToken(email);

        RefreshToken refreshToken = new RefreshToken();

        refreshToken.setToken(refreshTokenValue);
        refreshToken.setEmail(email);

        refreshToken.setExpiryDate(
                Instant.now().plusMillis(
                        tokenProvider.getRefreshExpirationMs()
                )
        );

        refreshTokenRepository.save(refreshToken);

        return new AuthResponseDTO(
                accessToken,
                refreshTokenValue
        );
    }
}