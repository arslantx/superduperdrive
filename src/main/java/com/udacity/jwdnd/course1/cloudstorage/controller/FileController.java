package com.udacity.jwdnd.course1.cloudstorage.controller;

import java.io.ByteArrayInputStream;
import javax.servlet.http.HttpServletResponse;
import com.udacity.jwdnd.course1.cloudstorage.model.File;
import com.udacity.jwdnd.course1.cloudstorage.model.User;
import com.udacity.jwdnd.course1.cloudstorage.services.FileService;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping("/file")
public class FileController {

    private final FileService fileService;
    private final UserService userService;

    public FileController(FileService storageService, UserService userService) {
        this.fileService = storageService;
        this.userService = userService;
    }

    @GetMapping("/{fileId}")
    public ResponseEntity<Resource> getFile(HttpServletResponse response,
            @PathVariable Integer fileId) {
        File file = fileService.getFile(fileId);
        InputStreamResource resource =
                new InputStreamResource(new ByteArrayInputStream(file.getFiledata()));
        return ResponseEntity.ok().contentType(MediaType.parseMediaType(file.getContenttype()))
                .header(HttpHeaders.CONTENT_DISPOSITION,
                        "attachment; filename=" + file.getFilename())
                .body(resource);
    }
    
    @PostMapping
    public String uploadFile(Authentication authentication, @RequestParam("fileUpload") MultipartFile file, Model model) {
        Integer currentUserid = getCurrentUserId(authentication);
        fileService.storeFile(file, currentUserid);
        model.addAttribute("files", fileService.getFileNames(currentUserid));
        return "home";
    }

    private Integer getCurrentUserId(Authentication authentication) {
        String username = authentication.getName();
        User user = userService.getUser(username);
        return user.getUserid();
    }
}
