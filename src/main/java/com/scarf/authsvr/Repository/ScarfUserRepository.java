package com.scarf.authsvr.Repository;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.scarf.authsvr.Entity.ScarfUser;
import org.springframework.data.jpa.repository.Query;

public interface ScarfUserRepository extends JpaRepository<ScarfUser, Long>{
    Optional<ScarfUser> findByEmail(String email);

    @Query(value = "SELECT * FROM USERS", nativeQuery = true)
    List<ScarfUser> getAllUserData();
}
