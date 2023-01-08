package com.scarf.authsvr.Controller;

import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.scarf.authsvr.DTO.ResponseMessageDTO;
import com.scarf.authsvr.DTO.SignInRequestDTO;
import com.scarf.authsvr.DTO.SignInResponseDTO;
import com.scarf.authsvr.Entity.RefreshToken;
import com.scarf.authsvr.Service.RefreshTokenService;
import com.scarf.authsvr.Service.ScarfUserDetails;
import com.scarf.authsvr.Service.ScarfUserDetailsService;
import com.scarf.authsvr.Utils.JwtUtils;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class AuthController {
    private final AuthenticationManager authenticationManager;
    private final ScarfUserDetailsService scarfUserDetailsService;
    private final RefreshTokenService refreshTokenService;
    private final JwtUtils jwtUtils;
    private static final Logger logger = LoggerFactory.getLogger(AuthController.class);

    /**
     * @author 김세영
     * @param req SignInRequestDTO
     * @return ResponseEntity<?> SignInResponseDTO.java 에 로그인 실패 혹은 성공 메세지를 담아 전송
     * @exception Incorrect Password, User Not Exist
     */
    @PostMapping("/signin")
    public ResponseEntity<?> trySignIn(@Valid SignInRequestDTO req) {
        logger.info("Start Authentication"); 
        try {
            // Get Email & Password from req DTO Object and Create Authentication Object use
            // them
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(req.getEmail(), req.getPassword()));

            // Save authentication object to SecurityContextHolder
            SecurityContextHolder.getContext().setAuthentication(authentication);

            // Generate Auth Token
            String authToken = jwtUtils.generateAuthToken(authentication);

            // Get userDetails Object Info from authentication Object
            ScarfUserDetails scarfUserDetails = (ScarfUserDetails) authentication.getPrincipal();
            
            logger.info(authentication.toString());

            // Granted Authority Logic
            List<String> roles = scarfUserDetails.getAuthorities().stream()
                    .map(item -> item.getAuthority())
                    .collect(Collectors.toList());
            
            RefreshToken refreshToken = refreshTokenService.createRefreshToken(scarfUserDetails.getId());

            return ResponseEntity.ok(
                    new SignInResponseDTO(
                        scarfUserDetails.getEmail(),
                        scarfUserDetails.getUsername(),
                        authToken,
                        refreshToken.getToken(),
                        roles
                    ));
        } catch (UsernameNotFoundException ex) {
            throw new UsernameNotFoundException("Login Fail: " + req.getEmail());
        }

    }

    @PostMapping("/signup")
    public void trySignUp(HttpServletRequest req) {

    }

    @PostMapping("/changeRoles/{userId}")
    public void changeRoles(@PathVariable long userId) {

    }
}
