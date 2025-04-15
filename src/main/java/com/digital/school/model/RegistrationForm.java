package com.digital.school.model;

import jakarta.persistence.*;
import org.hibernate.annotations.Filter;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "registration_forms")
@Filter(name = "schoolFilter", condition = "school_id = :schoolId")
public class RegistrationForm extends AuditableEntity {

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "school_id", nullable = false)
    private School school;
    
    @Column(nullable = false)
    private String name;
    
    @Column(columnDefinition = "TEXT")
    private String description;
    
    @ManyToOne
    @JoinColumn(name = "step_id")
    private RegistrationStep step;
    
    @Column(name = "is_mandatory")
    private boolean mandatory = true;
    
    @OneToMany(mappedBy = "form", cascade = CascadeType.ALL, orphanRemoval = true)
    @OrderBy("orderIndex")
    private Set<RegistrationFormField> fields = new HashSet<>();

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

    public RegistrationStep getStep() {
        return step;
    }

    public void setStep(RegistrationStep step) {
        this.step = step;
    }

    public boolean isMandatory() {
        return mandatory;
    }

    public void setMandatory(boolean mandatory) {
        this.mandatory = mandatory;
    }

    public Set<RegistrationFormField> getFields() {
        return fields;
    }

    public void setFields(Set<RegistrationFormField> fields) {
        this.fields = fields != null ? fields : new HashSet<>();
    }

    // Méthodes utilitaires
    public void addField(RegistrationFormField field) {
        fields.add(field);
        field.setForm(this);
    }

    public void removeField(RegistrationFormField field) {
        fields.remove(field);
        field.setForm(null);
    }
}