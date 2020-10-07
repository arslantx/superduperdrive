package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.User;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/signup")
public class SignupController {
    
    private final UserService userService;

    public SignupController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping()
    public String signupView(@ModelAttribute User user, Model model) {
        model.addAttribute("signupSuccess", false);
        model.addAttribute("signupError", null);
        return "signup";
    }

    @PostMapping()
    public String signupUser(@ModelAttribute User user, Model model) {
        String signupError = null;
        if (!userService.isUsernameAvailable(user.getUsername())) {
            signupError = "User is already taken";
        }
        if (signupError == null) {
            int rowsAdded = userService.createUser(user);
            if (rowsAdded <= 0) {
                signupError = "Error during signup. Please try again later.";
            }
        }

        if (signupError == null) {
            model.addAttribute("signupSuccess", true);
        } else {
            model.addAttribute("signupError", signupError);
        }
        return "signup";
    }
}
