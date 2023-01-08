package com.scarf.authsvr.DTO;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;

@Builder
@AllArgsConstructor
public class SignInResponseDTO {
    
    private String email;
    private String name;
    private String authToken;
    private String refreshToken;
    private List<String> roles;
    
}
