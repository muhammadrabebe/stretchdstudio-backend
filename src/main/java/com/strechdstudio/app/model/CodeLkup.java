package com.strechdstudio.app.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(
        name = "codelkup",
        uniqueConstraints = @UniqueConstraint(columnNames = {"listname", "code"})
)
public class CodeLkup {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "codelkupid")
    private Integer codeLkupId;

    @Column(name = "code")
    private String code;

    @Column(name = "description")
    private String description;

//    @ManyToOne
//    @JoinColumn(name = "listname", referencedColumnName = "listname")
//    private Codelist codelist;

    @Column(name = "listname")
    private String listname;

    @Column(name = "addwho")
    private String addWho;

    @Column(name = "adddate")
    private LocalDateTime addDate;

    @Column(name = "editwho")
    private String editWho;

    @Column(name = "editdate")
    private LocalDateTime editDate;

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

//    public Codelist getCodelist() {
//        return codelist;
//    }
//
//    public void setCodelist(Codelist codelist) {
//        this.codelist = codelist;
//    }

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

    public String getListname() {
        return listname;
    }

    public void setListname(String listname) {
        this.listname = listname;
    }
}
