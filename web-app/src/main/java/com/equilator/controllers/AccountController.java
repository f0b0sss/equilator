package com.equilator.controllers;

import com.equilator.exceptions.InvalidOldPasswordException;
import com.equilator.models.Error;
import com.equilator.models.user.User;
import com.equilator.services.UserService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

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



    @GetMapping("/account")
    public String account(Model model) {
        logger.debug("Open user account page");

        model.addAttribute("user", userService.getUserByEmail(
                SecurityContextHolder.getContext().getAuthentication().getName()));
        return "user/account";
    }

    @GetMapping("/edit/{id}")
    public String edit(Model model, @PathVariable("id") int id) {
        logger.debug("Open user edit page");

        model.addAttribute("user", userService.getUserById(id));

        return "/user/edit";
    }

    @PatchMapping("/edit/{id}")
    public String update(@ModelAttribute("user") @Valid User user,
                         BindingResult bindingResult,
                         @PathVariable("id") int id) {

        if (bindingResult.hasErrors()) {
            logger.warn("Invalid data when edit user data");

            return "user/edit";
        }

        userService.updateUser(id, user);

        logger.info("Successful updated user account");
        return "redirect:/user/account";
    }

    @GetMapping("/password/{id}")
    public String password(Model model, @PathVariable("id") int id) {
        logger.debug("Open user password edit page");

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
            logger.warn("Invalid format new password");
            bindingResult.rejectValue("password", "user.password", "New password must be min 3 symbols");
            return "/user/password";
        }
        try {
            userService.updatePassword(old_password, new_password, passwordEncoder, id);
        }catch (InvalidOldPasswordException e){
            logger.warn(e.getMessage());
            bindingResult.rejectValue("password", "user.password", e.getMessage());
            return "/user/password";
        }

        logger.info("Successful updated user password");
        return "redirect:/user/account";
    }


}
