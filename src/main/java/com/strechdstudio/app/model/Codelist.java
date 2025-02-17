package com.strechdstudio.app.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "codelist")
public class Codelist {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "codelistid")
    private Integer codeListId;

    @Column(name = "listname", nullable = false)
    private String listName;

    @Column(name = "description")
    private String description;

    @Column(name = "addwho")
    private String addWho;

    @Column(name = "adddate")
    private LocalDateTime addDate;

    @Column(name = "editwho")
    private String editWho;

    @Column(name = "editdate")
    private LocalDateTime editDate;

    public Integer getCodeListId() {
        return codeListId;
    }

    public void setCodeListId(Integer codeListId) {
        this.codeListId = codeListId;
    }

    public String getListName() {
        return listName;
    }

    public void setListName(String listName) {
        this.listName = listName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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
