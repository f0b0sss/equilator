package com.equilator.controllers;

import com.equilator.models.UploadForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import com.equilator.services.FileService;

@Controller
@RequestMapping("/calculator")
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

        return "calculator/uploadFile";
    }

    @PostMapping("/uploadFile")
    public String uploadOneFileHandlerPOST(@ModelAttribute("myUploadForm") UploadForm uploadForm) {
        try {
            fileService.openFile(uploadForm);
        } catch (NullPointerException e){
            e.getMessage();
            return "redirect:/calculator/uploadFile";
        }

        return "redirect:/calculator/calculator";
    }
}
