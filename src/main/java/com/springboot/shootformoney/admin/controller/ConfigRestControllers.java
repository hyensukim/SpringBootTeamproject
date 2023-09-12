package com.springboot.shootformoney.admin.controller;

import com.springboot.shootformoney.admin.service.ConfigInfoService;
import com.springboot.shootformoney.admin.service.ConfigSaveService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin/config")
@RequiredArgsConstructor
public class ConfigRestControllers {

    private final ConfigSaveService saveService;
    private final ConfigInfoService infoService;
    private String code = "siteConfig";

    @GetMapping
    public ResponseEntity<ConfigForm> configGet(Model model) {
        ConfigForm configForm = infoService.get(code, ConfigForm.class);

        // configForm -> null = 새로운 configForm, null != 이전의 configForm
        return ResponseEntity.ok(configForm == null ? new ConfigForm() : configForm);
    }

    @PostMapping
    public ResponseEntity<ConfigForm> configPost(@RequestBody ConfigForm configForm) {
        //json 형식의 데이터 처리를 위해 @RequestBody 사용
        saveService.configSave(code, configForm);

        return ResponseEntity.ok(configForm);
    }

}
