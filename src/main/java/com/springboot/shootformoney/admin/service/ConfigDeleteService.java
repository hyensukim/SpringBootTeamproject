package com.springboot.shootformoney.admin.service;

import com.springboot.shootformoney.admin.entity.ConfigEntity;
import com.springboot.shootformoney.admin.repository.ConfigRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ConfigDeleteService {
    private final ConfigRepository configRepository;

    public void configDelete(String code) {
        ConfigEntity configEntity = configRepository.findById(code).orElse(null);
        if(configEntity == null){
            return;
        }

        configRepository.delete(configEntity);
        configRepository.flush();
    }
}
