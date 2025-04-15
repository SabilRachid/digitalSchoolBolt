package com.digital.school.model;

import jakarta.persistence.Embeddable;

@Embeddable
public class Contact {
    private String name;
    private String role;
    private String email;
    private String phone;
    private String office;
    private String availability;

    // Getters and setters
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    
    public String getRole() { return role; }
    public void setRole(String role) { this.role = role; }
    
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    
    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }
    
    public String getOffice() { return office; }
    public void setOffice(String office) { this.office = office; }
    
    public String getAvailability() { return availability; }
    public void setAvailability(String availability) {  this.availability = availability; }

}
