package com.digital.school.model;

import jakarta.persistence.*;
import com.digital.school.converter.ListToJsonStringConverter;
import com.digital.school.converter.MapToJsonStringConverter;
import org.hibernate.annotations.Filter;

import java.util.List;
import java.util.Map;

@Entity
@Table(name = "registration_form_fields")
@Filter(name = "schoolFilter", condition = "school_id = :schoolId")
public class RegistrationFormField extends AuditableEntity {

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "school_id", nullable = false)
    private School school;

    @ManyToOne
    @JoinColumn(name = "form_id", nullable = false)
    private RegistrationForm form;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String label;

    @Column(name = "field_type", nullable = false)
    private String fieldType;

    // Utilisation d'un convertisseur pour stocker la liste de chaînes en JSONB
    @Convert(converter = ListToJsonStringConverter.class)
    @Column(columnDefinition = "jsonb")
    private List<String> options;

    // Pour le champ validationRules, on utilise le convertisseur déjà défini
    @Convert(converter = MapToJsonStringConverter.class)
    @Column(name = "validation_rules", columnDefinition = "jsonb")
    private Map<String, Object> validationRules;

    @Column(name = "is_mandatory")
    private boolean mandatory = true;

    @Column(name = "order_index", nullable = false)
    private Integer orderIndex;

    @Column(name = "help_text", columnDefinition = "TEXT")
    private String helpText;

    // Getters et setters

    public School getSchool() { return school; }

    public void setSchool(School school) { this.school = school; }

    public RegistrationForm getForm() {
        return form;
    }

    public void setForm(RegistrationForm form) {
        this.form = form;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getFieldType() {
        return fieldType;
    }

    public void setFieldType(String fieldType) {
        this.fieldType = fieldType;
    }

    public List<String> getOptions() {
        return options;
    }

    public void setOptions(List<String> options) {
        this.options = options;
    }

    public Map<String, Object> getValidationRules() {
        return validationRules;
    }

    public void setValidationRules(Map<String, Object> validationRules) {
        this.validationRules = validationRules;
    }

    public boolean isMandatory() {
        return mandatory;
    }

    public void setMandatory(boolean mandatory) {
        this.mandatory = mandatory;
    }

    public Integer getOrderIndex() {
        return orderIndex;
    }

    public void setOrderIndex(Integer orderIndex) {
        this.orderIndex = orderIndex;
    }

    public String getHelpText() {
        return helpText;
    }

    public void setHelpText(String helpText) {
        this.helpText = helpText;
    }
}
