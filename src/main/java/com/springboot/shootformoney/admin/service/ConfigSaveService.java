package com.springboot.shootformoney.admin.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.springboot.shootformoney.admin.entity.ConfigEntity;
import com.springboot.shootformoney.admin.repository.ConfigRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/*
 관리자 페이지 저장
 */
@Service
@RequiredArgsConstructor
@Transactional
public class ConfigSaveService {

    private final ConfigRepository configRepository;

    public <T> void configSave(String code, T t){
        ConfigEntity configEntity = configRepository.findById(code).orElseGet(ConfigEntity::new);

        ObjectMapper om = new ObjectMapper();
        String value;
        try{
            value = om.writeValueAsString(t);
        } catch (JsonProcessingException e ) {
//            e.printStackTrace();
            throw new RuntimeException("객체를 JSON으로 변환하는 도중 오류가 발생했습니다.", e);
        }

        configEntity.setCode(code);
        configEntity.setValue(value);

        configRepository.saveAndFlush(configEntity);
    }
}
