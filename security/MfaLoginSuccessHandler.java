package com.zaalima.iam_server.security;

import com.zaalima.iam_server.entity.User;
import com.zaalima.iam_server.repository.UserRepository;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class MfaLoginSuccessHandler
        implements AuthenticationSuccessHandler {

    private final UserRepository userRepository;

    private final SavedRequestAwareAuthenticationSuccessHandler
            savedRequestHandler =
            new SavedRequestAwareAuthenticationSuccessHandler();

    public MfaLoginSuccessHandler(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public void onAuthenticationSuccess(
            HttpServletRequest request,
            HttpServletResponse response,
            Authentication authentication)
            throws IOException, ServletException {

        User user = userRepository
                .findByUsername(authentication.getName())
                .orElse(null);

        if (user != null && user.isMfaEnabled()) {

            request.getSession().setAttribute(
                    "MFA_PENDING",
                    true
            );

            request.getSession().setAttribute(
                    "MFA_USERNAME",
                    user.getUsername()
            );

            response.sendRedirect("/mfa/verify");

            return;
        }

        // VERY IMPORTANT:
        // Let Spring Security restore the original OAuth request
        savedRequestHandler.onAuthenticationSuccess(
                request,
                response,
                authentication
        );
    }
}