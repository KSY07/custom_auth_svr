package com.scarf.authsvr.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.scarf.authsvr.Entity.ScarfUserRole;
import com.scarf.authsvr.Entity.Constant.Roles;

public interface ScarfUserRoleRepository extends JpaRepository<ScarfUserRole, Long>{
    
   Optional<ScarfUserRole> findByName(Roles name);
}
