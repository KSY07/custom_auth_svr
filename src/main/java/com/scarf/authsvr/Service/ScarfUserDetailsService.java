package com.scarf.authsvr.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.scarf.authsvr.Entity.ScarfUser;
import com.scarf.authsvr.Repository.ScarfUserRepository;


@Service
public class ScarfUserDetailsService implements UserDetailsService{
    
    @Autowired 
    private ScarfUserRepository scarfUserRepository;

    @Override
    public ScarfUserDetails loadUserByUsername(String email) throws UsernameNotFoundException{

        ScarfUser user = scarfUserRepository.findByEmail(email)
        .orElseThrow(
            () -> new UsernameNotFoundException(email + " can't find userdb")
        );


        return ScarfUserDetails.build(user);
    }   
}
