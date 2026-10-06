package com.zaalima.iam_server.service;

import com.zaalima.iam_server.dto.MfaSetupResponse;
import com.zaalima.iam_server.entity.User;
import com.zaalima.iam_server.repository.UserRepository;

import com.warrenstrange.googleauth.GoogleAuthenticator;
import com.warrenstrange.googleauth.GoogleAuthenticatorKey;

import org.springframework.stereotype.Service;

@Service
public class MfaService {

    private final UserRepository userRepository;

    private final GoogleAuthenticator googleAuthenticator =
            new GoogleAuthenticator();

    public MfaService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public MfaSetupResponse setupMfa(String username) {

    User user = userRepository.findByUsername(username)
            .orElseThrow(() -> new RuntimeException("User not found"));

    GoogleAuthenticatorKey key =
            googleAuthenticator.createCredentials();

    String secret = key.getKey();

    user.setMfaSecret(secret);
    user.setMfaEnabled(false);

    userRepository.save(user);

    String otpAuthUri =
            "otpauth://totp/Zaalima-IAM:" + user.getUsername()
            + "?secret=" + secret
            + "&issuer=Zaalima-IAM";

    return new MfaSetupResponse(
            user.getUsername(),
            secret,
            otpAuthUri
    );
    }
    public boolean verifyMfa(String username, String code) {

    User user = userRepository.findByUsername(username)
            .orElseThrow(() -> new RuntimeException("User not found"));

    if (user.getMfaSecret() == null) {
        throw new RuntimeException("MFA is not set up");
    }

    int verificationCode = Integer.parseInt(code);

    boolean valid = googleAuthenticator.authorize(
            user.getMfaSecret(),
            verificationCode
    );

    if (valid) {
        user.setMfaEnabled(true);
        userRepository.save(user);
    }

    return valid;
    }
}