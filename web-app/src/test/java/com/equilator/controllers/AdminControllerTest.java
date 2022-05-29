package com.equilator.controllers;

import com.equilator.config.TestControllerConfig;
import com.equilator.models.user.User;
import com.equilator.services.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.support.AnnotationConfigContextLoader;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@ExtendWith(SpringExtension.class)
//@ContextConfiguration
@WebAppConfiguration
@ContextConfiguration(
        classes = {TestControllerConfig.class},
        loader = AnnotationConfigContextLoader.class)
@Sql(scripts = {"classpath:schema_users.sql", "classpath:data_users.sql"})
class AdminControllerTest {

    MockMvc mockMvc;

    @Autowired
    private UserService userService;

  // @Autowired
  // UserRepository userRepository;
  // @Autowired
  // JdbcTemplate jdbcTemplate;

    @Test
    void shouldCreateMockMvc() {
        assertNotNull(userService);
    }


    @BeforeEach
    void init(){
        mockMvc = MockMvcBuilders.standaloneSetup(AdminController.class).build();
       // this.userService = new UserService();
       // this.userRepository = new UserRepositoryImpl();
       // this.dbUtils = new DBUtils();
       // this.jdbcTemplate = new JdbcTemplate();
    }

    @Test
    @WithMockUser(authorities = "access:admin")
    void admin_returnUsersList() throws Exception {
       // when(dbUtils.getAllUsers("email")).thenReturn(
       //         List.of(new User(), new User())
       // );
       // when(userRepository.getAllUsers("email")).thenReturn(
       //         List.of(new User(), new User())
       // );
      when(userService.getAllUsers("email")).thenReturn(
              List.of(new User(), new User())
      );

   //     System.out.println(userService.getAllUsers("email"));

      // mockMvc.perform(get("/main"))
      //         .andExpect(status().isOk())
      //         .andExpect(content().contentTypeCompatibleWith(MediaType.TEXT_HTML))
      //         .andExpect(view().name("main"));
           //     .andExpect(model().attribute("users", List.of(new User(), new User())));

        this.mockMvc.perform(get("/admin/main"))
                .andExpect(view().name("main"));
    }

    @Test
    void adminSort() {
    }

    @Test
    void show() {
    }

    @Test
    void update() {
    }

    @Test
    void delete() {
    }
}