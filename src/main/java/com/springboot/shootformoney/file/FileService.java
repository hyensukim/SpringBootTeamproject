package com.springboot.shootformoney.file;

import com.springboot.shootformoney.file.FileRepository;

import com.springboot.shootformoney.post.Post;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;

@Service
public class FileService {

    private final FileRepository fileRepository;

    private final Path root = Paths.get("uploads"); // 실제 파일이 저장될 경로

    @Autowired
    public FileService(FileRepository fileRepository) {
        this.fileRepository = fileRepository;
    }

    public File saveFile(MultipartFile multipartFile, Post post) throws IOException { //업로드
        String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
        Path filePath = root.resolve(fileName); // 실제 파일이 저장될 경로와 이름

        Files.copy(multipartFile.getInputStream(), filePath); // MultipartFile 객체의 내용을 해당 경로에 복사하여 파일 생성

        File newFile = new File();
        newFile.setFName(fileName);
        newFile.setPost(post);
        newFile.setFPath(filePath.toString());

        return fileRepository.save(newFile);
    }

    public Optional<File> getFile(Integer fileId) {
        return fileRepository.findById(fileId);
    }

    public Resource loadAsResource(Integer fileId) throws Exception { //다운로드
        Optional<File> optionalFile = getFile(fileId);

        if (optionalFile.isPresent()) {
            Path filePath = Paths.get(optionalFile.get().getFPath());
            Resource resource = new UrlResource(filePath.toUri());

            if (resource.exists() || resource.isReadable()) {
                return resource;
            } else {
                throw new Exception("Could not read the file!");
            }
        } else {
            throw new Exception("Could not find the file!");
        }
    }
}
