package com.strechdstudio.app.controller;

import com.strechdstudio.app.dto.ApiResponse;
import com.strechdstudio.app.model.CodeLkup;
import com.strechdstudio.app.service.CodelistService;
import com.strechdstudio.app.service.CodelkupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    public ResponseEntity<ApiResponse<List<CodeLkup>>>getAllCodelkups(){
        List<CodeLkup> codeLkupList = codelkupService.getAllCodelkup();
        ApiResponse<List<CodeLkup>> response = new ApiResponse<>("success", 200, codeLkupList);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{listname}")
    public ResponseEntity<ApiResponse<List<CodeLkup>>>getCodelkupsByListname(@RequestParam("listname") String listname  ){
        List<CodeLkup> codeLkupList = codelkupService.findCodelkupByListname(listname);
        ApiResponse<List<CodeLkup>> response = new ApiResponse<>("success", 200, codeLkupList);
        return ResponseEntity.ok(response);
    }

    @PostMapping
    public CodeLkup saveCodelkup(@RequestBody  CodeLkup codeLkup){
        return codelkupService.saveCodelkup(codeLkup);
    }

    @PutMapping("/{codelkupId}")
    public CodeLkup updateCodelkup(@PathVariable Integer codelkupId,  CodeLkup codeLkup){
        return codelkupService.updateCodelkup(codeLkup);
    }

    @DeleteMapping("{codelkupId}")
    public void deleteCodelkup(@PathVariable Integer codelkupId){
         codelkupService.deleteCodelkup(codelkupId);
    }

//    @GetMapping("/user/{id}")
//    public ResponseEntity<ApiResponse<User>> getUserById(@PathVariable Long id) {
//        User user = new User(id, "John Doe", "john.doe@example.com");
//        ApiResponse<User> response = new ApiResponse<>("User retrieved successfully", 200, user);
//        return ResponseEntity.ok(response);
//    }

}
