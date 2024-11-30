package com.cognizant.assettracker.logging;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.util.ContentCachingRequestWrapper;
import org.springframework.web.util.ContentCachingResponseWrapper;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
public class LoggingFilter implements Filter {
    private static final Logger LOGGER = LoggerFactory.getLogger(LoggingFilter.class);

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        Filter.super.init(filterConfig);
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain) throws IOException, ServletException {
        ContentCachingRequestWrapper requestWrapper = new ContentCachingRequestWrapper((HttpServletRequest) request);
        ContentCachingResponseWrapper responseWrapper = new ContentCachingResponseWrapper((HttpServletResponse) response);

        long startTime = System.currentTimeMillis();
        filterChain.doFilter(requestWrapper, responseWrapper);
        long timeTaken = System.currentTimeMillis() - startTime;

        String requestUri = ((HttpServletRequest) request).getRequestURI();
        String requestMethod = ((HttpServletRequest) request).getMethod();
        String requestBody = getStringValue(requestWrapper.getContentAsByteArray(),
                request.getCharacterEncoding());
        String responseBody = getStringValue(responseWrapper.getContentAsByteArray(),
                response.getCharacterEncoding());


        // Retrieve the correlationId from request headers
        String correlationId = requestWrapper.getHeader("correlationId");
        LOGGER.info("Request: {} {}",((HttpServletRequest) request).getRequestURI(),((HttpServletRequest) request).getMethod());
        LOGGER.info(
                "Client Asset Tracker Finished Processing:\nMETHOD={};\nREQUESTURI={};\nREQUEST BODY={};\nRESPONSE CODE={};\nRESPONSE BODY={};\nTIME TAKEN={} sec",
                requestMethod, requestUri, requestBody, ((HttpServletResponse) response).getStatus(), responseBody,
                timeTaken);
              // Log request and response details separately
        LOGGER.info(
                "Request Details:\nMETHOD={};\nREQUESTURI={};\nREQUEST BODY={};\nCORRELATION ID={}",
                requestMethod, requestUri, requestBody, correlationId);

        LOGGER.info(
                "Response Details:\nRESPONSE CODE={};\nRESPONSE BODY={};\nCORRELATION ID={}",
                ((HttpServletResponse) response).getStatus(), responseBody, correlationId);

        responseWrapper.copyBodyToResponse();
    }

    @Override
    public void destroy() {
        Filter.super.destroy();
    }

    private String getStringValue(byte[] contentAsByteArray, String characterEncoding) {
        try {
            return new String(contentAsByteArray, 0, contentAsByteArray.length, characterEncoding);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return "";
    }
}


