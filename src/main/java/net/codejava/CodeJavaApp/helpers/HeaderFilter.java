package net.codejava.CodeJavaApp.helpers;

import org.springframework.http.HttpHeaders;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Enumeration;

public class HeaderFilter implements Filter {

    private static ThreadLocal<HttpHeaders> headersThreadLocal = new ThreadLocal<>();

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpHeaders headers = new HttpHeaders();
        Enumeration<String> headerNames = httpRequest.getHeaderNames();
        while (headerNames.hasMoreElements()) {
            String headerName = headerNames.nextElement();
            String headerValue = httpRequest.getHeader(headerName);
            headers.set(headerName, headerValue);
        }
        headersThreadLocal.set(headers);
        chain.doFilter(request, response);
    }

    public static HttpHeaders getHeaders() {
        return headersThreadLocal.get();
    }
}
