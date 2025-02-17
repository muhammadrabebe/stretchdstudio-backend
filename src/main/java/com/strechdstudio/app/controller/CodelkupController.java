package com.strechdstudio.app.controller;

import com.strechdstudio.app.model.CodeLkup;
import com.strechdstudio.app.service.CodelistService;
import com.strechdstudio.app.service.CodelkupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/codelkups")
public class CodelkupController {

    @Autowired
    public CodelkupService codelkupService;

    public CodelkupController(CodelkupService codelkupService) {
        this.codelkupService = codelkupService;
    }

    @GetMapping
    public List<CodeLkup> getAllCodelkups(){
        return codelkupService.getAllCodelkup();
    }
}
