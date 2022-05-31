package com.equilator.controllers;

import com.equilator.models.user.User;
import com.equilator.services.UserService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/admin")
public class AdminController {
    private static Logger logger = LogManager.getLogger(AdminController.class);

    @Autowired
    private UserService userService;

    @GetMapping("/main")
    @PreAuthorize("hasAuthority('access:admin')")
    public String admin(Model model) {
        logger.debug("Open admin page and show all users");
        model.addAttribute("users", userService.getAllUsers("email"));
        return "admin/main";
    }

    @GetMapping("/main/{sort}")
    @PreAuthorize("hasAuthority('access:admin')")
    public String adminSort(Model model, @PathVariable("sort") String order) {
        logger.debug("Sort users by " + order);
        model.addAttribute("users", userService.getAllUsers(order));
        return "admin/main";
    }

    @GetMapping("/user-info/{id}")
    @PreAuthorize("hasAuthority('access:admin')")
    public String show(@PathVariable("id") int id, Model model) {
        logger.debug("Open account information about user with email " + userService.getUserById(id).getEmail());
        model.addAttribute("user", userService.getUserById(id));
        return "admin/user-info";
    }

    @PatchMapping("/user-info/{id}")
    @PreAuthorize("hasAuthority('access:admin')")
    public String update(@ModelAttribute("user") User user, @PathVariable("id") int id) {
         userService.updateUser(id, user);
        logger.info("Successful updated user data");
        return "redirect:/admin/main";
    }

    @DeleteMapping("/user-info/{id}")
    @PreAuthorize("hasAuthority('access:admin')")
    public String delete(@PathVariable("id") int id) {
        userService.deleteUserById(id);
        logger.info("Successful deleted user with email " + userService.getUserById(id).getEmail());
        return "redirect:/admin/main";
    }


}
