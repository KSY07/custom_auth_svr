package com.scarf.authsvr.DTO;

import java.util.Set;

import javax.validation.constraints.NotNull;

import com.scarf.authsvr.Entity.ScarfUserRole;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SignUpRequestDTO {

    @NotNull
    private @Getter @Setter String email;

    @NotNull
    private @Getter @Setter String password;

    private @Getter @Setter String company;

    private @Getter @Setter Set<String> roles;
    
}
