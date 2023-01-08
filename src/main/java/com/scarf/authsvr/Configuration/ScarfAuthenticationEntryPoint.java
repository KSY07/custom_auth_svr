package com.scarf.authsvr.Configuration;

import java.io.IOException;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;

@Component
public class ScarfAuthenticationEntryPoint implements AuthenticationEntryPoint, Serializable {
    
    private final static Logger logger = LoggerFactory.getLogger(ScarfAuthenticationEntryPoint.class);

    @Override
    public void commence(HttpServletRequest req, HttpServletResponse res, 
                            AuthenticationException authException) throws IOException, ServletException {
                                logger.error("Unauthorized Exception: {}",authException.getMessage());

                                res.setContentType(MediaType.APPLICATION_JSON_VALUE);
                                res.setStatus(HttpServletResponse.SC_UNAUTHORIZED);

                                final Map<String, Object> body = new HashMap<>();

                                body.put("status", HttpServletResponse.SC_UNAUTHORIZED);
                                body.put("error",  "unauthorized");
                                body.put("message", authException.getMessage());
                                body.put("path", req.getServletPath());

                                final ObjectMapper mapper = new ObjectMapper();

                                mapper.writeValue(res.getOutputStream(), body);
                            }
}
