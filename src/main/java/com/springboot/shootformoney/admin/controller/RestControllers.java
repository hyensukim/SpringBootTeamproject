package com.springboot.shootformoney.admin.controller;

import com.springboot.shootformoney.admin.service.ConfigInfoService;
import com.springboot.shootformoney.admin.service.ConfigSaveService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin/config/post")
@RequiredArgsConstructor
public class RestControllers {

    private final ConfigSaveService saveService;
    private final ConfigInfoService infoService;
    private String code = "siteConfig";

    @PostMapping
    public ResponseEntity<ConfigForm> configPost(ConfigForm configForm, Model model) {
//        commonProcess(model);
//        System.out.println(siteTitle);
        saveService.configSave(code, configForm);
//        model.addAttribute("message", "설정 저장 완료");
        return ResponseEntity.status(HttpStatus.OK).body(configForm);
//        return null;
    }

}
