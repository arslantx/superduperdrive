package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.Credential;
import com.udacity.jwdnd.course1.cloudstorage.model.User;
import com.udacity.jwdnd.course1.cloudstorage.services.CredentialService;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/credential")
public class CredentialController {
    
    private CredentialService credentialService;
    private UserService userService;

    public CredentialController(CredentialService credentialService, UserService userService) {
        this.credentialService = credentialService;
        this.userService = userService;
    }

    @PostMapping
    public ModelAndView createOrEditCredential(Authentication authentication, Model model, @ModelAttribute Credential credential) {
        String currentUsername = authentication.getName();
        User user = userService.getUser(currentUsername);
        credential.setUserid(user.getUserid());
        credentialService.createOrEditCredential(credential);
        return new ModelAndView("redirect:/home");
    }

    @DeleteMapping("/{credentialid}")
    public ModelAndView deleteCredential(@PathVariable Integer credentialid) {
        credentialService.deleteCredential(credentialid);
        return new ModelAndView("redirect:/home");
    }
}
