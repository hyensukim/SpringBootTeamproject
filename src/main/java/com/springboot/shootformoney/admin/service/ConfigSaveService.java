package com.springboot.shootformoney.admin.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.springboot.shootformoney.admin.entity.ConfigEntity;
import com.springboot.shootformoney.admin.repository.ConfigRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
// 관리자 페이지 저장
public class ConfigSaveService {

    private final ConfigRepository configRepository;

    public <T> void configSave(String code, T t){
        ConfigEntity configEntity = configRepository.findById(code).orElseGet(ConfigEntity::new);

        ObjectMapper om = new ObjectMapper();
        String value = null;
        try{
            value = om.writeValueAsString(t);
        } catch (JsonProcessingException e ) {
            e.printStackTrace();
        }

        configEntity.setCode(code);
        configEntity.setValue(value);

        configRepository.saveAndFlush(configEntity);
    }
}
