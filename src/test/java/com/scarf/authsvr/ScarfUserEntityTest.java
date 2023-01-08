package com.scarf.authsvr;

import javax.servlet.http.HttpServletRequest;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.scarf.authsvr.Entity.ScarfUser;
import com.scarf.authsvr.Entity.ScarfUserRole;
import com.scarf.authsvr.Entity.Constant.Roles;
import com.scarf.authsvr.Repository.ScarfUserRepository;

@SpringBootTest
public class ScarfUserEntityTest {
    
    @Autowired
    private ScarfUserRepository userRepository;

    @Test
    public void userCreateTest(){
        
       ScarfUser user = new ScarfUser().builder()
                        .email("ksy2008w@naver.com")
                        .password("1111")
                        .company("bizpeer")
                        .email_verify(true)
                        .isLocked(false)
                        .build();
        
        userRepository.save(user);
    }
}
