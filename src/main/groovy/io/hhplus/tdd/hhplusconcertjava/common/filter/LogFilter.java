package io.hhplus.tdd.hhplusconcertjava.common.filter;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.util.ContentCachingRequestWrapper;
import org.springframework.web.util.ContentCachingResponseWrapper;

import java.io.IOException;

@Slf4j
public class LogFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

        ContentCachingRequestWrapper cacheRequest = new ContentCachingRequestWrapper((HttpServletRequest) request);

        String url = cacheRequest.getRequestURL().toString().replace("http://localhost:8080", "");
        String method = cacheRequest.getMethod();
        String userId = cacheRequest.getHeader("Authorization");
        if(userId == null || userId.equals("")){
            userId = "UNKNOWN";
        }

        log.info("URL: {}, METHOD: {}, Authorization: {}", url, method,userId);
        chain.doFilter(request, response);

        ContentCachingResponseWrapper cacheResponse = new ContentCachingResponseWrapper((HttpServletResponse) response);
        log.info("Status: ", cacheResponse.getStatus());

    }
}
