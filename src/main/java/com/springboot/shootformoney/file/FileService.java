package com.springboot.shootformoney.file;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@Service
public class FileService {

    private final FileRepository fileRepository;

    // 생성자 주입
    public FileService(FileRepository fileRepository) {
        this.fileRepository = fileRepository;
    }

    @Value("${file.upload-dir}")
    private String uploadDir; // application.properties에서 설정한 디렉토리 경로 가져오기

    public File uploadFile(MultipartFile multipartFile, Post post) throws IOException {
        // 업로드할 디렉토리 생성 (없는 경우)
        File directory = new File(uploadDir);
        if (!directory.exists()) {
            directory.mkdirs();
        }

        // 원본 파일명 가져오기
        String originalFileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());

        // 새로운 파일명 생성 (중복 방지)
        String fileName = UUID.randomUUID().toString() + "_" + originalFileName;

        // 지정된 디렉토리에 파일 저장하기
        Path filePath = Paths.get(uploadDir).resolve(fileName);
        multipartFile.transferTo(filePath);

        // 파일 엔티티 생성 및 저장하기
        File fileEntity = new File();
        fileEntity.setFName(originalFileName);
        fileEntity.setFPath(filePath.toString());
        fileEntity.setPost(post);

        return fileRepository.save(fileEntity);
    }
}
