package com.equilator.services;

import com.equilator.exceptions.InvalidOldPasswordException;
import com.equilator.exceptions.UserAlreadyExistException;
import com.equilator.exceptions.UserNotFoundException;
import com.equilator.models.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.equilator.repository.UserRepository;

import java.util.List;
import java.util.Optional;

@Service
public class UserService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return SecurityUser.fromUser(getUserByEmail(email));
    }

    public List<User> getAllUsers(String order) {
        List<User> users = userRepository.getAllUsers(order);

        return users;
    }

    public User getUserById(int id) {
        Optional<User> user = Optional.ofNullable(userRepository.getUserById(id));
        return user.orElseThrow(() -> new UserNotFoundException("User with this id does not exist"));
    }

    public User getUserByEmail(String email) {
        Optional<User> user = Optional.ofNullable(userRepository.getUserByEmail(email));
        return user.orElseThrow(() -> new UserNotFoundException("User with this email does not exist"));
    }


    public void addUser(User user, PasswordEncoder passwordEncoder) {
        if(checkIfUserExist(user.getEmail())){
            throw new UserAlreadyExistException();
        }

        user.setPassword(passwordEncoder.encode(user.getPassword()));

        userRepository.addUser(user);
    }

    public void deleteUserById(int id) {
        userRepository.deleteUserById(id);
    }

    private boolean checkIfUserExist(String email) {
        return userRepository.getUserByEmail(email) !=null ? true : false;
    }


    public void updateUser(int id, User user) {
        userRepository.updateUser(id, user);
    }

    public void updatePassword(String old_password, String new_password, PasswordEncoder passwordEncoder, int id) {

        if (!isOldPassCorrect(old_password, id, passwordEncoder)){
            throw new InvalidOldPasswordException("Invalid Old Password");
        }

        String pass = passwordEncoder.encode(new_password);
        userRepository.updatePassword(pass, id);
    }

    private boolean isOldPassCorrect(String old_password, int id, PasswordEncoder passwordEncoder) {
        return passwordEncoder.matches(old_password, userRepository.getUserById(id).getPassword());
    }
}
