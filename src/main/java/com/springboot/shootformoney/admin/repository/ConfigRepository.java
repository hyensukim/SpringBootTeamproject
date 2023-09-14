package com.springboot.shootformoney.admin.repository;

import com.springboot.shootformoney.admin.entity.ConfigEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ConfigRepository extends JpaRepository<ConfigEntity, String> {
}
