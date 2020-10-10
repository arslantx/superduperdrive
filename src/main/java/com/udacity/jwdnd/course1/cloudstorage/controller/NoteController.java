package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import com.udacity.jwdnd.course1.cloudstorage.model.User;
import com.udacity.jwdnd.course1.cloudstorage.services.NoteService;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/note")
public class NoteController {

    private NoteService noteService;
    private UserService userService;

    public NoteController(NoteService noteService, UserService userService) {
        this.noteService = noteService;
        this.userService = userService;
    }

    @GetMapping("/{noteid")
    public Note getNote(@PathVariable Integer noteid) {
        return null;
    }
    
    @PostMapping
    public ModelAndView createOrEditNote(Authentication authentication, Model model, @ModelAttribute Note note) {
        String currentUsername = authentication.getName();
        User user = userService.getUser(currentUsername);
        note.setUserid(user.getUserid());
        noteService.createOrEditNote(note);
        return new ModelAndView("redirect:/home");
    }

    @DeleteMapping("/{noteid}")
    public ModelAndView deleteFile(@PathVariable Integer noteid) {
        noteService.deleteNote(noteid);
        return new ModelAndView("redirect:/home");
    }
}
