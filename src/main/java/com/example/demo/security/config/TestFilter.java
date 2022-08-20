package com.example.demo.security.config;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

public class TestFilter extends BasicAuthenticationFilter {
    public TestFilter(AuthenticationManager authenticationManager) {
        super(authenticationManager);
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        Enumeration<String> headerNames = request.getHeaderNames();
        while(headerNames.hasMoreElements())
        {
            String elem = headerNames.nextElement();
            System.out.println(elem+": "+request.getHeader(elem));
        }
        System.out.println(request.getRemoteAddr());
        System.out.println(request.getProtocol());
        System.out.println(response.getStatus());
        chain.doFilter(request,response);
    }
}
