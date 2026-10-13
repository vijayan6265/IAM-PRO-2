package com.zaalima.iam_server.controller;

import com.zaalima.iam_server.dto.MfaSetupResponse;
import com.zaalima.iam_server.service.MfaService;

import com.zaalima.iam_server.dto.MfaVerifyRequest;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;

import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth/mfa")
public class MfaController {

    private final MfaService mfaService;

    public MfaController(MfaService mfaService) {
        this.mfaService = mfaService;
    }

    @PostMapping("/setup")
    public MfaSetupResponse setupMfa(Authentication authentication) {

        return mfaService.setupMfa(
                authentication.getName()
        );
    }
    @PostMapping("/verify")
    public ResponseEntity<String> verifyMfa(
        Authentication authentication,
        @Valid @RequestBody MfaVerifyRequest request) {

    boolean valid = mfaService.verifyMfa(
            authentication.getName(),
            request.getCode()
    );

    if (valid) {
        return ResponseEntity.ok("MFA verification successful");
    }

    return ResponseEntity.badRequest()
            .body("Invalid MFA code");
    }
}
