package com.strechdstudio.app.repository;

import com.strechdstudio.app.model.CodeLkup;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CodeLkupRepository extends JpaRepository<CodeLkup, Integer> {
    // This method will generate a query to find CodeLkup by the 'code' field
    CodeLkup findByCode(String code);

    List<CodeLkup> findByListname(String listname);

    Optional<CodeLkup> findByCodelistListnameAndCode(String listname, String code);
}
