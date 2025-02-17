package com.strechdstudio.app.controller;

import com.strechdstudio.app.model.Codelist;
import com.strechdstudio.app.service.CodelistService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/codelists")
public class CodelistController {

    @Autowired
    public CodelistService codelistService;

    public CodelistController(CodelistService codelistService) {
        this.codelistService = codelistService;
    }

    @GetMapping
    public List<Codelist> getAllCodelists(){
        return codelistService.getAllcodelists();
    }

    @GetMapping("/{codelistId}")
    public Optional<Codelist> getCodelistById(@PathVariable Integer codelistId){
        return codelistService.getCodelistById(codelistId);
    }

    @PostMapping
    public Codelist saveCodelist(@RequestBody Codelist codelist){
        return codelistService.saveCodelist(codelist);
    }

    // Update an existing customer
    @PutMapping("/{codelistId}")
    public Codelist updateCodelist(@PathVariable Integer codelistId, @RequestBody Codelist codelist) {
        return codelistService.updateCodelist(codelistId, codelist);
    }

    // Delete a customer
    @DeleteMapping("/{codelistId}")
    public void deleteCodelist(@PathVariable Integer codelistId) {
        codelistService.deleteCodelist(codelistId);
    }

}
