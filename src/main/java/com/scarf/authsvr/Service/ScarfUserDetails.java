package com.scarf.authsvr.Service;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.scarf.authsvr.Entity.ScarfUser;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ScarfUserDetails implements UserDetails {
    
    private static final long serialVersionUID = 1L;

    private long id;

    @JsonIgnore
    private String password;

    private String email;

    private Boolean isLocked;

    private Boolean email_verified;

    private Collection<? extends GrantedAuthority> authorities;

    public static ScarfUserDetails build(ScarfUser user) {

        List<GrantedAuthority> authorities = user.getRoles()
                                                .stream().map(
                                                    role -> new SimpleGrantedAuthority(role.getName().name())
                                                ).collect(Collectors.toList());
        
        return (new ScarfUserDetails(
            user.getId(),
            user.getPassword(),
            user.getEmail(),
            user.getIsLocked(),
            user.getEmail_verify(),
            authorities
        ));
    }

    public Collection<? extends GrantedAuthority> getAuthorities() {

        return authorities;
    }

    public String getUsername() {

        return email;
    }

    public String getPassword() {

        return password;
    }

    public boolean isAccountNonLocked() {

        return !isLocked;
    }

    public boolean isAccountNonExpired() {

        return true;
    }

    public boolean isCredentialsNonExpired() {

        return true;
    }

    public boolean isEnabled() {

        return true; // 추후 이메일 확인 로직 변경요망
    }
}
