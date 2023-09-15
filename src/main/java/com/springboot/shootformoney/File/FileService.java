package com.springboot.shootformoney.File;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@Service
public class FileService {

    @Value("${file.upload-dir}") // application.properties에서 설정한 디렉토리 경로 가져오기
    private String uploadDir;

    public String uploadFile(MultipartFile file) throws IOException {
        // 업로드할 디렉토리 생성 (없는 경우)
        File directory = new File(uploadDir);
        if (!directory.exists()) {
            directory.mkdirs();
        }

        // 원본 파일명 가져오기
        String originalFileName = StringUtils.cleanPath(file.getOriginalFilename());

        // 새로운 파일명 생성 (중복 방지)
        String fileName = UUID.randomUUID().toString() + "_" + originalFileName;

        // 지정된 디렉토리에 파일 저장하기
        Path filePath = Paths.get(uploadDir).resolve(fileName);
        file.transferTo(filePath);

        return filePath.toString();
    }
}

//# 업로드할 디렉토리 경로 설정 (예시: /uploads)
//        file.upload-dir=/uploads/
// 업로드할 디렉터리 경로와 관련된 설정 값을 application.properties에 추가