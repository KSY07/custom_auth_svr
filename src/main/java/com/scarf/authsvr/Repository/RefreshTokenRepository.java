package com.scarf.authsvr.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;

import com.scarf.authsvr.Entity.RefreshToken;
import com.scarf.authsvr.Entity.ScarfUser;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {
    Optional<RefreshToken> findByToken(String token);
    
    @Modifying
    Integer deleteByUser(ScarfUser user);
}
