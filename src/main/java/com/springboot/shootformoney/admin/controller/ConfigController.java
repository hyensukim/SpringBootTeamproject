//package com.springboot.shootformoney.admin.controller;
//
//import com.springboot.shootformoney.admin.service.ConfigDeleteService;
//import com.springboot.shootformoney.admin.service.ConfigInfoService;
//import com.springboot.shootformoney.admin.service.ConfigSaveService;
//import lombok.RequiredArgsConstructor;
//import lombok.extern.java.Log;
//import org.springframework.http.ResponseEntity;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.*;
//
//@Log
//@Controller
//@RequestMapping("/admin/config")
//@RequiredArgsConstructor
////@RestController
//public class ConfigController {
//
//    private final ConfigSaveService saveService;
//    private final ConfigInfoService infoService;
//    private String code = "siteConfig";
//
//    @GetMapping("/")
//    public ResponseEntity<ConfigForm> configGet(Model model) {
//        commonProcess(model);
//        ConfigForm configForm = infoService.get(code, ConfigForm.class);
//
//        // configForm -> null = 새로운 configForm, null != 이전의 configForm
//        return ResponseEntity.ok(configForm == null ? new ConfigForm() : configForm);
//    }
//
//    @PostMapping("/update/configForm")
//    public ResponseEntity<ConfigForm> configPost(@RequestBody ConfigForm configForm, Model model) {
//        //json 형식의 데이터 처리를 위해 @RequestBody 사용
//        commonProcess(model);
//        saveService.configSave(code, configForm);
//        model.addAttribute("message", "설정 저장 완료");
//
//        return ResponseEntity.ok(configForm);
//    }
//
//    @GetMapping("/")
//    public String configGet(Model model) {
//        commonProcess(model);
//        ConfigForm configForm = infoService.get(code, ConfigForm.class);
//
//        // configForm -> null = 새로운 configForm, null != 이전의 configForm
//        model.addAttribute("configForm", (configForm == null ? new ConfigForm() : configForm));
//
//        return "config";
//    }
//
//    @PostMapping("/update/configForm")
//    public String configPost(@ModelAttribute("configForm") ConfigForm configForm, Model model) {
//        commonProcess(model);
//        saveService.configSave(code, configForm);
//        model.addAttribute("message", "설정 저장 완료");
//
//        return "redirect:/admin/config/";
//    }
//
//    private void commonProcess(Model model) {
//        String title = "사이트 설정";
//        String menuCode = "config";
//
//        model.addAttribute("pageTitle", title); // 텝 이름 설정
//        model.addAttribute("title", title); // 사이트 내 title 이름 설정
//        model.addAttribute("menuCode", menuCode); // 프론트 - 네비게이션 바 구현시, 선택된 메뉴 on 표시 용도
//    }
//}