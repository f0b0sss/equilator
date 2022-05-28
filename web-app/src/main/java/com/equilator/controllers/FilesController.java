package com.equilator.controllers;

import com.equilator.models.UploadForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import com.equilator.services.FileService;

@Controller
public class FilesController {
    @Autowired
    private FileService fileService;
    @Autowired
    private UploadForm uploadForm;

    @PostMapping("/save-result")
    @PreAuthorize("hasAuthority('access:user')")
    public ResponseEntity<InputStreamResource> saveResult(@RequestParam("fileName") String fileName) {

        return fileService.safe(fileName);
    }

    @GetMapping("/uploadFile")
    public String uploadOneFileHandler(Model model) {
        model.addAttribute("uploadForm", uploadForm);

        return "uploadFile";
    }

    @PostMapping("/uploadFile")
    public String uploadOneFileHandlerPOST(@ModelAttribute("myUploadForm") UploadForm uploadForm) {
        try {
            fileService.openFile(uploadForm);
        } catch (NullPointerException e){
            e.getMessage();
            return "redirect:/uploadFile";
        }

        return "redirect:/calculator";
    }
}
