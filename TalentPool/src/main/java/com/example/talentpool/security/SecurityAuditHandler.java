package com.example.talentpool.security;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class SecurityAuditHandler implements AccessDeniedHandler {

    private static final Logger log =
            LoggerFactory.getLogger(SecurityAuditHandler.class);

    @Override
    public void handle(HttpServletRequest request,
                       HttpServletResponse response,
                       AccessDeniedException ex) throws IOException {

        log.warn("Forbidden access attempt to '{}' from User {}",
                request.getRequestURI(),
                request.getRemoteAddr());

        response.sendError(HttpServletResponse.SC_FORBIDDEN, "Access Denied");
    }
}
