package com.zaalima.iam_server.service;

import com.zaalima.iam_server.entity.RevokedToken;
import com.zaalima.iam_server.repository.RevokedTokenRepository;

import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;

@Service
public class TokenRevocationService {

    private final RevokedTokenRepository revokedTokenRepository;

    public TokenRevocationService(
            RevokedTokenRepository revokedTokenRepository) {

        this.revokedTokenRepository =
                revokedTokenRepository;
    }

    public void revokeToken(
            String jti,
            Instant expiresAt) {

        if (jti == null || jti.isBlank()) {
            throw new IllegalArgumentException(
                    "Token JTI is missing"
            );
        }

        if (revokedTokenRepository.existsByJti(jti)) {
            return;
        }

        LocalDateTime expiry =
                LocalDateTime.ofInstant(
                        expiresAt,
                        ZoneId.systemDefault()
                );

        RevokedToken revokedToken =
                new RevokedToken(jti, expiry);

        revokedTokenRepository.save(revokedToken);
    }

    public boolean isRevoked(String jti) {

        if (jti == null || jti.isBlank()) {
            return false;
        }

        return revokedTokenRepository
                .existsByJti(jti);
    }
}