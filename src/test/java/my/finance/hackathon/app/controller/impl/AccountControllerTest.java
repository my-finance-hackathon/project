package my.finance.hackathon.app.controller.impl;

import lombok.RequiredArgsConstructor;
import my.finance.hackathon.app.repository.UserRepository;
import my.finance.hackathon.app.service.UserService;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest
@AutoConfigureMockMvc
@RequiredArgsConstructor
class AccountControllerTest {

    private MockMvc mockMvc;

    @MockBean
    private UserRepository userRepository;

    @Test
    @WithMockUser(username = "user", roles = {"ROLE_USER"})
    void accountListHandler() throws Exception {
        mockMvc.perform(get("/account"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(Matchers.anything("account")));
    }

    @Test
    @WithMockUser(username = "user", roles = {"ROLE_USER"})
    void accountHandlerException() throws Exception {
        mockMvc.perform(get("/account/1"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().source(Matchers.anything("User sid not found")));
    }

    @Test
    @WithMockUser(username = "user", roles = {"ROLE_USER"})
    void createAccountHandler() throws Exception {
        mockMvc.perform(get("/account/create"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().source(Matchers.anything("ok")));

    }
}