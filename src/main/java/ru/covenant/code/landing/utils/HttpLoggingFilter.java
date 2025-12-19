package ru.covenant.code.landing.utils;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.jspecify.annotations.NonNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Set;
import java.util.UUID;


public class HttpLoggingFilter extends OncePerRequestFilter {

    private static final Logger log = LoggerFactory.getLogger(HttpLoggingFilter.class);

    private static final String CORRELATION_ID_HEADER = "X-Correlation-Id";
    private static final String CORRELATION_ID_MDC_KEY = "correlationId";

    private static final Set<String> STATIC_RESOURCES_PREFIXES = Set.of(
            "/css",
            "/js",
            "/images",
            "/static",
            "/favicon.ico"
    );

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull FilterChain filterChain) throws ServletException, IOException {

        String url = request.getRequestURI();

        if (isStaticResource(url)) {
            filterChain.doFilter(request, response);
            return;
        }

        executeWithLogging(request, response, filterChain, url);
    }

    private void executeWithLogging(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain, String url) throws IOException, ServletException {

        Long start = System.currentTimeMillis();

        initCorrelationContext(request, response);

        try {
            filterChain.doFilter(request, response);
        } finally {
            Long duration = System.currentTimeMillis() - start;

            logRequest(request, url, response, duration);

            MDC.clear();
        }
    }

    private boolean shouldLog(int status) {
        return status >= 400;
    }

    private void logRequest(HttpServletRequest request, String url, HttpServletResponse response, Long duration) {
        int status = response.getStatus();
        if (!shouldLog(status)) {
            return;
        }

        if (status >= 500) {
            log.error("HTTP {} {} -> {} ({} ms)",
                    request.getMethod(),
                    url,
                    status,
                    duration
            );
        } else {
            log.warn("HTTP {} {} -> {} ({} ms)",
                    request.getMethod(),
                    url,
                    status,
                    duration
            );
        }
    }

    private static void initCorrelationContext(HttpServletRequest request, HttpServletResponse response) {
        String correlationId = request.getHeader(CORRELATION_ID_HEADER);

        if (correlationId == null || correlationId.isBlank()) {
            correlationId = UUID.randomUUID().toString();
        }

        MDC.put(CORRELATION_ID_MDC_KEY, correlationId);
        response.setHeader(CORRELATION_ID_HEADER, correlationId);
    }

    private boolean isStaticResource(String url) {
        return STATIC_RESOURCES_PREFIXES.stream()
                .anyMatch(url::startsWith);
    }
}