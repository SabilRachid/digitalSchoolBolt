package com.digital.school.model;

import jakarta.persistence.*;
import org.hibernate.annotations.Filter;

import java.time.LocalDateTime;

@Entity
@Table(name = "student_comments")
@Filter(name = "schoolFilter", condition = "school_id = :schoolId")
public class StudentComment extends AuditableEntity {


    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "school_id", nullable = false)
    private School school;

    @ManyToOne
    @JoinColumn(name = "student_id", nullable = false)
    private User student;

    @ManyToOne
    @JoinColumn(name = "teacher_id", nullable = false)
    private User teacher;

    @Column(nullable = false)
    private String subject;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String comment;

    @Column(nullable = false)
    private Integer trimester;

    @Column(name = "academic_year", nullable = false)
    private String academicYear;

    @Column(name = "created_at")
    private LocalDateTime createdAt = LocalDateTime.now();

    // Getters and setters

    public School getSchool() { return school; }

    public void setSchool(School school) { this.school = school; }

    public User getStudent() {
        return student;
    }

    public void setStudent(User student) {
        this.student = student;
    }

    public User getTeacher() {
        return teacher;
    }

    public void setTeacher(User teacher) {
        this.teacher = teacher;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Integer getTrimester() {
        return trimester;
    }

    public void setTrimester(Integer trimester) {
        this.trimester = trimester;
    }

    public String getAcademicYear() {
        return academicYear;
    }

    public void setAcademicYear(String academicYear) {
        this.academicYear = academicYear;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    // Méthodes utilitaires
    public boolean isValid() {
        return student != null &&
                teacher != null &&
                subject != null && !subject.trim().isEmpty() &&
                comment != null && !comment.trim().isEmpty() &&
                trimester != null && trimester >= 1 && trimester <= 3 &&
                academicYear != null && academicYear.matches("\\d{4}-\\d{4}");
    }

    public boolean isFromCurrentYear() {
        if (academicYear == null) return false;

        String[] years = academicYear.split("-");
        if (years.length != 2) return false;

        int startYear = Integer.parseInt(years[0]);
        int currentYear = LocalDateTime.now().getYear();

        return startYear == currentYear || startYear == currentYear - 1;
    }

    public boolean isFromCurrentTrimester() {
        if (trimester == null) return false;

        LocalDateTime now = LocalDateTime.now();
        int month = now.getMonthValue();

        // 1er trimestre : septembre à novembre
        if (month >= 9 && month <= 11) return trimester == 1;

        // 2ème trimestre : décembre à février
        if (month == 12 || month <= 2) return trimester == 2;

        // 3ème trimestre : mars à juin
        if (month >= 3 && month <= 6) return trimester == 3;

        return false;
    }

    @PrePersist
    protected void onCreate() {
        if (createdAt == null) {
            createdAt = LocalDateTime.now();
        }
    }
}