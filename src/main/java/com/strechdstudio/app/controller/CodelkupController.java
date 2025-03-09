package com.strechdstudio.app.controller;

import com.strechdstudio.app.dto.ApiResponse;
import com.strechdstudio.app.dto.CodelkupDTO;
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
    public ResponseEntity<ApiResponse<List<CodelkupDTO>>>getAllCodelkups(){
        List<CodelkupDTO> codeLkupList = codelkupService.getAllCodeLookups();
        ApiResponse<List<CodelkupDTO>> response = new ApiResponse<>("success", 200, codeLkupList);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{listname}")
    public ResponseEntity<ApiResponse<List<CodelkupDTO>>> getCodelkupsByListname(@PathVariable String listname) {
        // Retrieve the CodelkupDTO list from the service
        List<CodelkupDTO> codelkupDTOList = codelkupService.findCodelkupByListname(listname);

        // Create the ApiResponse object
        ApiResponse<List<CodelkupDTO>> response = new ApiResponse<>("success", 200, codelkupDTOList);

        // Return the response with the CodelkupDTO list
        return ResponseEntity.ok(response);
    }

    @PostMapping
    public CodeLkup saveCodelkup(@RequestBody  CodeLkup codeLkup){
        return codelkupService.saveCodelkup(codeLkup);
    }

    @PutMapping("/update")
    public ResponseEntity<CodelkupDTO> updateCodelkup(@RequestBody CodelkupDTO codeLkupDTO) {
        CodeLkup updatedCodelkup = codelkupService.updateCodelkup(codeLkupDTO);
        return ResponseEntity.ok(convertToDto(updatedCodelkup));
    }

    @DeleteMapping("{codelkupId}")
    public void deleteCodelkup(@PathVariable Integer codelkupId){
         codelkupService.deleteCodelkup(codelkupId);
    }

    private CodelkupDTO convertToDto(CodeLkup codeLkup) {
        CodelkupDTO dto = new CodelkupDTO();
        dto.setCodeLkupId(codeLkup.getCodeLkupId());
        dto.setCode(codeLkup.getCode());
        dto.setListname(codeLkup.getCodelist().getListName());
        dto.setDescription(codeLkup.getDescription());
        dto.setAddWho(codeLkup.getAddWho());
        dto.setAddDate(codeLkup.getAddDate());
        dto.setEditWho(codeLkup.getEditWho());
        dto.setEditDate(codeLkup.getEditDate());
        return dto;
    }

}
