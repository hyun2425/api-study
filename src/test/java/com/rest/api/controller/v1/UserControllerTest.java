package com.rest.api.controller.v1;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rest.api.entity.User;
import com.rest.api.repo.UserJpaRepo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.util.LinkedMultiValueMap;

import java.util.Collections;
import java.util.HashMap;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@AutoConfigureMockMvc
@SpringBootTest
class UserControllerTest {

    @Autowired
    protected MockMvc mockMvc;
    @Autowired
    private UserJpaRepo userJpaRepo;
    @Autowired
    private PasswordEncoder passwordEncoder;

    private String token;

    @BeforeEach
    void setUp() throws Exception {
        userJpaRepo.deleteByUid("test@naver.com");
        saveSampleUser();

        LinkedMultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("id", "test@naver.com");
        params.add("password", "1234");
        MvcResult mvcResult = mockMvc.perform(post("/v1/signin").params(params))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.code").value(0))
                .andExpect(jsonPath("$.msg").exists())
                .andExpect(jsonPath("$.data").exists())
                .andReturn();

        String resultString = mvcResult.getResponse().getContentAsString();
        token = new ObjectMapper().readValue(resultString, HashMap.class).get("data").toString();

    }

    private void saveSampleUser() {
        String uid = "test@naver.com";
        String name = "hsj";
        userJpaRepo.save(User.builder()
                .uid(uid)
                .password(passwordEncoder.encode("1234"))
                .name(name)
                .roles(Collections.singletonList("ROLE_USER"))
                .build());
    }

    @Test
    void invalidToken() throws Exception {
        mockMvc.perform(get("/v1/users")
                        .header("X-AUTH-TOKEN", "XXXXXXXXXX"))
                .andDo(print())
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/exception/entrypoint"));
    }

    @Test
    @WithMockUser(username = "mockUser", roles = {"ADMIN"})
        // 가상의 Mock 유저 대입
    void accessDenied() throws Exception {
        mockMvc.perform(get("/v1/users"))
                .andDo(print())
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/exception/accessDenied"));
    }

    @Test
    void findAllUser() throws Exception {
        mockMvc.perform(get("/v1/users")
                        .header("X-AUTH-TOKEN", token))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.list").exists());
    }

    @Test
    void findUserById() throws Exception {
        mockMvc.perform(get("/v1/user/1")
                        .header("X-AUTH-TOKEN", token))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data").exists());
    }

    @Test
    void modify() {
    }

    @Test
    void delete() {
    }

}