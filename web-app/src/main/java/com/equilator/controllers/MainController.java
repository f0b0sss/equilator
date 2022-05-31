package com.equilator.controllers;

import com.equilator.exceptions.UserAlreadyExistException;
import com.equilator.models.user.User;
import com.equilator.services.UserService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;

@Controller
@RequestMapping("/")
public class MainController {
    private static Logger logger = LogManager.getLogger(MainController.class);

    @Autowired
    private PasswordEncoder passwordEncoder;

    private final UserService userService;

    public MainController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/index")
    public String index() {
        logger.debug("Open Home page");
        return "index";
    }

    @GetMapping("/about")
    public String about() {
        logger.debug("Open page About");
        return "about";
    }

    @GetMapping("/contact")
    public String contact() {
        logger.debug("Open page Contact");
        return "contact";
    }

    @GetMapping("/login")
    public String login() {
        logger.debug("Open page Login");
        return "login";
    }

    @GetMapping("/logout")
    public String logout() {
        logger.debug("Logout");
        return "logout";
    }

    @GetMapping("/registration")
    public String showRegistrationForm(@ModelAttribute("user") User user) {
        logger.debug("Open Registration Form");
        return "registration";
    }

    @PostMapping("/registration")
    public String registerUserAccount(@Valid User user,
                                      BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            logger.warn("Invalid Registered data");
            return "registration";
        }
        try {
            userService.addUser(user, passwordEncoder);
        } catch (UserAlreadyExistException e) {
            bindingResult.rejectValue("email", "user.email", "Email already exists");
            logger.warn("Invalid Registered data, Email already exists");
            return "registration";
        }

        logger.info("Successful registered");

        return "redirect:/user/account";
    }

}
