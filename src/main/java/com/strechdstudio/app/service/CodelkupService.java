package com.strechdstudio.app.service;

import com.strechdstudio.app.dto.CodelkupDTO;
import com.strechdstudio.app.model.CodeLkup;
import com.strechdstudio.app.model.Codelist;
import com.strechdstudio.app.repository.CodeLkupRepository;
import com.strechdstudio.app.repository.CodelistRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CodelkupService {

    @Autowired
    public CodeLkupRepository codeLkupRepository;

    @Autowired
    public CodelistRepository codelistRepository;

    // get all codelkups
    public List<CodelkupDTO> getAllCodeLookups() {
        return codeLkupRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    // Method to find Codelkup by listname
    public List<CodelkupDTO> findCodelkupByListname(String listname) {
        // Find the Codelkups by listname (using the method in the repository)
        List<CodeLkup> codeLkupList = codeLkupRepository.findByCodelist_ListName(listname);

        // Convert the CodeLkup list to CodelkupDTO list
        List<CodelkupDTO> codelkupDTOList = codeLkupList.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());

        return codelkupDTOList;
    }

//    // Get codeLkup by ID
//    public Optional<CodeLkup> getCodelkupById(Integer codelkupId) {
//        Optional<CodeLkup> codeLkup = codeLkupRepository.findById(codelkupId);
//        if (codeLkup.isEmpty())
//            throw new RuntimeException("CodeLkup does not exist.");
//        else
//            return codeLkup;
//    }

    public CodelkupDTO getCodelkupById(Integer id) {
        CodeLkup entity = codeLkupRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("CodeLkup not found with ID: " + id));

        CodelkupDTO dto = new CodelkupDTO();
        dto.setCodeLkupId(entity.getCodeLkupId());
        dto.setCode(entity.getCode());
        dto.setDescription(entity.getDescription());
        dto.setListname(entity.getCodelist().getListName());
        dto.setAddWho(entity.getAddWho());
        dto.setAddDate(entity.getAddDate());
        dto.setEditWho(entity.getEditWho());
        dto.setEditDate(entity.getEditDate());

        return dto;
    }


    // Save a new codelkup
    public CodeLkup saveCodelkup(CodelkupDTO codelkupDTO) {
        // Check if the listname exists
        Codelist codelist = codelistRepository.findBylistName(codelkupDTO.getListname())
                .orElseThrow(() -> new RuntimeException("The listname given doesn't exist."));

        // Check if the combination of listname and code already exists
        Optional<CodeLkup> existingCodelkup = codeLkupRepository.findByCodelist_ListNameAndCode(
                codelist.getListName(), codelkupDTO.getCode()
        );

        if (existingCodelkup.isPresent()) {
            throw new RuntimeException("The combination of listname and code already exists.");
        }

        // Create a new CodeLookup object and set values
        CodeLkup newCodeLkup = new CodeLkup();
        LocalDateTime currentDate = LocalDateTime.now();
        newCodeLkup.setCode(codelkupDTO.getCode());
        newCodeLkup.setDescription(codelkupDTO.getDescription());
        newCodeLkup.setCodelist(codelist);
        newCodeLkup.setAddWho(codelkupDTO.getAddWho());
        newCodeLkup.setAddDate(currentDate);
        newCodeLkup.setEditWho(codelkupDTO.getEditWho());
        newCodeLkup.setEditDate(currentDate);

        return codeLkupRepository.save(newCodeLkup);
    }

    // Update an existing codelkup
    public CodeLkup updateCodelkup(Integer codelkupId, CodelkupDTO codeLkupDTO) {
        if (codeLkupDTO.getListname() == null) {
            throw new IllegalArgumentException("Listname cannot be null.");
        }

        // Fetch required entities with validation
        Codelist codelist = codelistRepository.findBylistName(codeLkupDTO.getListname())
                .orElseThrow(() -> new RuntimeException("The listname given doesn't exist."));

        CodeLkup existingCodelkup = codeLkupRepository.findById(codelkupId)
                .orElseThrow(() -> new RuntimeException("CodeLkup not found with ID: " + codelkupId));

        // Check for duplicate (same listname + code) excluding itself
        Optional<CodeLkup> duplicate = codeLkupRepository
                .findByCodelist_ListNameAndCode(codeLkupDTO.getListname(), codeLkupDTO.getCode());

        if (duplicate.isPresent() && !duplicate.get().getCodeLkupId().equals(existingCodelkup.getCodeLkupId())) {
            throw new RuntimeException("A CodeLkup with the same listname and code already exists.");
        }

        // Map fields from DTO
        existingCodelkup.setCode(codeLkupDTO.getCode());
        existingCodelkup.setCodelist(codelist);
        existingCodelkup.setDescription(codeLkupDTO.getDescription());
        existingCodelkup.setEditWho(codeLkupDTO.getEditWho());
        existingCodelkup.setEditDate(LocalDateTime.now());

        return codeLkupRepository.save(existingCodelkup);
    }

    // Delete a codelkup by ID
    public void deleteCodelkup(Integer codelkupId) {
        CodeLkup codeLkup = codeLkupRepository.findById(codelkupId)
                .orElseThrow(() -> new RuntimeException("CodeLkup not found with ID: " + codelkupId));

        codeLkupRepository.delete(codeLkup);
    }


//    // Delete a codelkup by ID
//    public void deleteCodelkup(Integer codelkupId) {
//        if (codeLkupRepository.existsById(codelkupId)) {
//            Optional<CodeLkup> deletedCodelkup = codeLkupRepository.findById(codelkupId);
//            codeLkupRepository.deleteById(codelkupId);
//            throw new RuntimeException("Codelkup " + deletedCodelkup.get().getCode()+ " has been deleted");
//        } else {
//            throw new RuntimeException("Codelkup not found with ID: " + codelkupId);
//        }
//    }

    private CodelkupDTO convertToDTO(CodeLkup codeLookup) {
        return new CodelkupDTO(
                codeLookup.getCodeLkupId(),
                codeLookup.getCode(),
                codeLookup.getDescription(),
                codeLookup.getCodelist().getListName(),
                codeLookup.getAddWho(),
                codeLookup.getAddDate(),
                codeLookup.getEditWho(),
                codeLookup.getEditDate()
        );
    }
}
