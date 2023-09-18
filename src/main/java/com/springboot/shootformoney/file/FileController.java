package com.springboot.shootformoney.file;

import com.springboot.shootformoney.file.File;
import com.springboot.shootformoney.file.FileService;
import com.springboot.shootformoney.post.Post;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Controller
@RequestMapping("/files")
public class FileController {

    private final FileService fileService;

    @Autowired
    public FileController(FileService fileService) {
        this.fileService = fileService;
    }

    @PostMapping("/upload")
    public String uploadFile(@RequestParam("file") MultipartFile file, Post post, Model model) {
        if (file.isEmpty()) {
            model.addAttribute("pageTitle", "No file selected");
            return "error";
        }

        try {
            File uploadedFile = fileService.saveFile(file, post);
            model.addAttribute("pageTitle", "File uploaded successfully: " + uploadedFile.getFName());
            return "success";
        } catch (IOException e) {
            model.addAttribute("pageTitle", "Failed to upload the file: " + e.getMessage());
            return "error";
        }
    }

    @GetMapping("/download/{fileId}")
    public ResponseEntity<Resource> downloadFile(@PathVariable Integer fileId, Model model) {
        if (fileId == null || fileId <= 0) {
            return ResponseEntity.badRequest().body(null);
        }

        try {
            Resource resource = fileService.loadAsResource(fileId);

            if(resource == null){
                throw new Exception("Could not find the file!");
            }

            String fileName = resource.getFilename();

            model.addAttribute("pageTitle", "파일 다운로드");

            // download.html 뷰에 pageTitle이라는 이름으로 데이터를 전달합니다.

            // 하지만 이렇게 작성할 경우 파일을 직접 다운로드 받지 못하고 페이지에 표시되는 문제가 있습니다.

            // 따라서 실제 사용을 위해서는 적절한 방법을 찾아야 합니다.

            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + fileName + "\"")
                    .body(resource);

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }
}
