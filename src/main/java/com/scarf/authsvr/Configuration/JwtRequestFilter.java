package com.scarf.authsvr.Configuration;

import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import com.scarf.authsvr.Service.ScarfUserDetails;
import com.scarf.authsvr.Service.ScarfUserDetailsService;
import com.scarf.authsvr.Utils.JwtUtils;

import io.jsonwebtoken.ExpiredJwtException;
import lombok.NoArgsConstructor;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@NoArgsConstructor
public class JwtRequestFilter extends OncePerRequestFilter {
    
    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private ScarfUserDetailsService scarfUserDetailsService;
    
    private static final Logger logger = LoggerFactory.getLogger(JwtRequestFilter.class);

    @Override
    protected void doFilterInternal(HttpServletRequest req, HttpServletResponse res, FilterChain filterchain) throws ServletException, IOException {
        
        try {
            String jwt = parseJwt(req);
            if(jwt != null && jwtUtils.validateJwtToken(jwt)) {
                String email = jwtUtils.getEmailFromJwtToken(jwt);

                ScarfUserDetails scarfUserDetails = scarfUserDetailsService.loadUserByUsername(email);
                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                    scarfUserDetails, null, scarfUserDetails.getAuthorities()
                    );

                    authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(req));

                    SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        } catch (IllegalArgumentException e) {
            logger.error("Unalbe to get JWT Token: {}", e.getMessage());
        } catch (ExpiredJwtException e) {
            logger.error("JWT Token has expired: {}", e.getMessage());
        }

        filterchain.doFilter(req,res);
    }
    
    private String parseJwt(HttpServletRequest req) {
        String headerAuth = req.getHeader("Authorization");
        if (StringUtils.hasText(headerAuth) && headerAuth.startsWith("Bearer ")) {
            return headerAuth.substring(7, headerAuth.length());
        }


        return null;
    }
}
