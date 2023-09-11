package com.springboot.shootformoney.admin.controller;

import com.springboot.shootformoney.admin.service.ConfigDeleteService;
import com.springboot.shootformoney.admin.service.ConfigInfoService;
import com.springboot.shootformoney.admin.service.ConfigSaveService;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Log
@Controller
@RequestMapping("/admin/config")
@RequiredArgsConstructor
public class ConfigController {

    private final ConfigSaveService saveService;
    private final ConfigInfoService infoService;
    private String code = "siteConfig";

    @GetMapping
    public String configGet(Model model) {
        commonProcess(model);
        ConfigForm configForm = infoService.get(code, ConfigForm.class);

        model.addAttribute("configForm", configForm == null ? new ConfigForm() : configForm);
        return "admin/config";
    }

    @PostMapping
    public String configPost(ConfigForm configForm, Model model) {
        commonProcess(model);
        saveService.configSave(code, configForm);
        model.addAttribute("message", "설정 저장 완료");
        return "admin/config";
    }

    private void commonProcess(Model model) {
        String title = "사이트 설정";
        String menuCode = "config";
        model.addAttribute("pageTitle", title);
        model.addAttribute("title", title);
        model.addAttribute("menuCode", menuCode);
    }
}
