package com.digital.school.model;

import jakarta.persistence.*;
import com.digital.school.model.enumerated.PermissionName;
import org.hibernate.annotations.Filter;

@Entity
@Table(name = "permissions")
@Filter(name = "schoolFilter", condition = "school_id = :schoolId")
public class Permission extends AuditableEntity {


    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "school_id", nullable = false)
    private School school;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, unique = true)
    private PermissionName name;
    
    private String description;
    
    public Permission() {
    }

    public School getSchool() { return school; }

    public void setSchool(School school) { this.school = school; }
    
    public Permission(PermissionName name) {
        this.name = name;
    }

    public PermissionName getName() {
        return name;
    }

    public void setName(PermissionName name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Permission that = (Permission) o;
        return id != null && id.equals(that.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}