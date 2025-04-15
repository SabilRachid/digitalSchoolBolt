package com.digital.school.model;

import jakarta.persistence.*;
import org.hibernate.annotations.Filter;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "registration_steps")
@Filter(name = "schoolFilter", condition = "school_id = :schoolId")
public class RegistrationStep extends AuditableEntity {

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "school_id", nullable = false)
    private School school;

    @Column(nullable = false)
    private String name;
    
    @Column(columnDefinition = "TEXT")
    private String description;
    
    @Column(name = "order_index", nullable = false)
    private Integer orderIndex;
    
    @Column(name = "is_mandatory")
    private boolean mandatory = true;
    
    @OneToMany(mappedBy = "step", cascade = CascadeType.ALL, orphanRemoval = true)
    @OrderBy("name")
    private Set<RegistrationForm> forms = new HashSet<>();

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

    public Integer getOrderIndex() {
        return orderIndex;
    }

    public void setOrderIndex(Integer orderIndex) {
        this.orderIndex = orderIndex;
    }

    public boolean isMandatory() {
        return mandatory;
    }

    public void setMandatory(boolean mandatory) {
        this.mandatory = mandatory;
    }

    public Set<RegistrationForm> getForms() {
        return forms;
    }

    public void setForms(Set<RegistrationForm> forms) {
        this.forms = forms != null ? forms : new HashSet<>();
    }

    // Méthodes utilitaires
    public void addForm(RegistrationForm form) {
        forms.add(form);
        form.setStep(this);
    }

    public void removeForm(RegistrationForm form) {
        forms.remove(form);
        form.setStep(null);
    }
}