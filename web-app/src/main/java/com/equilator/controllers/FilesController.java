package com.equilator.controllers;

import com.equilator.models.UploadForm;
import com.equilator.services.FileService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/calculator")
public class FilesController {
    private static Logger logger = LogManager.getLogger(FilesController.class);
    @Autowired
    private FileService fileService;
    @Autowired
    private UploadForm uploadForm;

    @PostMapping("/save-result")
    @PreAuthorize("hasAuthority('access:user')")
    public ResponseEntity<InputStreamResource> saveResult(@RequestParam("fileName") String fileName) {
        logger.debug("Select button to import calculation information");
        return fileService.safe(fileName);
    }

    @GetMapping("/uploadFile")
    public String uploadOneFileHandler(Model model) {
        logger.debug("Select button to export calculation information");

        model.addAttribute("uploadForm", uploadForm);

        return "calculator/uploadFile";
    }

    @PostMapping("/uploadFile")
    public String uploadOneFileHandlerPOST(@ModelAttribute("myUploadForm") UploadForm uploadForm) {
        try {
            logger.info("Import file");
            fileService.openFile(uploadForm);
        } catch (NullPointerException e){
            logger.warn("No one file selected");
            e.getMessage();
            return "redirect:/calculator/uploadFile";
        }

        return "redirect:/calculator/calculator";
    }
}
