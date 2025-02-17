package com.strechdstudio.app.repository;

import com.strechdstudio.app.model.CodeLkup;
import com.strechdstudio.app.model.Codelist;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CodelistRepository extends JpaRepository<Codelist,Integer> {
    // This method will generate a query to find Codelist by the 'listname' field
    Codelist findBylistName(String listname);
}
