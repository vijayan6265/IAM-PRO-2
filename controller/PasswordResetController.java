package com.zaalima.iam_server.controller;

import com.zaalima.iam_server.dto.PasswordResetRequest;
import com.zaalima.iam_server.dto.ResetPasswordRequest;
import com.zaalima.iam_server.service.PasswordResetService;

import jakarta.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class PasswordResetController {

    private final PasswordResetService passwordResetService;

    public PasswordResetController(
            PasswordResetService passwordResetService) {

        this.passwordResetService = passwordResetService;
    }

    @PostMapping("/forgot-password")
    public ResponseEntity<String> forgotPassword(
            @Valid @RequestBody PasswordResetRequest request) {

        String token =
                passwordResetService.createResetToken(
                        request.getEmail()
                );

        return ResponseEntity.ok(
                "Password reset token: " + token
        );
    }

    @PostMapping("/reset-password")
    public ResponseEntity<String> resetPassword(
            @Valid @RequestBody ResetPasswordRequest request) {

        passwordResetService.resetPassword(
                request.getToken(),
                request.getNewPassword()
        );

        return ResponseEntity.ok(
                "Password reset successful"
        );
    }
}