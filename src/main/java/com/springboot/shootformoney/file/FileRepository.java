package com.springboot.shootformoney.file;

import org.springframework.data.jpa.repository.JpaRepository;

public interface FileRepository extends JpaRepository<File, Integer> {
    // 추가적인 파일 관련 메서드 정의 가능
}
