package com.digital.school.model;

import jakarta.persistence.*;
import org.hibernate.annotations.Filter;

@Entity
@Table(name = "registration_documents")
@Filter(name = "schoolFilter", condition = "school_id = :schoolId")
public class RegistrationDocument extends AuditableEntity {

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "school_id", nullable = false)
    private School school;
    
    @Column(nullable = false)
    private String name;
    
    @Column(columnDefinition = "TEXT")
    private String description;
    
    @Column(name = "document_type", nullable = false)
    private String documentType;
    
    @Column(name = "is_mandatory")
    private boolean mandatory = true;
    
    @Column(name = "file_types")
    private String[] fileTypes = {"pdf", "jpg", "png"};
    
    @Column(name = "max_size_mb")
    private Integer maxSizeMb = 5;

    // Getters and setters

    public School getSchool() { return school; }

    public void setSchool(School school) { this.school = school; }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDocumentType() {
        return documentType;
    }

    public void setDocumentType(String documentType) {
        this.documentType = documentType;
    }

    public boolean isMandatory() {
        return mandatory;
    }

    public void setMandatory(boolean mandatory) {
        this.mandatory = mandatory;
    }

    public String[] getFileTypes() {
        return fileTypes;
    }

    public void setFileTypes(String[] fileTypes) {
        this.fileTypes = fileTypes;
    }

    public Integer getMaxSizeMb() {
        return maxSizeMb;
    }

    public void setMaxSizeMb(Integer maxSizeMb) {
        this.maxSizeMb = maxSizeMb;
    }

    // Méthodes utilitaires
    public boolean acceptsFileType(String fileType) {
        if (fileTypes == null || fileTypes.length == 0) return true;
        
        for (String type : fileTypes) {
            if (type.equalsIgnoreCase(fileType)) {
                return true;
            }
        }
        return false;
    }

    public boolean isFileSizeValid(long sizeInBytes) {
        if (maxSizeMb == null) return true;
        return sizeInBytes <= (maxSizeMb * 1024 * 1024L);
    }
}