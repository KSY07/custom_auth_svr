package com.scarf.authsvr;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.HashSet;
import java.util.Set;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.TestExecutionEvent;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.scarf.authsvr.DTO.SignInRequestDTO;
import com.scarf.authsvr.DTO.SignUpRequestDTO;
import com.scarf.authsvr.Entity.Constant.Roles;


@SpringBootTest
@AutoConfigureMockMvc
public class AuthControllerTest {

    private static final Logger logger = LoggerFactory.getLogger(AuthControllerTest.class);

    @Autowired
    private WebApplicationContext context;
    
    @Autowired
    private ObjectMapper mapper;

    @Autowired
    private MockMvc mvc;

    @BeforeEach
    public void setup() {
        mvc = MockMvcBuilders.webAppContextSetup(this.context)
        .apply(springSecurity())
        .build();

        logger.info("Before Each 실행");
        logger.info(mvc.toString());

    }

    @Test
    @DisplayName("Sign Up Request Test")
    public void signUpTest() throws Exception {
        Set<String> roles = new HashSet<>();
        roles.add("ROLE_ADMIN");
        String body = mapper.writeValueAsString(
            SignUpRequestDTO.builder()
            .email("ksy2008w@daum.net")
            .password("1111")
            .company("bizpeer")
            .roles(roles)
            .build()
        );

        logger.info(body.toString());

        mvc.perform(MockMvcRequestBuilders.post("/signup")
                                            .content(body)
                                            .contentType(MediaType.APPLICATION_JSON)
                                            .with(csrf()))
                                        .andExpect(status().isOk())
                                        .andDo(print());

        logger.info(mvc.toString());
    }

    @Test
    @DisplayName("Sign In Request Test")
    public void signInTest() throws Exception {

        String body = mapper.writeValueAsString(SignInRequestDTO.builder()
                                                .email("ksy2008w@daum.net")
                                                .password("1111")
                                                .build());

        logger.info(body);

        mvc.perform(MockMvcRequestBuilders.post("/signin")
                                                            .content(body)
                                                            .contentType(MediaType.APPLICATION_JSON)
                                                            .with(csrf()))
                                            .andExpect(status().isOk())
                                            .andDo(print());

        logger.info(mvc.toString());
                                    
    }

    @Test
    @DisplayName("Logout Test")
    @WithUserDetails(value = "ksy2008w@daum.net", setupBefore = TestExecutionEvent.TEST_EXECUTION)
    public void logoutTest() throws Exception {

        mvc.perform(MockMvcRequestBuilders.get("logout")
                                            .header("Authorization", "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJrc3kyMDA4d0BkYXVtLm5ldCIsImlhdCI6MTY3MzIzMzUyMSwiZXhwIjoxNjczMjM3MTIxfQ.bc459bgL2-6e1nD2QJrTmUDgupJy7QtNE5uEN9yYpyiDwHPPUctLwtJpU0e9Y8jJ1qCH6jDG_qv_3LBJpRHalg")
                                            .with(csrf()))
                                            .andExpect(status().isOk())
                                            .andDo(print());
    }
}