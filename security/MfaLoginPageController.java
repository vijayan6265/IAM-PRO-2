package com.zaalima.iam_server.security;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.savedrequest.SavedRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.io.IOException;

@Controller
public class MfaLoginPageController {

    private final HttpSessionRequestCache requestCache =
            new HttpSessionRequestCache();

    @GetMapping("/login")
    public void login(
            HttpServletRequest request,
            HttpServletResponse response)
            throws IOException {

        SavedRequest savedRequest =
                requestCache.getRequest(request, response);

        if (savedRequest != null) {

            request.getSession().setAttribute(
                    "MFA_ORIGINAL_URL",
                    savedRequest.getRedirectUrl()
            );
        }

        response.sendRedirect("/login.html");
    }
}