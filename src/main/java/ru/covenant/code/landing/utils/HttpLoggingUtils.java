package ru.covenant.code.landing.utils;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;

import java.util.Set;
import java.util.UUID;

public final class HttpLoggingUtils {

    private static final Logger log = LoggerFactory.getLogger(HttpLoggingUtils.class);

    private static final String LOG_TEMPLATE = "HTTP {} {} -> {} ({} ms)";
    private static final String CORRELATION_ID_HEADER = "X-Correlation-Id";
    private static final String CORRELATION_ID_MDC_KEY = "correlationId";

    private static final Set<String> STATIC_RESOURCES_PREFIXES = Set.of(
            "/css",
            "/js",
            "/images",
            "/static",
            "/favicon.ico"
    );

    private HttpLoggingUtils() {
    }

    private static boolean shouldLog(int status) {
        return status >= 400;
    }

    public static void logRequest(HttpServletRequest request, HttpServletResponse response, String url, Long duration) {
        int status = response.getStatus();

        if (!shouldLog(status)) {
            return;
        }

        if (status >= 500) {
            log.error(LOG_TEMPLATE, request.getMethod(), url, status, duration);
        } else {
            log.warn(LOG_TEMPLATE, request.getMethod(), url, status, duration);
        }
    }

    public static void initCorrelationContext(HttpServletRequest request, HttpServletResponse response) {
        String correlationId = request.getHeader(CORRELATION_ID_HEADER);

        if (correlationId == null || correlationId.isBlank()) {
            correlationId = UUID.randomUUID().toString();
        }

        MDC.put(CORRELATION_ID_MDC_KEY, correlationId);
        response.setHeader(CORRELATION_ID_HEADER, correlationId);
    }

    public static boolean isStaticResource(String url) {
        return STATIC_RESOURCES_PREFIXES.stream()
                .anyMatch(url::startsWith);
    }
}
