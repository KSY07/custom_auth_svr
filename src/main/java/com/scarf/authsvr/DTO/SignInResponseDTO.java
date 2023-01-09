package com.scarf.authsvr.DTO;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class SignInResponseDTO {

    private String email;
    private String name;
    private String authToken;
    private String refreshToken;
    private List<String> roles;

}
