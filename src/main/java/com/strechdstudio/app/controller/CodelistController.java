package com.strechdstudio.app.controller;

import com.strechdstudio.app.dto.ApiResponse;
import com.strechdstudio.app.model.Codelist;
import com.strechdstudio.app.service.CodelistService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/codelists")
public class CodelistController {

    private final CodelistService codelistService;

    @Autowired
    public CodelistController(CodelistService codelistService) {
        this.codelistService = codelistService;
    }

    // Get all code lists
    @GetMapping
    public ResponseEntity<ApiResponse<List<Codelist>>> getAllCodeLists() {
        List<Codelist> codelists = codelistService.getAllcodelists();
        ApiResponse<List<Codelist>> response = new ApiResponse<>("Code lists fetched successfully", 200, codelists);
        return ResponseEntity.ok(response);
    }

    // Get code list by ID
    @GetMapping("/{codeListId}")
    public ResponseEntity<ApiResponse<Optional<Codelist>>> getCodeListById(@PathVariable Integer codeListId) {
        Optional<Codelist> codelist = codelistService.getCodelistById(codeListId);
        ApiResponse<Optional<Codelist>> response = new ApiResponse<>("Success", 200, codelist);
        return ResponseEntity.ok(response);
    }

    // 📥 Create a new codelist
    @PostMapping
    public ResponseEntity<ApiResponse<Codelist>> createCodelist(@RequestBody Codelist codelist) {
        Codelist created = codelistService.createCodelist(codelist);
        ApiResponse<Codelist> response = new ApiResponse<>("Codelist " + codelist.getListName() + "has been created successfully." , 200, created);
        return ResponseEntity.ok(response);
    }

    // ✏️ Update a codelist
    @PutMapping("/{codelistId}")
    public ResponseEntity<ApiResponse<Codelist>> updateCodelist(
            @PathVariable Integer codelistId,
            @RequestBody Codelist codelist) {
        Codelist updated = codelistService.updateCodelist(codelistId, codelist);
        ApiResponse<Codelist> response = new ApiResponse<>("Updated", 200, updated);
        return ResponseEntity.ok(response);
    }

    // Delete a code list
    @DeleteMapping("/{codelistId}")
    public ResponseEntity<ApiResponse<String>> deleteCodelist(@PathVariable Integer codelistId) {
        codelistService.deleteCodelist(codelistId);
        ApiResponse<String> response = new ApiResponse<>("Codelist has been deleted successfully", 200, "Codelist deleted");
        return ResponseEntity.ok(response);
    }

}
