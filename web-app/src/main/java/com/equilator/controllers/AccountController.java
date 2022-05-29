package com.equilator.controllers;

import com.equilator.exceptions.InvalidOldPasswordException;
import com.equilator.exceptions.UserAlreadyExistException;
import com.equilator.models.Error;
import com.equilator.models.user.User;
import com.equilator.services.UserService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
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

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;


@Controller
@RequestMapping("/user")
public class AccountController {
    private static Logger logger = LogManager.getLogger(AccountController.class);

    @Autowired
    private HttpServletRequest request;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserService userService;

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

        return "redirect:/user/account";
    }

    @GetMapping("/account")
    public String account(Model model) {
        model.addAttribute("user", userService.getUserByEmail(
                SecurityContextHolder.getContext().getAuthentication().getName()));
        return "user/account";
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

        return "/user/edit";
    }

    @PatchMapping("/edit/{id}")
    public String update(@ModelAttribute("user") @Valid User user,
                         BindingResult bindingResult, Model model,
                         @PathVariable("id") int id) {

        if (bindingResult.hasErrors()) {
            return "user/edit";
        }

        userService.updateUser(id, user);

        return "redirect:/user/account";
    }

    @GetMapping("/password/{id}")
    public String password(Model model, @PathVariable("id") int id) {
        model.addAttribute("user", userService.getUserById(id));
        model.addAttribute("error", new Error());

        return "/user/password";
    }

    @PatchMapping("/password/{id}")
    public String updatePass(@ModelAttribute("user") @Valid User user, BindingResult bindingResult,
                             @RequestParam("old_password") String old_password,
                             @RequestParam("new_password") String new_password,
                             @PathVariable("id") int id) {
        if (bindingResult.hasErrors()) {
            bindingResult.rejectValue("password", "user.password", "New password must be min 3 symbols");
            return "/user/password";
        }

        try {
            userService.updatePassword(old_password, new_password, passwordEncoder, id);
        }catch (InvalidOldPasswordException e){
            bindingResult.rejectValue("password", "user.password", e.getMessage());
            return "/user/password";
        }

        return "redirect:/user/account";
    }


}
