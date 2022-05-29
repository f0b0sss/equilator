package com.equilator.repository;

import com.equilator.DAO.DBUtils;
import com.equilator.models.user.User;
import org.springframework.beans.factory.annotation.Autowired;

import java.sql.SQLException;
import java.util.List;

public class UserRepositoryImpl implements UserRepository{

    @Autowired
    private DBUtils dbUtils;

    @Override
    public List<User> getAllUsers(String order) {
        try {
            return dbUtils.getAllUsers(order);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public List<User> getUsers(String role) {
        try {
            return dbUtils.getUsers(role).stream().filter(user -> user.getRole().equals(role)).toList();
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public User getUserById(int id) {
        try {
            return dbUtils.getUserById(id);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public User getUserByEmail(String email) {
        try {
            return dbUtils.getUserByEmail(email);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public void addUser(User user) {
        try {
            dbUtils.addUsers(user);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void deleteUserById(int id) {
        try {
            dbUtils.deleteUserById(id);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void updateUser(int id, User user) {
        dbUtils.updateUser(id, user);
    }

    @Override
    public void updatePassword(String new_password, int id) {
        dbUtils.updatePassword(new_password, id);
    }
}


