package com.udacity.jwdnd.course1.cloudstorage.controller;

import java.io.ByteArrayInputStream;
import com.udacity.jwdnd.course1.cloudstorage.model.File;
import com.udacity.jwdnd.course1.cloudstorage.services.FileService;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

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
    public ResponseEntity<Resource> getFile(@PathVariable Integer fileId, Authentication auth) {
        Integer userid = userService.getCurrentUserId(auth);
        File file = fileService.getFile(fileId, userid);
        InputStreamResource resource =
                new InputStreamResource(new ByteArrayInputStream(file.getFiledata()));
        return ResponseEntity.ok().contentType(MediaType.parseMediaType(file.getContenttype()))
                .header(HttpHeaders.CONTENT_DISPOSITION,
                        "attachment; filename=" + file.getFilename())
                .body(resource);
    }
    
    @PostMapping
    public ModelAndView uploadFile(Authentication auth, @RequestParam("fileUpload") MultipartFile file) {
        Integer userid = userService.getCurrentUserId(auth);
        fileService.storeFile(file, userid);
        return new ModelAndView("redirect:/home");
    }

    @DeleteMapping("/{fileId}")
    public ModelAndView deleteFile(Authentication auth, @PathVariable Integer fileId) {
        Integer userid = userService.getCurrentUserId(auth);
        fileService.deleteFile(fileId, userid);
        return new ModelAndView("redirect:/home");
    }
}
