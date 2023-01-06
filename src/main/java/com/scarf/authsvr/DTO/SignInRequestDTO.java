package com.scarf.authsvr.DTO;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class SignInRequestDTO {
    
    private String email;

    private String password;

}
