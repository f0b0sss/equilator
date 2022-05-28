package com.equilator.controllers;

import com.equilator.DAO.DBUtils;
import com.equilator.config.Config;
import com.equilator.models.user.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import com.equilator.repository.UserRepository;
import com.equilator.services.UserService;

//@WebMvcTest(AccountController.class)
//@SpringBootTest(classes = {UserService.class, UserRepositoryImpl.class, DBUtils.class, MockMvc.class,
//   DefaultData.class, JdbcTemplate.class
//})
//@ExtendWith(SpringExtension.class)
//@SpringBootTest
@AutoConfigureMockMvc
@Import({Config.class})
class AccountControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    UserRepository userRepository;
    @Autowired
    UserService userService;
    @Autowired
    DBUtils dbUtils;
    //  @Autowired
    //   JdbcTemplate jdbcTemplate;


    @Test
    void showRegistrationForm() throws Exception {
        mockMvc.perform(
                        MockMvcRequestBuilders.get("/registration")
                )
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentTypeCompatibleWith(MediaType.TEXT_HTML))
                .andExpect(MockMvcResultMatchers.view().name("/registration"))
                .andExpect(MockMvcResultMatchers.model().attribute("user", new User()));
    }

    @Test
    void showRegistrationForm1() throws Exception {

    }
}