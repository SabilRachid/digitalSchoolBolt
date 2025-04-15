package com.digital.school.model;

import jakarta.persistence.*;
import org.hibernate.annotations.Filter;

@Entity
@Table(name = "registration_responses")
@Filter(name = "schoolFilter", condition = "school_id = :schoolId")
public class RegistrationResponse extends AuditableEntity {

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "school_id", nullable = false)
    private School school;
    
    @ManyToOne
    @JoinColumn(name = "registration_id", nullable = false)
    private Registration registration;
    
    @ManyToOne
    @JoinColumn(name = "field_id", nullable = false)
    private RegistrationFormField field;
    
    @Column(name = "response_value", columnDefinition = "TEXT")
    private String responseValue;

    // Getters and setters

    public School getSchool() { return school; }

    public void setSchool(School school) { this.school = school; }

    public Registration getRegistration() {
        return registration;
    }

    public void setRegistration(Registration registration) {
        this.registration = registration;
    }

    public RegistrationFormField getField() {
        return field;
    }

    public void setField(RegistrationFormField field) {
        this.field = field;
    }

    public String getResponseValue() {
        return responseValue;
    }

    public void setResponseValue(String responseValue) {
        this.responseValue = responseValue;
    }

    // Méthodes utilitaires
    public boolean isValid() {
        if (field == null) return false;
        if (field.isMandatory() && (responseValue == null || responseValue.trim().isEmpty())) {
            return false;
        }
        
        return validateResponse();
    }

    private boolean validateResponse() {
        if (responseValue == null) return !field.isMandatory();
        
        switch (field.getFieldType()) {
            case "text":
            case "textarea":
                return validateTextResponse();
            case "number":
                return validateNumberResponse();
            case "date":
                return validateDateResponse();
            case "email":
                return validateEmailResponse();
            case "select":
                return validateSelectResponse();
            default:
                return true;
        }
    }

    private boolean validateTextResponse() {
        if (field.getValidationRules() == null) return true;

        Object minObj = field.getValidationRules().get("minLength");
        Object maxObj = field.getValidationRules().get("maxLength");

        Integer minLength = (minObj != null) ? Integer.parseInt(minObj.toString()) : null;
        Integer maxLength = (maxObj != null) ? Integer.parseInt(maxObj.toString()) : null;

        if (minLength != null && responseValue.length() < minLength) return false;
        if (maxLength != null && responseValue.length() > maxLength) return false;

        return true;
    }


    private boolean validateNumberResponse() {
        try {
            double value = Double.parseDouble(responseValue);

            if (field.getValidationRules() == null) return true;

            Object minObj = field.getValidationRules().get("min");
            Object maxObj = field.getValidationRules().get("max");

            Double min = (minObj != null) ? Double.parseDouble(minObj.toString()) : null;
            Double max = (maxObj != null) ? Double.parseDouble(maxObj.toString()) : null;

            if (min != null && value < min) return false;
            if (max != null && value > max) return false;

            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }


    private boolean validateDateResponse() {
        try {
            java.time.LocalDate date = java.time.LocalDate.parse(responseValue);

            if (field.getValidationRules() == null) return true;

            Object minObj = field.getValidationRules().get("min");
            Object maxObj = field.getValidationRules().get("max");

            String min = (minObj != null) ? minObj.toString() : null;
            String max = (maxObj != null) ? maxObj.toString() : null;

            if (min != null && date.isBefore(java.time.LocalDate.parse(min))) return false;
            if (max != null && date.isAfter(java.time.LocalDate.parse(max))) return false;

            return true;
        } catch (Exception e) {
            return false;
        }
    }

    private boolean validateEmailResponse() {
        String emailRegex = "^[A-Za-z0-9+_.-]+@(.+)$";
        return responseValue.matches(emailRegex);
    }

    private boolean validateSelectResponse() {
      /*  if (field.getOptions() == null || field.getOptions().isEmpty()) return true;
        return field.getOptions().contains(responseValue);
      */
        return true;
    }
}