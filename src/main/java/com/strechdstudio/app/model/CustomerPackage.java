package com.strechdstudio.app.model;

import jakarta.persistence.*;

@Entity
@Table(name = "customerPackage")
public class CustomerPackage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long customerPackageId;

    private String packageName;
    private Integer duration;
}
