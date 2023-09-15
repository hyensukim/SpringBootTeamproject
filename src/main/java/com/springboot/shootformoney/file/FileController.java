package com.springboot.shootformoney.file;

import com.springboot.shootformoney.file.File;
import com.springboot.shootformoney.file.FileService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/files")
public class FileController {

    private final FileService fileService;
    private final PostRepository postRepository; // 게시글 Repository

    public FileController(FileService fileService, PostRepository postRepository) {
        this.fileService= fileService;
        this.postRepository= postRepository;
    }

    @PostMapping("/upload/{postingId}")
    public ResponseEntity<String> uploadFile(@PathVariable("postingId") Integer pno,
                                             @RequestParam("file") MultipartFile multipartFile) {
        try {
            Optional<Post> optionalPosting= postRepository.findById(pno);
            if (!optionalPosting.isPresent()) {
                return new ResponseEntity<>("게시글을 찾을 수 없습니다.", HttpStatus.NOT_FOUND);
            }

            Post post = optionalPosting.get();

            File uploadedFile= fileService.uploadFile(multipartFile, post);

            return new ResponseEntity<>(uploadedFile.getFNo().toString(), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
