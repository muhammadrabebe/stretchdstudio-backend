package com.strechdstudio.app.dto;

import com.strechdstudio.app.model.Codelist;
import jakarta.persistence.*;

import java.time.LocalDateTime;

public class CodelkupDTO {

    private Integer codeLkupId;
    private String code;
    private String description;
    private String listname;
    private String addWho;
    private LocalDateTime addDate;
    private String editWho;
    private LocalDateTime editDate;

    public CodelkupDTO() {
    }

    public CodelkupDTO(Integer codeLkupId, String code, String description, String listname, String addWho, LocalDateTime addDate, String editWho, LocalDateTime editDate) {
        this.codeLkupId = codeLkupId;
        this.code = code;
        this.description = description;
        this.listname = listname;
        this.addWho = addWho;
        this.addDate = addDate;
        this.editWho = editWho;
        this.editDate = editDate;
    }

    public Integer getCodeLkupId() {
        return codeLkupId;
    }

    public void setCodeLkupId(Integer codeLkupId) {
        this.codeLkupId = codeLkupId;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getListname() {
        return listname;
    }

    public void setListname(String listname) {
        this.listname = listname;
    }

    public String getAddWho() {
        return addWho;
    }

    public void setAddWho(String addWho) {
        this.addWho = addWho;
    }

    public LocalDateTime getAddDate() {
        return addDate;
    }

    public void setAddDate(LocalDateTime addDate) {
        this.addDate = addDate;
    }

    public String getEditWho() {
        return editWho;
    }

    public void setEditWho(String editWho) {
        this.editWho = editWho;
    }

    public LocalDateTime getEditDate() {
        return editDate;
    }

    public void setEditDate(LocalDateTime editDate) {
        this.editDate = editDate;
    }
}
