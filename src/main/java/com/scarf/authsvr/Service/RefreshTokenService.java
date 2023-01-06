package com.scarf.authsvr.Service;

import java.time.Instant;
import java.util.UUID;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.scarf.authsvr.Entity.RefreshToken;
import com.scarf.authsvr.Exception.RefreshTokenException;
import com.scarf.authsvr.Repository.RefreshTokenRepository;
import com.scarf.authsvr.Repository.ScarfUserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RefreshTokenService {
    
    private static final Logger logger = LoggerFactory.getLogger(RefreshTokenService.class);
    
    @Value("${jwt.RefreshExpirationMs}")
    private long refreshTokenExpirationMs;

    private final RefreshTokenRepository refreshTokenRepository;

    private final ScarfUserRepository scarfUserRepository;

    public RefreshToken createRefreshToken(long userId) {
        RefreshToken refreshToken = new RefreshToken();

        refreshToken.setUser(scarfUserRepository.findById(userId).orElseThrow(
            () -> new UsernameNotFoundException("User Not Found")
        ));
        refreshToken.setExpiryDate(Instant.now().plusMillis(refreshTokenExpirationMs));
        refreshToken.setToken(UUID.randomUUID().toString());

        refreshTokenRepository.save(refreshToken);

        return refreshToken;
    }

    public RefreshToken verifyExpiration(RefreshToken token) {
        if(token.getExpiryDate().compareTo(Instant.now()) < 0) {
            refreshTokenRepository.delete(token);
            throw new RefreshTokenException(token.getToken(), "Refresh token was expired.");
        }

        return token;
    }

    @Transactional
    public int deleteByUserId(long userId) {
        return refreshTokenRepository.deleteByUser(scarfUserRepository.findById(userId).get());
    } 
}
