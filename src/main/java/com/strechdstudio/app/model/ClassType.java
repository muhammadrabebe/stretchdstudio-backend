package com.strechdstudio.app.model;

import jakarta.persistence.*;
import java.util.UUID;

@Entity
@Table(name = "classType")
public class ClassType {

    @Id
    @GeneratedValue
    private UUID typeId;

    @Column(unique = true, nullable = false)
    private String type;

    private String imageUrl;

    // Getters and Setters
    public UUID getTypeId() {
        return typeId;
    }

    public void setTypeId(UUID typeId) {
        this.typeId = typeId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
