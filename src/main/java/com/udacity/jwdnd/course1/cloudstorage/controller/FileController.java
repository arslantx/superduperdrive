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
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
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
    public String uploadFile(Authentication auth, @RequestParam("fileUpload") MultipartFile file, Model model) {
        Integer userid = userService.getCurrentUserId(auth);
        try {
            if (fileService.isExistingFile(file, userid)) {
                model.addAttribute("hasSpecificError", true);
                model.addAttribute("errorMsg", "File with same name already exists. File not uploaded.");
            } else {
                fileService.storeFile(file, userid);
                model.addAttribute("isChangeSuccess", true);
            }
        } catch (Exception e) {
            model.addAttribute("hasGenericError", true);
            e.printStackTrace();
        }
        model.addAttribute("redirectTab", "");
        return "result";
    }

    @DeleteMapping("/{fileId}")
    public String deleteFile(Authentication auth, @PathVariable Integer fileId, Model model) {
        Integer userid = userService.getCurrentUserId(auth);
        try {
            fileService.deleteFile(fileId, userid);
            model.addAttribute("isChangeSuccess", true);
        } catch (Exception e) {
            model.addAttribute("hasGenericError", true);
            e.printStackTrace();
        }
        model.addAttribute("redirectTab", "");
        return "result";
    }
}
