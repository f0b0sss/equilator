package com.equilator.services;

import com.equilator.exceptions.InvalidOldPasswordException;
import com.equilator.exceptions.UserAlreadyExistException;
import com.equilator.exceptions.UserNotFoundException;
import com.equilator.models.user.User;
import com.equilator.testConfig.DataBaseTestConfig;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.function.Executable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

import static org.junit.jupiter.api.Assertions.*;


@ExtendWith(SpringExtension.class)
@ContextConfiguration(
        classes = {DataBaseTestConfig.class},
        loader = AnnotationConfigContextLoader.class)
@Sql(scripts = {"classpath:schema_users.sql", "classpath:data_users.sql"})
class UserServiceTest {

    @Autowired
    private UserService userService;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @AfterEach
    void drop(){
        jdbcTemplate.execute("DROP TABLE users cascade ");
    }

    @Test
    void addUser_throwUserAlreadyExistException_whenNewUserRegisteredWithEmailThatExistInDB() {
        User user2 = new User();
        user2.setFirstname("Dmytro");
        user2.setLastname("Zimin");
        user2.setEmail("superadmin@mail.com");
        user2.setPassword("111");

        assertThrows(UserAlreadyExistException.class, new Executable() {
            @Override
            public void execute() throws Throwable {
                userService.addUser(user2, passwordEncoder);
            }
        });
    }

    @Test
    @DirtiesContext
    void addUser() {
        User user2 = new User();
        user2.setFirstname("Dmytro");
        user2.setLastname("Zimin");
        user2.setEmail("superadmin1@mail.com");
        user2.setPassword("111");

        userService.addUser(user2, passwordEncoder);

        System.out.println(userService.getAllUsers("email"));

        assertEquals(2, userService.getAllUsers("email").size());
    }

    @Test
    void addUser_idIsSerial() {
        User user2 = new User();
        user2.setFirstname("Dmytro");
        user2.setLastname("Zimin");
        user2.setEmail("superadmin1@mail.com");
        user2.setPassword("111");

        userService.addUser(user2, passwordEncoder);

        assertEquals(2, userService.getUserById(2).getId());
    }

    @Test
    @DirtiesContext
    void addUser_correctConvertPassowrd_whenAddUser() {
        User user = new User();
        user.setFirstname("Dmytro");
        user.setLastname("Zimin");
        user.setEmail("superadmin1@mail.com");
        user.setPassword("111");

        userService.addUser(user, passwordEncoder);

        assertTrue(passwordEncoder.matches("111", userService.getUserById(2).getPassword()));
    }

    @Test
    void getAllUsers_getUsersList_whenDBContainSomeUsers() {
        assertEquals(1, userService.getAllUsers("email").size());
    }

    @Test
    void getUserByEmail_throwUserNotFoundException_whenUserWithEnteredEmailDoesNotExist() {
        assertThrows(UserNotFoundException.class, new Executable() {
            @Override
            public void execute() throws Throwable {
                userService.getUserByEmail("email@email.com");
            }
        });
    }

    @Test
    void getUserByEmail_whenUserWithEnteredEmailIsExist() {
        assertEquals("Dmytro", userService.getUserByEmail("superadmin@mail.com").getFirstname());
    }

    @Test
    void getUserById_throwUserNotFoundException_whenUserWithEnteredIdDoesNotExist() {
        assertThrows(UserNotFoundException.class, new Executable() {
            @Override
            public void execute() throws Throwable {
                userService.getUserById(2);
            }
        });
    }

    @Test
    void getUserById_whenUserWithEnteredIdlIsExist() {
        assertEquals("Dmytro", userService.getUserById(1).getFirstname());
    }

    @Test
    void deleteUserById() {
        User user = new User();
        user.setFirstname("Dmytro");
        user.setLastname("Zimin");
        user.setEmail("superadmin1@mail.com");
        user.setPassword("111");

        userService.addUser(user, passwordEncoder);

        assertEquals(2, userService.getAllUsers("email").size());

        userService.deleteUserById(2);

        assertEquals(1, userService.getAllUsers("email").size());
    }

    @Test
    @DirtiesContext
    void updateUser() {
        assertEquals("Dmytro", userService.getUserById(1).getFirstname());

        User user = userService.getUserById(1);
        user.setFirstname("Dmytro1");

        userService.updateUser(1, user);

        assertEquals("Dmytro1", userService.getUserById(1).getFirstname());
    }

    @Test
    @DirtiesContext
    void updatePassword_throwInvalidOldPasswordException_whenOldPAssIncorrect() {
        assertThrows(InvalidOldPasswordException.class, new Executable() {
            @Override
            public void execute() throws Throwable {
                userService.updatePassword("1112", "1113", passwordEncoder, 1);
            }
        });
    }

    @Test
    void updatePassword() {
        String newPass = "2222";

        userService.updatePassword("superadmin", newPass, passwordEncoder, 1);

        assertTrue(passwordEncoder.matches(newPass, userService.getUserById(1).getPassword()));
    }
}
