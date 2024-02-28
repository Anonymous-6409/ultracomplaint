package com.ultrasoft.ultracomplaint.configuration;

import org.springframework.context.annotation.Configuration;
import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;

@Configuration
public class CorsConfiguration implements Filter{

    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
            throws IOException, ServletException {
        HttpServletResponse response = (HttpServletResponse) res;
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, PUT");
        response.setHeader("Access-Control-Max-Age", "3600");
        response.setHeader("Server", "Vartulz Tech Pvt. Ltd.");
        response.setHeader("Access-Control-Allow-Headers", "Content-Type, Accept, X-AUTH-TOKEN, ngrok-skip-browser-warning");
        chain.doFilter(req, res);
    }

    @Override
    public void init(FilterConfig filterConfig) {
        System.out.println("FILTER STARTED INIT");
    }

    @Override
    public void destroy() {
        System.out.println("FILTER STARTED DESTROY");
    }
}
