package com.zaalima.iam_server.controller;

import com.zaalima.iam_server.service.TokenRevocationService;

import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class TokenRevocationController {

    private final TokenRevocationService tokenRevocationService;

    public TokenRevocationController(
            TokenRevocationService tokenRevocationService) {

        this.tokenRevocationService =
                tokenRevocationService;
    }

    @PostMapping("/revoke")
    public ResponseEntity<String> revokeToken(
            @AuthenticationPrincipal Jwt jwt) {

        if (jwt == null) {
            return ResponseEntity
                    .badRequest()
                    .body("Invalid token");
        }

        tokenRevocationService.revokeToken(
                jwt.getId(),
                jwt.getExpiresAt()
        );

        return ResponseEntity.ok(
                "Token revoked successfully"
        );
    }
}
