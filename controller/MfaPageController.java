package com.zaalima.iam_server.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.io.IOException;

@Controller
public class MfaPageController {

    @GetMapping("/mfa/verify")
    public String mfaVerifyPage() {
        return "forward:/mfa-verify.html";
    }

    @GetMapping("/mfa/resume")
    public void resumeOAuth(
            HttpServletRequest request,
            HttpServletResponse response)
            throws IOException {

        String originalUrl =
                (String) request.getSession()
                        .getAttribute("MFA_ORIGINAL_URL");

        System.out.println(
                "========== MFA RESUME =========="
        );

        System.out.println(
                "Original OAuth URL = " + originalUrl
        );

        if (originalUrl == null || originalUrl.isBlank()) {

            response.sendError(
                    HttpServletResponse.SC_BAD_REQUEST,
                    "Original OAuth request not found"
            );

            return;
        }

        request.getSession().removeAttribute(
                "MFA_ORIGINAL_URL"
        );

        request.getSession().removeAttribute(
                "MFA_PENDING"
        );

        response.sendRedirect(originalUrl);
    }
}