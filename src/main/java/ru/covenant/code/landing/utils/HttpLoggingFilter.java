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


    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request,
                                    @NonNull HttpServletResponse response,
                                    @NonNull FilterChain filterChain) throws ServletException, IOException {

        String url = request.getRequestURI();

        if (HttpLoggingUtils.isStaticResource(url)) {
            filterChain.doFilter(request, response);
            return;
        }

        Long start = System.currentTimeMillis();

        HttpLoggingUtils.initCorrelationContext(request, response);

        try {
            filterChain.doFilter(request, response);
        } finally {
            Long duration = System.currentTimeMillis() - start;
            HttpLoggingUtils.logRequest(request, response, url, duration);
            MDC.clear();
        }
    }
}