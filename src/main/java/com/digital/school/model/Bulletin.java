package com.digital.school.model;

import com.digital.school.config.school.SchoolAware;
import jakarta.persistence.*;
import org.hibernate.annotations.Filter;

import java.time.LocalDateTime;

@Entity
@Table(name = "bulletins")
@Filter(name = "schoolFilter", condition = "school_id = :schoolId")
public class Bulletin extends AuditableEntity implements SchoolAware {

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "school_id", nullable = false)
    private School school;

    @ManyToOne
    @JoinColumn(name = "class_id", nullable = false)
    private Classe classe;

    @Column(nullable = false)
    private String period; // Example: "Term 1", "Semester 2"

    @Column(nullable = false)
    private String title; // Bulletin title

    @Column(columnDefinition = "TEXT")
    private String comments; // General comments on performance

    @Column(nullable = false)
    private String filePath; // Path to the generated bulletin PDF file

    @Column(nullable = false)
    private LocalDateTime generatedAt; // Date and time of generation

    // Constructors
    public Bulletin() {}

    public Bulletin(Classe classe, String period, String title, String comments, String filePath) {
        this.classe = classe;
        this.period = period;
        this.title = title;
        this.comments = comments;
        this.filePath = filePath;
        this.generatedAt = LocalDateTime.now();
    }

    // Getters and Setters
    @Override
    public School getSchool() { return school; }

    @Override
    public void setSchool(School school) { this.school = school; }

    public Classe getClasse() { return classe; }

    public void setClasse(Classe classe) { this.classe = classe; }

    public String getPeriod() { return period; }

    public void setPeriod(String period) { this.period = period; }

    public String getTitle() { return title; }

    public void setTitle(String title) { this.title = title; }

    public String getComments() { return comments; }

    public void setComments(String comments) { this.comments = comments; }

    public String getFilePath() { return filePath; }

    public void setFilePath(String filePath) { this.filePath = filePath; }

    public LocalDateTime getGeneratedAt() { return generatedAt; }

    public void setGeneratedAt(LocalDateTime generatedAt) { this.generatedAt = generatedAt; }
}
