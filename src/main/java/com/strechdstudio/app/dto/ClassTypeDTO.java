package com.strechdstudio.app.dto;

import com.strechdstudio.app.model.ClassType;

import java.util.UUID;

public class ClassTypeDTO {
    private UUID typeId;
    private String imageUrl;
    private String type;

    public ClassTypeDTO() {
    }

    public ClassTypeDTO(ClassType classType) {
        this.typeId = classType.getTypeId();
        this.imageUrl = classType.getImageUrl();
        this.type = classType.getType();
    }

    public UUID getTypeId() {
        return typeId;
    }

    public void setTypeId(UUID typeId) {
        this.typeId = typeId;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
