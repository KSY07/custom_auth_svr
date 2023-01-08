package com.scarf.authsvr;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
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
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.scarf.authsvr.DTO.SignInRequestDTO;


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
    @DisplayName("Sign In Request Test")
    public void signInTest() throws Exception {
        String body = mapper.writeValueAsString(SignInRequestDTO
                                                .builder()
                                                .email("ksy2008w@naver.com")
                                                .password("1111")
                                                .build());


        mvc.perform(MockMvcRequestBuilders.post("http://localhost:8080/signin")                                                           .with(csrf())
                                                            .content(body)
                                                            .contentType(MediaType.APPLICATION_JSON))
                                            .andExpect(status().isOk())
                                            .andDo(print());

        logger.info(mvc.toString());
                                    
    }
}