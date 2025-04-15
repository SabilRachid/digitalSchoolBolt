package com.digital.school.model;

import jakarta.persistence.*;
import org.hibernate.annotations.Filter;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "subjects")
@Filter(name = "schoolFilter", condition = "school_id = :schoolId")
public class Subject extends AuditableEntity {

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "school_id", nullable = false)
    private School school;

    @Column(nullable = false, unique = true)
    private String code;
    
    @Column(nullable = false, unique = true)
    private String name;
    
    @Column(columnDefinition = "TEXT")
    private String description;
    
    @Column(nullable = false)
    private Double coefficient = 1.0;
    
    @Column(name = "is_optional")
    private boolean optional = false;

    @ManyToMany(mappedBy = "subjects") // Relation bidirectionnelle avec Professor
    private Set<Professor> professors = new HashSet<>();

    @ManyToMany(mappedBy = "subjects")
    private Set<Level> levels = new HashSet<>();
    
    @ManyToMany(mappedBy = "subjects")
    private Set<Classe> classes = new HashSet<>();
    
    @OneToMany(mappedBy = "subject")
    private Set<Course> courses = new HashSet<>();

    public Subject() {
    }

    public School getSchool() { return school; }

    public void setSchool(School school) { this.school = school; }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

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

    public Double getCoefficient() {
        return coefficient;
    }

    public void setCoefficient(Double coefficient) {
        this.coefficient = coefficient;
    }

    public boolean isOptional() {
        return optional;
    }

    public void setOptional(boolean optional) {
        this.optional = optional;
    }

    public Set<Professor> getProfessors() {
        return professors;
    }

    public void setProfessors(Set<Professor> professors) {
        this.professors = professors;
    }


    public Set<Level> getLevels() {
        return levels;
    }

    public void setLevels(Set<Level> levels) {
        this.levels = levels != null ? levels : new HashSet<>();
    }

    public Set<Classe> getClasses() {
        return classes;
    }

    public void setClasses(Set<Classe> classes) {
        this.classes = classes != null ? classes : new HashSet<>();
    }

    public Set<Course> getCourses() {
        return courses;
    }

    public void setCourses(Set<Course> courses) {
        this.courses = courses != null ? courses : new HashSet<>();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Subject subject = (Subject) o;
        return id != null && id.equals(subject.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}