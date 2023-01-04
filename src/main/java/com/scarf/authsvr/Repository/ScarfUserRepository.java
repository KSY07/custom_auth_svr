package com.scarf.authsvr.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.scarf.authsvr.Entity.ScarfUser;

public interface ScarfUserRepository extends JpaRepository<ScarfUser, Long>{
    Optional<ScarfUser> findByEmail(String email);
}
