package com.zaalima.iam_server.dto;

public class UserProfileResponse {

    private Long id;
    private String username;
    private String email;
    private boolean enabled;
    private boolean mfaEnabled;

    public UserProfileResponse(
            Long id,
            String username,
            String email,
            boolean enabled,
            boolean mfaEnabled) {

        this.id = id;
        this.username = username;
        this.email = email;
        this.enabled = enabled;
        this.mfaEnabled = mfaEnabled;
    }

    public Long getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public boolean isMfaEnabled() {
        return mfaEnabled;
    }
}