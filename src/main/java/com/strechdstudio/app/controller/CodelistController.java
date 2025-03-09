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

    // Create a new code list
//    @PostMapping
//    public ResponseEntity<ApiResponse<Codelist>> createCodeList(@RequestBody Codelist codelist) {
//        Codelist savedCodeList = codelistService.saveCodeList(codelist);
//
//        if (savedCodeList == null) {
//            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
//                    .body(new ApiResponse<>("Failed to save code list", 400, null));
//        }
//
//        ApiResponse<Codelist> response = new ApiResponse<>("Code list saved successfully", 200, savedCodeList);
//        return ResponseEntity.ok(response);
//    }

    // Update an existing code list
//    @PutMapping("/{codeListId}")
//    public ResponseEntity<ApiResponse<Codelist>> updateCodeList(@PathVariable Integer codeListId, @RequestBody Codelist codeListDTO) {
//        Codelist udpatedCodelist = codelistService.updateCodeList(codeListId, codeListDTO);
//        ApiResponse<Codelist> response = new ApiResponse<>("Code list updated successfully", 200, udpatedCodelist);
//        return ResponseEntity.ok(response);
//    }

    // Delete a code list
//    @DeleteMapping("/{codeListId}")
//    public ResponseEntity<ApiResponse<Void>> deleteCodeList(@PathVariable Integer codeListId) {
//        try {
//            codelistService.deleteCodeList(codeListId);
//            ApiResponse<Void> response = new ApiResponse<>("Code list deleted successfully", 200, null);
//            return ResponseEntity.ok(response);
//        } catch (Exception e) {
//            ApiResponse<Void> response = new ApiResponse<>("Failed to delete code list: " + e.getMessage(), 400, null);
//            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
//        }
//    }

}
