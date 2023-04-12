package com.scarf.authsvr.DTO;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserDTO {
    private @Getter @Setter String username;
    private @Getter @Setter String email;
    private @Getter @Setter String company;

}
