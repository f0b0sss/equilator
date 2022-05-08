package com.equilator.controllers;

public class registrationController {

    /*
    @GetMapping("/user/registration")
    public String showRegistrationForm(WebRequest request, Model model) {
        User user = new User();
        model.addAttribute("user", user);
        return "registration";
    }

    @PostMapping("/user/registration")
    public ModelAndView registerUserAccount(
            @ModelAttribute("user") @Valid User user,
            HttpServletRequest request,
            Errors errors) {

        try {
            org.springframework.security.core.userdetails.User registered = userService.registerNewUserAccount(user);
        } catch (UserAlreadyExistException uaeEx) {
            mav.addObject("message", "An account for that username/email already exists.");
            return mav;
        }

        return new ModelAndView("successRegister", "user", user);
    }
    }

     */



}
