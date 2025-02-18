package com.strechdstudio.app.service;

import com.strechdstudio.app.model.CodeLkup;
import com.strechdstudio.app.model.Codelist;
import com.strechdstudio.app.repository.CodeLkupRepository;
import com.strechdstudio.app.repository.CodelistRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class CodelkupService {

    @Autowired
    public CodeLkupRepository codeLkupRepository;

    @Autowired
    public CodelistRepository codelistRepository;

    // Get all codelkup
    public List<CodeLkup> getAllCodelkup() {
        List<CodeLkup> codelkups = codeLkupRepository.findAll();
        return codelkups;
    }

    // Method to find Codelkup by listname
    public List<CodeLkup> findCodelkupByListname(String listname) {
        return codeLkupRepository.findByListname(listname);
    }

    // Get codeLkup by ID
    public Optional<CodeLkup> getCodelkupById(Integer codelkupId) {
        Optional<CodeLkup> codeLkup = codeLkupRepository.findById(codelkupId);
        if (codeLkup.isEmpty())
            throw new RuntimeException("CodeLkup does not exist.");
        else
            return codeLkup;
    }

    // Save a new codelkup
    public CodeLkup saveCodelkup(CodeLkup codeLkup) {
        // check if the listname exists
        Optional<Codelist> codelist = Optional.ofNullable(codelistRepository.findBylistName(codeLkup.getListname()));
        if(!codelist.isPresent()){
            throw new RuntimeException("The listname given doesn't exist.");
        }

        // check if the combination is already existing
        Optional<CodeLkup> existingCodelkup = codeLkupRepository
                .findByListnameAndCode(codeLkup.getListname(), codeLkup.getCode());
        if (existingCodelkup.isPresent()) {
            throw new RuntimeException("The combination of listname and code already exists.");
        } else {
            CodeLkup currentCodelkup = new CodeLkup();
            LocalDateTime currentDate = LocalDateTime.now();
            currentCodelkup.setCode(codeLkup.getCode());
            currentCodelkup.setListname(codeLkup.getListname());
            currentCodelkup.setDescription(codeLkup.getDescription());
            currentCodelkup.setAddWho(codeLkup.getAddWho());
            currentCodelkup.setAddDate(currentDate);
            currentCodelkup.setEditWho(codeLkup.getEditWho());
            currentCodelkup.setEditDate(currentDate);

            return codeLkupRepository.save(currentCodelkup);
        }
    }

    // Save a new codelkup
    public CodeLkup updateCodelkup(CodeLkup codeLkup) {
        // check if the listname exists
        Optional<Codelist> codelist = Optional.ofNullable(codelistRepository.findBylistName(codeLkup.getListname()));
        if(!codelist.isPresent()){
            throw new RuntimeException("The listname given doesn't exist.");
        }

        // check if the combination is already existing
        Optional<CodeLkup> existingCodelkup = codeLkupRepository
                .findByListnameAndCode(codeLkup.getListname(), codeLkup.getCode());
        if (existingCodelkup.isPresent()) {
            throw new RuntimeException("The combination of listname and code already exists.");
        } else {
            CodeLkup currentCodelkup = new CodeLkup();
            LocalDateTime currentDate = LocalDateTime.now();
            currentCodelkup.setCode(codeLkup.getCode());
            currentCodelkup.setListname(codeLkup.getListname());
            currentCodelkup.setDescription(codeLkup.getDescription());
            currentCodelkup.setAddWho(codeLkup.getAddWho());
            currentCodelkup.setAddDate(currentDate);
            currentCodelkup.setEditWho(codeLkup.getEditWho());
            currentCodelkup.setEditDate(currentDate);

            return codeLkupRepository.save(currentCodelkup);
        }
    }

    // Delete a codelkup by ID
    public void deleteCodelkup(Integer codelkupId) {
        if (codeLkupRepository.existsById(codelkupId)) {
            Optional<CodeLkup> deletedCodelkup = codeLkupRepository.findById(codelkupId);
            codeLkupRepository.deleteById(codelkupId);
            throw new RuntimeException("Codelkup " + deletedCodelkup.get().getCode()+ " has been deleted");
        } else {
            throw new RuntimeException("Codelkup not found with ID: " + codelkupId);
        }
    }
}
