package com.strechdstudio.app.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "instructors")
public class Instructor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "instructorid")
    private Integer instructorId;

    @Column(name = "firstname", nullable = false)
    private String firstName;

    @Column(name = "lastname", nullable = false)
    private String lastName;

    @Column(name = "email", unique = true, nullable = false)
    private String email;

    @Column(name = "phonenumber" , unique = true, nullable = false)
    private String phoneNumber;

    @Column(name = "bio", columnDefinition = "TEXT")
    private String bio;

    @Column(name = "addwho")
    private String addWho;

    @Column(name = "adddate")
    private LocalDateTime addDate;

    @Column(name = "editwho")
    private String editWho;

    @Column(name = "editdate")
    private LocalDateTime editDate;

    @ManyToOne
    @JoinColumn(name = "statusid", referencedColumnName = "codelkupid")
    private CodeLkup statusid; // Assuming status is stored in codelkup

    @ManyToOne
    @JoinColumn(name = "specializationid", referencedColumnName = "codelkupid")
    private CodeLkup specializationid; // Assuming specialization is stored in codelkup
}
