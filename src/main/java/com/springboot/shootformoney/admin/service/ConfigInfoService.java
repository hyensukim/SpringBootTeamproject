package com.springboot.shootformoney.admin.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.springboot.shootformoney.admin.entity.ConfigEntity;
import com.springboot.shootformoney.admin.repository.ConfigRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class ConfigInfoService {

    private final ConfigRepository configRepository;

    public <T> T get(String code, Class<T> tClass) {
        return get(code, tClass, null);
    }

    public <T> T get(String code, TypeReference<T> type) {
        return get(code, null, type);
    }

    public <T> T get(String code, Class<T> tClass, TypeReference<T> typeReference) {
        try {
            ConfigEntity configEntity = configRepository.findById(code).orElse(null);
            if (configEntity == null || configEntity.getValue() == null || configEntity.getValue().isBlank()) {
                return null;
            }

            String value = configEntity.getValue();

            ObjectMapper om = new ObjectMapper();

            try {
                if (tClass == null) {
                    return om.readValue(value, typeReference);
                } else
                    return om.readValue(value, tClass);

            } catch (JsonProcessingException e) {
                throw new RuntimeException("객체를 JSON에서 변환하는 도중 오류가 발생했습니다", e);
            }

        } catch (Exception e) {
            throw new RuntimeException("설정 정보를 가져오는 도중 오류가 발생했습니다", e);
        }
//            T data = null;
//            try {
//                if (tClass == null) {
//                    data = om.readValue(value, typeReference);
//                } else data = om.readValue(value, tClass);
//            } catch (JsonProcessingException e) {
//                e.printStackTrace();
//            }
//
//            return data;
//        } catch (Exception e) {
//            return null;
//        }
    }
}
