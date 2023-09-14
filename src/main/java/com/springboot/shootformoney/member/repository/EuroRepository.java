package com.springboot.shootformoney.member.repository;

import com.springboot.shootformoney.member.entity.Euro;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface EuroRepository extends JpaRepository<Euro, Long> {
    Euro findBymNo(Long mNo);
}
