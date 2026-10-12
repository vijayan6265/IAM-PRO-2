package com.zaalima.iam_server.dto;

public class MfaSetupResponse {

    private String username;
    private String secret;
    private String otpAuthUri;

    public MfaSetupResponse(String username, String secret, String otpAuthUri) {
        this.username = username;
        this.secret = secret;
        this.otpAuthUri = otpAuthUri;
    }

    public String getUsername() {
        return username;
    }

    public String getSecret() {
        return secret;
    }

    public String getOtpAuthUri() {
        return otpAuthUri;
    }
}