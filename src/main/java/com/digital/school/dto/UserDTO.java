package com.digital.school.dto;

import com.digital.school.model.enumerated.RoleName;
import java.util.Set;

public class UserDTO {
    private Long id;
    private String username;
    private String email;
    private String password;
    private String firstName;
    private String lastName;
    private boolean enabled;
    private Set<RoleName> roles;
    private Long classeId;

    // Getters and setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
    public String getFirstName() { return firstName; }
    public void setFirstName(String firstName) { this.firstName = firstName; }
    public String getLastName() { return lastName; }
    public void setLastName(String lastName) { this.lastName = lastName; }
    public boolean isEnabled() { return enabled; }
    public void setEnabled(boolean enabled) { this.enabled = enabled; }
    public Set<RoleName> getRoles() { return roles; }
    public void setRoles(Set<RoleName> roles) { this.roles = roles; }
    public Long getClasseId() { return classeId; }
    public void setClasseId(Long classeId) { this.classeId = classeId; }
}