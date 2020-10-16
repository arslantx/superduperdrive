package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import com.udacity.jwdnd.course1.cloudstorage.model.User;
import com.udacity.jwdnd.course1.cloudstorage.services.NoteService;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/note")
public class NoteController {

    private NoteService noteService;
    private UserService userService;

    public NoteController(NoteService noteService, UserService userService) {
        this.noteService = noteService;
        this.userService = userService;
    }
    
    @PostMapping
    public String createOrEditNote(Authentication auth, Model model, @ModelAttribute Note note) {
        Integer userid = userService.getCurrentUserId(auth);
        note.setUserid(userid);
        try {
            noteService.createOrEditNote(note);
            model.addAttribute("isChangeSuccess", true);
        } catch (Exception e) {
            model.addAttribute("hasGenericError", true);
            e.printStackTrace();
        }
        model.addAttribute("redirectTab", "nav-notes-tab");
        return "result";
    }

    @DeleteMapping("/{noteid}")
    public String deleteFile(Authentication auth, @PathVariable Integer noteid, Model model) {
        Integer userid = userService.getCurrentUserId(auth);
        try {
            noteService.deleteNote(noteid, userid);
            model.addAttribute("isChangeSuccess", true);
        } catch (Exception e) {
            model.addAttribute("hasGenericError", true);
            e.printStackTrace();
        }
        model.addAttribute("redirectTab", "nav-notes-tab");
        return "result";
    }
}
