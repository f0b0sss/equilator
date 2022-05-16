package com.equilator.controllers;

import exceptions.UserAlreadyExistException;
import models.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import services.UserService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@Controller
public class AccountController {

    @Autowired
    private HttpServletRequest request;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AuthenticationManager authenticationManager;

    private final UserService userService;


    public AccountController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/registration")
    public String showRegistrationForm(@ModelAttribute("user") User user) {
        return "registration";
    }

    @PostMapping("/registration")
    public String registerUserAccount(@Valid User user,
                                      BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "registration";
        }
        try {
            userService.addUser(user, passwordEncoder);
        } catch (UserAlreadyExistException e) {
            bindingResult.rejectValue("email", "user.email", "Email already exists");
            return "registration";
        }

        authenticateUserAndSetSession(user, request);

        return "redirect:/account";
    }

    @GetMapping("/account")
    public String account(Model model) {
        model.addAttribute("user", userService.getUserByEmail(
                SecurityContextHolder.getContext().getAuthentication().getName()));
        return "account";
    }

    public void authWithHttpServletRequest(User user, HttpServletRequest request) {
        try {
            request.login(user.getEmail(), user.getPassword());
        } catch (ServletException e) {
            System.out.println("Error while login ");
        }
    }

    private void authenticateUserAndSetSession(User user, HttpServletRequest request) {
        UsernamePasswordAuthenticationToken token = new
                UsernamePasswordAuthenticationToken(user.getEmail(), user.getPassword());

        request.getSession();

        token.setDetails(new WebAuthenticationDetails(request));

        Authentication authenticatedUser = authenticationManager.authenticate(token);

        SecurityContextHolder.getContext().setAuthentication(authenticatedUser);

        request.getSession().setAttribute(HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY, SecurityContextHolder.getContext());
    }

    @GetMapping("/edit/{id}")
    public String edit(Model model, @PathVariable("id") int id) {
        model.addAttribute("user", userService.getUserById(id));

        return "/edit";
    }

    @PatchMapping("/edit/{id}")
    public String update(@ModelAttribute("user") @Valid User user,
                         BindingResult bindingResult,
                         @PathVariable("id") int id) {

        System.out.println(user.getRole());
        System.out.println(user.getEmail());

        if (bindingResult.hasErrors()) {
            return "/edit";
        }

        userService.updateUser(id, user);

        return "redirect:/account";
    }

    @GetMapping("/password/{id}")
    public String password(Model model, @PathVariable("id") int id) {
        model.addAttribute("user", userService.getUserById(id));

        return "/password";
    }

    @PatchMapping("/password/{id}")
    public String updatePass(@ModelAttribute("user") @Valid User user, BindingResult bindingResult,
                             @RequestParam("old_password") String old_password,
                             @RequestParam("new_password") String new_password,
                             @PathVariable("id") int id) {
        System.out.println(user.getRole());
        System.out.println(user.getEmail());
        System.out.println(old_password);
        System.out.println(new_password);

        if (!isOldPassCorrect(old_password, id)){
            System.out.println("1");
            bindingResult.rejectValue("password", "user.password", "Invalid Old Password");
            return "/password";
        }
        if (bindingResult.hasErrors()) {
            System.out.println(bindingResult.getAllErrors());
            return "/password";
        }

        return "redirect:/account";
    }

    private boolean isOldPassCorrect(String password, int id) {
        return passwordEncoder.matches(password, userService.getUserById(id).getPassword());
    }


}
