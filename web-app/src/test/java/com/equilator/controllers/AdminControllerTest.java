package com.equilator.controllers;

import com.equilator.config.TestControllerConfig;
import com.equilator.services.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.support.AnnotationConfigContextLoader;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;


@ExtendWith(SpringExtension.class)
@ContextConfiguration(
        classes = {TestControllerConfig.class},
        loader = AnnotationConfigContextLoader.class)
@Sql(scripts = {"classpath:schema_users.sql", "classpath:data_users.sql"})
class AdminControllerTest {


    @Mock
    UserService userService;

    MockMvc mockMvc;

    @BeforeEach
    void init() {
        mockMvc = MockMvcBuilders.standaloneSetup(AdminController.class).build();
        // this.userService = new UserService();
        // this.userRepository = new UserRepositoryImpl();
        // this.dbUtils = new DBUtils();
        // this.jdbcTemplate = new JdbcTemplate();
    }
/*
    @Test
 //   @WithMockUser(authorities = "access:superadmin")
 //   @WithMockUser(roles = "SUPERADMIN")
    void show() throws Exception {
         User user = new User();
         user.setFirstname("Dmytro");
         user.setLastname("Zimin");
         user.setEmail("superadmin1@mail.com");
         user.setPassword("111");

         when(userService.getUserById(1)).thenReturn(user);

        mockMvc.perform(get("/admin/user-info/1").with(user("Dmytro").roles("SUPERADMIN").password("admin")))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.TEXT_HTML));
    }

 */

}

/*
    @Test
    void shouldCreateMockMvc1() {
        assertNotNull(mockMvc);
    }

    @Test
    void fdjdhf() throws Exception {
        mockMvc.perform(get("/admin/main")
        ).andExpect(status().isOk())
        .andExpect(view().name("admin/main2"));
    }

    @Test
    void fdjdh2f() throws Exception {
        when(userService.getAllUsers("email")).thenReturn(
                         List.of(new User()));

        mockMvc.perform(get("/admin/main")
                ).andExpect(status().isOk())
                .andExpect(view().name("admin/main2"));
    }

 */


   // @Test
        //  @WithMockUser(authorities = "access:admin")
   // void admin_returnUsersList() throws Exception {
       // User user = new User();
       // user.setFirstname("Dmytro");
       // user.setLastname("Zimin");
       // user.setEmail("superadmin1@mail.com");
       // user.setPassword("111");
       //
       // when(userService.getAllUsers("email")).thenReturn(
       //         List.of(user));
       //
       // this.mockMvc.perform(get("/admin/main")).andDo(print()).andExpect(status().isOk())
       //         .andExpect(content().string(containsString("Dmytro")));

        /*
        mockMvc.perform(
                        get("/admin/main")
                )
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.TEXT_HTML))
                .andExpect(MockMvcResultMatchers.view().name("main"));

         */
        //   .andExpect(MockMvcResultMatchers.model().attribute("applicationName", "Notes Dev"));


        //   this.mockMvc.perform(get("/admin/main")).andDo(print())
        //         .andExpect(view().name("main"));


        // mockMvc.perform(get("/admin/main").contentType(MediaType.TEXT_HTML))
        //         .andExpect(status().isOk())
        //         .andExpect(content().contentTypeCompatibleWith("text/html"))
        //         .andExpect(jsonPath("$.users").value(List.of(new User())));
        //     //    .andExpect(view().name("admin/main"));
        //   //      .andExpect(MockMvcResultMatchers.model().attribute("applicationName", "Notes Dev"));

        //  mockMvc.perform(get("/index").contentType(MediaType.APPLICATION_JSON))
        //          .andExpect(status().isOk())
        //          .andExpect(content().contentTypeCompatibleWith("application/json"))
        //          .andExpect(jsonPath("$.greeting").value("Hello World"));

   // }


/*
    @Test
    void adminSort() {
    }

 */


/*
    @Test
    void update() throws Exception {
        User user = userService.getUserById(1);
        user.setFirstname("Dmytro1");

        mockMvc.perform(
                        patch("/admin/user-info/1")
                                .param("text", "1")
                )
                .andExpect(status().isOk())
                .andExpect(redirectedUrl("/notes/1"));

        verify(userService).updateUser(1, user);
    }

    @Test
    void delete() throws Exception {
        mockMvc.perform(delete("/admin/user-info/1"))
                .andExpect(status().isNoContent())
                .andExpect(redirectedUrl("/admin/main"));
        verify(userService).deleteUserById(1);
    }

 */

