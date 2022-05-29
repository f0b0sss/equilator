package com.equilator.controllers;


import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@ContextConfiguration
@WebAppConfiguration
class AccountControllerTest {

    MockMvc mockMvc;
/*
    @BeforeEach
    void init(){
        mockMvc = MockMvcBuilders.standaloneSetup(AccountController.class).build();
    }

 */

    @Test
    void fdjdhf() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/")
        ).andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void updatePass() {

    }


    @Test
    void showRegistrationForm() throws Exception {
        mockMvc.perform(get("/registration"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.TEXT_HTML))
                .andExpect(view().name("registration"));

    }


}