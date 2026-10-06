package com.zaalima.iam_server.security;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.savedrequest.SavedRequest;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class MfaAuthenticationEntryPoint
        implements AuthenticationEntryPoint {

    private final HttpSessionRequestCache requestCache =
            new HttpSessionRequestCache();

    @Override
    public void commence(
            HttpServletRequest request,
            HttpServletResponse response,
            AuthenticationException authException)
            throws IOException, ServletException {

        SavedRequest savedRequest =
                requestCache.getRequest(request, response);

        if (savedRequest != null) {

            String originalUrl =
                    savedRequest.getRedirectUrl();

            System.out.println(
                    "MFA ENTRY POINT ORIGINAL URL = "
                    + originalUrl
            );

            request.getSession().setAttribute(
                    "MFA_ORIGINAL_URL",
                    originalUrl
            );
        }

        response.sendRedirect("/login");
    }
}