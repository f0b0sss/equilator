package services;

import exceptions.UserAlreadyExistException;
import models.user.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import repository.UserRepository;

import java.util.List;
import java.util.Optional;

@Service
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return SecurityUser.fromUser(getUserByEmail(email));
    }

    public List<User> getAllUsers(String order) {
        List<User> users = userRepository.getAllUsers(order);
        if (users.isEmpty()){
            throw new UsernameNotFoundException("user list does not exist");
        }
        return users;
    }


    public List<User> getUsers(String role) {
        List<User> users = userRepository.getUsers(role);
        if (users.isEmpty()){
            throw new UsernameNotFoundException("user list does not exist");
        }
        return users;
    }

    public User getUserById(int id) {
        Optional<User> user = Optional.ofNullable(userRepository.getUserById(id));
        return user.orElseThrow(() -> new UsernameNotFoundException("user does not exist by id"));
    }


    public User getUserByEmail(String email) {
        Optional<User> user = Optional.ofNullable(userRepository.getUserByEmail(email));
        return user.orElse(null);
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

    public void updatePassword(String new_password, PasswordEncoder passwordEncoder, int id) {
        String pass = passwordEncoder.encode(new_password);
        userRepository.updatePassword(pass, id);
    }
}
