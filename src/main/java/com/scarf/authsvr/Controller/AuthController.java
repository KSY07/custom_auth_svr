package com.scarf.authsvr.Controller;

import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.management.RuntimeErrorException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.scarf.authsvr.DTO.*;
import com.scarf.authsvr.Entity.RefreshToken;
import com.scarf.authsvr.Entity.ScarfUser;
import com.scarf.authsvr.Entity.ScarfUserRole;
import com.scarf.authsvr.Entity.Constant.Roles;
import com.scarf.authsvr.Repository.RefreshTokenRepository;
import com.scarf.authsvr.Repository.ScarfUserRepository;
import com.scarf.authsvr.Repository.ScarfUserRoleRepository;
import com.scarf.authsvr.Service.RefreshTokenService;
import com.scarf.authsvr.Service.ScarfUserDetails;
import com.scarf.authsvr.Service.ScarfUserDetailsService;
import com.scarf.authsvr.Utils.JwtUtils;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class AuthController {
    private final AuthenticationManager authenticationManager;
    private final ScarfUserRepository scarfUserRepository;
    private final ScarfUserRoleRepository scarfUserRoleRepository;
    private final RefreshTokenService refreshTokenService;
    private final RefreshTokenRepository refreshTokenRepository;
    private final JwtUtils jwtUtils;
    private final PasswordEncoder passwordEncoder;
    private static final Logger logger = LoggerFactory.getLogger(AuthController.class);

    /**
     * @author 김세영
     * @param req SignInRequestDTO
     * @return ResponseEntity<?> SignInResponseDTO.java 에 로그인 실패 혹은 성공 메세지를 담아 전송
     * @exception Incorrect Password, User Not Exist
     */
    @PostMapping("/signin")
    public ResponseEntity<?> trySignIn(@Valid @RequestBody SignInRequestDTO req) {
        logger.info("Start Authentication"); 
        try {
            logger.info("Try 문 진입");
            // Get Email & Password from req DTO Object and Create Authentication Object use
            // them
            logger.info(req.getEmail() + " & "+req.getPassword());

            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(req.getEmail(), req.getPassword()));
            logger.info("Authentication 객체 생성");

            // Save authentication object to SecurityContextHolder
            SecurityContextHolder.getContext().setAuthentication(authentication);
            logger.info("SecurityContext 저장완료");
            // Generate Auth Token
            String authToken = jwtUtils.generateAuthToken(authentication);
            logger.info("auth 토큰 생성: " + authToken);
            // Get userDetails Object Info from authentication Object
            ScarfUserDetails scarfUserDetails = (ScarfUserDetails) authentication.getPrincipal();

            // Granted Authority Logic
            List<String> roles = scarfUserDetails.getAuthorities().stream()
                    .map(item -> item.getAuthority())
                    .collect(Collectors.toList());
            
            RefreshToken refreshToken = refreshTokenService.createRefreshToken(scarfUserDetails.getId());
            logger.info("RefreshToken 생성: " + refreshToken);

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

    /**
    * @author 김세영
    * @param SignUpRequestDTO req
    * @return ResponseEntity<ResponseMessageDTO>
    * @exception IOExcpetion
    */
    @PostMapping("/signup")
    public ResponseEntity<?> trySignUp(@Valid @RequestBody SignUpRequestDTO req) throws IOException {
        try{

            if(scarfUserRepository.findByEmail(req.getEmail()).isPresent()) {
                logger.info("이메일 중복" + req.getEmail());
               return ResponseEntity.badRequest().body(
                    new ResponseMessageDTO(
                        "Email is Already Taken: " + req.getEmail()
                        )
                );
            }

            ScarfUser newUser = ScarfUser.builder()
                                .email(req.getEmail())
                                .password(passwordEncoder.encode(req.getPassword()))
                                .company(req.getCompany())
                                .isLocked(false)
                                .email_verify(true) //추후 False로 변환해야 이메일 인증 가능
                                .build();
            
            Set<String> strRoles = req.getRoles();
            Set<ScarfUserRole> roles = new HashSet<>();

            if(strRoles == null) {
                ScarfUserRole userRole = scarfUserRoleRepository.findByName(Roles.ROLE_USER)
                .orElseThrow(() -> new RuntimeException("Caution: Roles is null"));

                roles.add(userRole);                
            } else {
                strRoles.forEach(role -> {
                    switch (role) {
                        case "ROLE_ADMIN":
                            ScarfUserRole adminRole = scarfUserRoleRepository.findByName(Roles.ROLE_ADMIN)
                            .orElseThrow(() -> new RuntimeException("Roles Does Not in DB"));

                            roles.add(adminRole);
                            break;

                        case "P_SUPERUSER":
                            ScarfUserRole pSuperUserRole = scarfUserRoleRepository.findByName(Roles.P_SUPERUSER)
                                    .orElseThrow(() -> new RuntimeException("Role Does Not in DB"));
                            roles.add(pSuperUserRole);

                        case "P_USER":
                            ScarfUserRole pUserRole = scarfUserRoleRepository.findByName(Roles.P_USER)
                                    .orElseThrow(() -> new RuntimeException("Role Does Not in DB"));
                            roles.add(pUserRole);
                    
                        default:
                            ScarfUserRole userRole = scarfUserRoleRepository.findByName(Roles.ROLE_USER)
                            .orElseThrow(() -> new RuntimeException("Fatal Error: Please Inspect DB Table"));

                            roles.add(userRole);
                            break;
                    }
                });
            }

            newUser.setRoles(roles);
            scarfUserRepository.save(newUser);

            return ResponseEntity.ok(
                new ResponseMessageDTO("Sign Up Complete")
            );    
        } catch(Exception e) {
            throw new IOException("!! SignUp Does Not Complete !!" + e.getMessage());
        }
        
    }
    /**
     * @author 김세영
     * @param RefreshTokenRequest req
     * @return ResponseEntity<RefreshTokenResponse>
     * @exception UsernameNotFoundException
     */
    @PostMapping("/refresh")
    public ResponseEntity<?> refreshToken(@Valid @RequestBody RefreshTokenRequestDTO req) {
        String refreshToken = req.getToken();

        return refreshTokenRepository.findByToken(refreshToken)
                                    .map(refreshTokenService::verifyExpiration)
                                    .map(RefreshToken::getUser)
                                    .map(user -> {
                                        String token = jwtUtils.generateTokenFromUsername(user.getEmail());
                                        return ResponseEntity.ok(
                                            new RefreshTokenResponseDTO(
                                                token
                                            )
                                        );
                                    })
                                        .orElseThrow(() -> new UsernameNotFoundException("Refresh Token is invalid"));
    }

    @GetMapping("/logout")
    public ResponseEntity<?> tryLogOut(HttpServletRequest req, HttpServletResponse res) {
        String refreshToken = req.getHeader("RefreshToken");
        ScarfUser current_user = scarfUserRepository.findByEmail(
            jwtUtils.getEmailFromJwtToken(refreshToken)
        ).orElseThrow(()-> new RuntimeException("Fatal Error: User Not Found"));
        
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        if(auth != null && auth.isAuthenticated()) {
            new SecurityContextLogoutHandler().logout(req,res,auth);
        }

        refreshTokenRepository.deleteByUser(current_user);
        

        return ResponseEntity.ok(
            new ResponseMessageDTO("logout complete")
        );
    }

    /**
     * @author 김세영
     * @param PathVariable userId, RoleChangeDTO req
     * @return ResponseEntity<ResponseMessageDTO>
     * @exception UsernameNotFoundException
     */
    // @PostMapping("/changeRoles/{userId}")
    // public void changeRoles(@PathVariable long userId) {

    // }
}
