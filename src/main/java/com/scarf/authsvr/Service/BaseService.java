package com.scarf.authsvr.Service;

import com.scarf.authsvr.DTO.UserDTO;
import com.scarf.authsvr.Entity.ScarfUser;
import com.scarf.authsvr.Repository.ScarfUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class BaseService {

    private final ScarfUserRepository scarfUserRepository;

    public List<UserDTO> getAllUserList() {

        List<ScarfUser> rs = scarfUserRepository.getAllUserData();
        List<UserDTO> result = new ArrayList<>();
        rs.forEach(data -> {
            UserDTO tempUserDTO = UserDTO.builder()
                    .username(data.getUsername())
                    .email(data.getEmail())
                    .company(data.getCompany())
                    .build();

            result.add(tempUserDTO);
        });

        return result;
    }

}
