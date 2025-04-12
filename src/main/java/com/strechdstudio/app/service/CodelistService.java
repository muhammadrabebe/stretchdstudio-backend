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

    // Get all code lists
    public List<Codelist> getAllcodelists() {
        return codelistRepository.findAll();
    }

    // Get code list by ID
    public Optional<Codelist> getCodelistById(Integer codelistId) {
        Optional<Codelist> codelist = codelistRepository.findById(codelistId);
        if (codelist.isEmpty())
            throw new RuntimeException("Codelist does not exist.");
        else
            return codelist;
    }

    public Codelist createCodelist(Codelist codelist) {
        // Check if the codelist already exists by list name
        Optional<Codelist> existingCodelist = codelistRepository.findBylistName(codelist.getListName());
        if (existingCodelist.isPresent()) {
            throw new RuntimeException("Codelist '" + codelist.getListName() + "' already exists.");
        }

        LocalDateTime now = LocalDateTime.now();

        codelist.setAddDate(now);
        codelist.setEditDate(now);

        return codelistRepository.save(codelist);
    }

    public Codelist updateCodelist(Integer codelistId, Codelist updatedData) {
        return codelistRepository.findById(codelistId)
                .map(existing -> {
                    existing.setListName(updatedData.getListName());
                    existing.setDescription(updatedData.getDescription());
                    existing.setEditWho(updatedData.getEditWho());
                    existing.setEditDate(LocalDateTime.now());
                    return codelistRepository.save(existing);
                })
                .orElseThrow(() -> new RuntimeException("Codelist not found with ID: " + codelistId));
    }

    public void deleteCodelist(Integer codelistId) {
        Optional<Codelist> existing = codelistRepository.findById(codelistId);
        if (existing.isPresent()) {
            codelistRepository.deleteById(codelistId);
        } else {
            throw new RuntimeException("Codelist not found with ID: " + codelistId);
        }
    }


}
