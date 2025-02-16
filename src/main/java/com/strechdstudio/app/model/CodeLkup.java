package com.strechdstudio.app.model;

import jakarta.persistence.*;

@Entity
@Table(name = "codeLkup")
public class CodeLkup {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer codeLkupId;

    private String code;
    private String description;

    // Getters and Setters
}
