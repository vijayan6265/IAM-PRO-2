package com.zaalima.iam_server.security;

import com.zaalima.iam_server.service.TokenRevocationService;

import org.springframework.security.oauth2.core.OAuth2Error;
import org.springframework.security.oauth2.core.OAuth2TokenValidator;
import org.springframework.security.oauth2.core.OAuth2TokenValidatorResult;
import org.springframework.security.oauth2.jwt.Jwt;

import org.springframework.stereotype.Component;

@Component
public class RevokedTokenValidator
        implements OAuth2TokenValidator<Jwt> {

    private final TokenRevocationService
            tokenRevocationService;

    public RevokedTokenValidator(
            TokenRevocationService tokenRevocationService) {

        this.tokenRevocationService =
                tokenRevocationService;
    }

    @Override
    public OAuth2TokenValidatorResult validate(
            Jwt jwt) {

        String jti = jwt.getId();

        if (tokenRevocationService.isRevoked(jti)) {

            OAuth2Error error =
                    new OAuth2Error(
                            "invalid_token",
                            "Token has been revoked",
                            null
                    );

            return OAuth2TokenValidatorResult.failure(
                    error
            );
        }

        return OAuth2TokenValidatorResult.success();
    }
}
