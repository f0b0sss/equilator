package repository;


import models.user.User;

import java.util.List;

public interface UserRepository {

    List<User> getAllUsers(String order);

    List<User> getUsers(String role);

    User getUserById(int id);

    User getUserByEmail(String email);

    void addUser(User user);

    void deleteUserById(int id);

    void updateUser(int id, User user);
}
