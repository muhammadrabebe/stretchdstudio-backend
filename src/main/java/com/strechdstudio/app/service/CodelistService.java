package com.strechdstudio.app.service;

import com.strechdstudio.app.model.Codelist;
import com.strechdstudio.app.repository.CodelistRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class CodelistService {

    @Autowired
    public CodelistRepository codelistRepository;

    // Get all codelists
    public List<Codelist> getAllcodelists() {
        List<Codelist> codelists = codelistRepository.findAll();
        return codelists;
    }

    // Get codelist by ID
    public Optional<Codelist> getCodelistById(Integer codelistId) {
        Optional<Codelist> codelist = codelistRepository.findById(codelistId);
        if (codelist.isEmpty())
            throw new RuntimeException("Codelist does not exist.");
        else
        return codelist;
    }

    // Save a new codelist
    public Codelist saveCodelist(Codelist codelist) {
        // Check if the codelist exists
        Optional<Codelist> existingCodelist = Optional.ofNullable(codelistRepository.findBylistName(codelist.getListName()));
        if (existingCodelist.isPresent()) {
            throw new RuntimeException("Codelist " + codelist.getListName() + " is already exist ");
        } else {
            Codelist currentCodelist = new Codelist();
            LocalDateTime currentDate = LocalDateTime.now();
            // Update fields here (you can add more logic like checking for changes)
            currentCodelist.setListName(codelist.getListName());
            currentCodelist.setDescription(codelist.getDescription());
            currentCodelist.setAddWho(codelist.getAddWho());
            currentCodelist.setAddDate(currentDate);
            currentCodelist.setEditWho(codelist.getEditWho());
            currentCodelist.setEditDate(currentDate);
            return codelistRepository.save(currentCodelist);
        }
    }

    // Update an existing codelist
    public Codelist updateCodelist(Integer codelistId, Codelist updateCodelist) {
        // Check if the codelist exists
        Optional<Codelist> existingCodelist = codelistRepository.findById(codelistId);
        if (existingCodelist.isPresent()) {
            Codelist currentCodelist = existingCodelist.get();
            LocalDateTime currentDate = LocalDateTime.now();

            currentCodelist.setListName(updateCodelist.getListName());
            currentCodelist.setDescription(updateCodelist.getDescription());
            currentCodelist.setEditWho(updateCodelist.getEditWho());
            currentCodelist.setEditDate(currentDate);
            return codelistRepository.save(currentCodelist);
        } else {
            throw new RuntimeException("Codelist not found with id " + codelistId);
        }
    }

    // Delete a codelist by ID
    public void deleteCodelist(Integer codelistId) {
        if (codelistRepository.existsById(codelistId)) {
            Optional<Codelist> deletedCodelist = codelistRepository.findById(codelistId);
            codelistRepository.deleteById(codelistId);
            throw new RuntimeException("Codelist " + deletedCodelist.get().getListName() + " has been deleted");
        } else {
            throw new RuntimeException("Codelist not found with ID: " + codelistId);
        }
    }

//    // Example of method to get customers by membership type (can add more business logic)
//    public List<Codelist> getCod(String membershipTypeCode) {
//        CodeLkup membershipType = codeLkupRepository.findByCode(membershipTypeCode); // Assuming 'membershipTypeCode' matches the code in codeLkup
//        if (membershipType != null) {
//            return customerRepository.findByMembershipType(membershipType);  // Assuming a method in the repository to find by membership type
//        } else {
//            throw new RuntimeException("Invalid membership type code: " + membershipTypeCode);
//        }
//    }
}
