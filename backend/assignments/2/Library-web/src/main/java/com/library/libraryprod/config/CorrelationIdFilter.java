package com.library.libraryprod.config;

import org.slf4j.MDC;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.UUID;

/**
 * Servlet filter that ensures every request has a correlation ID.
 * Reads the ID from the request header (or generates one), stores it in MDC for log correlation,
 * echoes it back in the response header, logs request start/end, and always clears MDC.
 */
@Component
public class CorrelationIdFilter extends OncePerRequestFilter {

    /**
     * Name of the HTTP header carrying the correlation ID.
     */
    public static final String CORRELATION_ID_HEADER = AppConstants.CORRELATION_ID_HEADER;

    /**
     * MDC key under which the correlation ID is stored for log enrichment.
     */
    public static final String MDC_KEY = AppConstants.MDC_KEY;

    /**
     * Applies correlation ID propagation and request logging around the filter chain.
     *
     * @param request incoming HTTP request
     * @param response HTTP response to be sent
     * @param filterChain the remaining filter chain
     * @throws ServletException if the filter chain throws
     * @throws IOException if I/O errors occur
     */
    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain
    ) throws ServletException, IOException {

        long startTime = System.currentTimeMillis();

        String correlationId = request.getHeader(CORRELATION_ID_HEADER);
        if (correlationId == null || correlationId.isEmpty()) {
            correlationId = UUID.randomUUID().toString();
        }

        MDC.put(MDC_KEY, correlationId);

        response.setHeader(CORRELATION_ID_HEADER, correlationId);

        try {
            logger.info(String.format(
                    "request started: method=%s, path=%s, correlationId=%s",
                    request.getMethod(), request.getRequestURI(), correlationId
            ));

            filterChain.doFilter(request, response);

        } finally {
            long latencyMs = System.currentTimeMillis() - startTime;

            logger.info(String.format(
                    "request completed: method=%s, path=%s, status=%s, latencyMs=%s, correlationId=%s",
                    request.getMethod(),
                    request.getRequestURI(),
                    response.getStatus(),
                    latencyMs,
                    correlationId
            ));

            MDC.clear();
        }
    }
}
