package com.digital.school.model;

import com.digital.school.config.school.SchoolAware;
import jakarta.persistence.*;
import java.util.HashSet;
import java.util.Set;
import org.hibernate.annotations.Filter;
import org.hibernate.annotations.FilterDef;
import org.hibernate.annotations.ParamDef;

import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
@Table(name = "classes")
@FilterDef(name = "schoolFilter", parameters = @ParamDef(name = "schoolId", type = Long.class))
@Filter(name = "schoolFilter", condition = "school_id = :schoolId")
public class Classe extends AuditableEntity implements SchoolAware {

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "school_id", nullable = false)
    private School school;

    @Column(nullable = false)
    private String name;
    
    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "level_id", nullable = false)
    private Level level;
    
    private Integer maxStudents;
    
    @Column(name = "school_year")
    private String schoolYear;
    
    @OneToMany(mappedBy = "classe")
    private Set<Student> students = new HashSet<>();
    
    @ManyToMany
    @JoinTable(
        name = "class_subjects",
        joinColumns = @JoinColumn(name = "class_id"),
        inverseJoinColumns = @JoinColumn(name = "subject_id")
    )
    private Set<Subject> subjects = new HashSet<>();


    @ManyToMany(mappedBy = "classes") // Relation bidirectionnelle avec Professor
    private Set<Professor> professors = new HashSet<>();

    public Classe() {
    }

    @Override
    public School getSchool() { return school; }

    @Override
    public void setSchool(School school) { this.school = school; }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Level getLevel() {
        return level;
    }

    public void setLevel(Level level) {
        this.level = level;
    }

    public Integer getMaxStudents() {
        return maxStudents;
    }

    public void setMaxStudents(Integer maxStudents) {
        this.maxStudents = maxStudents;
    }

    public String getSchoolYear() {
        return schoolYear;
    }

    public void setSchoolYear(String schoolYear) {
        this.schoolYear = schoolYear;
    }

    public Set<Student> getStudents() {
        return students;
    }

    public void setStudents(Set<Student> students) {
        this.students = students != null ? students : new HashSet<>();
    }

    public Set<Subject> getSubjects() {
        return subjects;
    }

    public void setSubjects(Set<Subject> subjects) {
        this.subjects = subjects != null ? subjects : new HashSet<>();
    }

    public
    Set<Professor> getProfessors() {
        return professors;
    }
    public void setProfessors(Set<Professor> professors) {
        this.professors = professors != null ? professors : new HashSet<>();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Classe classe = (Classe) o;
        return id != null && id.equals(classe.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}