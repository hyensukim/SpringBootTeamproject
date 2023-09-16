package com.springboot.shootformoney.file;

import com.springboot.shootformoney.file.File;
import com.springboot.shootformoney.file.FileService;

import com.springboot.shootformoney.post.Post;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/files")
public class FileController {

    private final FileService fileService;

    @Autowired
    public FileController(FileService fileService) {
        this.fileService = fileService;
    }

    @PostMapping("/upload")
    public ResponseEntity<?> uploadFile(@RequestParam("file") MultipartFile file, Post post) {
        if (file.isEmpty()) {
            return ResponseEntity.badRequest().body("No file selected");
        }

        try {
            File uploadedFile = fileService.saveFile(file, post);
            return ResponseEntity.ok().body("File uploaded successfully: " + uploadedFile.getFName());
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to upload the file: " + e.getMessage());
        }
    }

    @GetMapping("/download/{fileId}")
    public ResponseEntity<Resource> downloadFile(@PathVariable Integer fileId) {
        if (fileId == null || fileId <= 0) {
            return ResponseEntity.badRequest().body(null);
        }

        try {
            Resource resource = fileService.loadAsResource(fileId);

            if(resource == null){
                throw new Exception("Could not find the file!");
            }

            String fileName = resource.getFilename();

            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + fileName + "\"")
                    .body(resource);

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }
}
