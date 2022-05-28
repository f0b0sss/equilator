package com.equilator.services;

import com.equilator.models.user.User;
import com.equilator.repository.UserRepository;
import com.equilator.testConfig.DataBaseTestConfig;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.function.Executable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

import static org.junit.jupiter.api.Assertions.*;


@ExtendWith(SpringExtension.class)
@ContextConfiguration(
        classes = {DataBaseTestConfig.class},
        loader = AnnotationConfigContextLoader.class)
@Sql(scripts = {"classpath:schema.sql", "classpath:data.sql"})
class UserServiceTest {

    @Autowired
    private UserRepository userRepository;


    @Test
    void getAllUsers_getUsersList_whenDBContainSomeUsers() {
        assertEquals(1, userRepository.getAllUsers("email").size());
    }

    @Test
    void getUserByEmail_returnNull_whenUserWithEnteredEmailDoesNotExist() {
        assertEquals(null, userRepository.getUserByEmail("mail@mail.com"));
    }
    @Test
    void getUserByEmail_returnUser_whenUserWithEnteredEmailIsExist() {
        assertEquals("Dmytro1", userRepository.getUserByEmail("superadmin@mail.com").getFirstname());
    }

    @Test
    void getUserById_returnNull_whenUserWithEnteredEmailDoesNotExist() {
        assertEquals(null, userRepository.getUserById(2));
    }

    @Test
    void getUserById_returnUser_whenUserWithEnteredIdlIsExist() {
        assertEquals("Dmytro1", userRepository.getUserById(1).getFirstname());
    }

    @Test
    void addUser_throwUserAlreadyExistException_whenNewUserRegisteredWithEmailThatExistInDB() {
        User user = new User();
        user.setId(1l);
        user.setFirstname("Dmytro");
        user.setLastname("Zimin");
        user.setEmail("superadmin@mail.com");
        user.setPassword("111");

        assertThrows(DuplicateKeyException.class, new Executable() {
            @Override
            public void execute() throws Throwable {
                userRepository.addUser(user);
            }
        });
    }




    @Test
    void addUser() {
        User user = new User();
        user.setId(1l);
        user.setFirstname("Dmytro");
        user.setLastname("Zimin");
        user.setEmail("superadmin@mail.com");
        user.setPassword("$2a$12$BMCe7.ytHFwyLxkOIYAmXOmit3GqLTZ90kDH5VAgId30Y/a6KMVNq");

        userRepository.addUser(user);

        User user1 = userRepository.getUserById(1);

        assertNotNull(user1);
        assertEquals(user.getEmail(), user.getEmail());
    }

    /*

    @Test
    void addUser() {
     //   UserRepository userRepository = new UserRepositoryImpl(dbUtils);

       User user = new User();
       user.setId(1l);
       user.setFirstname("Dmytro");
       user.setLastname("Zimin");
       user.setEmail("superadmin@mail.com");
       user.setPassword("$2a$12$BMCe7.ytHFwyLxkOIYAmXOmit3GqLTZ90kDH5VAgId30Y/a6KMVNq");
       user.setRole(Role.valueOf("SUPERADMIN"));
       user.setStatus(Status.valueOf("ACTIVE"));

       userRepository.addUser(user);

       assertEquals(1, userRepository.getAllUsers("mail").size());
    }

     */





}