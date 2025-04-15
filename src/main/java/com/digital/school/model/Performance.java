package com.digital.school.model;

import com.digital.school.config.school.SchoolAware;
import jakarta.persistence.*;
import org.hibernate.annotations.Filter;

import java.time.LocalDateTime;

@Entity
@Table(name = "performances")
@Filter(name = "schoolFilter", condition = "school_id = :schoolId")
public class Performance extends AuditableEntity implements SchoolAware {

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "school_id", nullable = false)
    private School school;

    @ManyToOne
    @JoinColumn(name = "student_id", nullable = false)
    private User student; // L'étudiant concerné

    @ManyToOne
    @JoinColumn(name = "subject_id", nullable = true)
    private Subject subject; // Matière concernée (optionnel si c'est une moyenne générale)

    private Double averageGrade; // Moyenne des notes obtenues

    private Double classAverage; // Moyenne générale de la classe pour comparaison

    private Integer rank; // Classement de l'étudiant

    private Integer totalStudents; // Nombre total d'étudiants dans la classe

    private LocalDateTime updatedAt; // Date de mise à jour de la performance

    public Performance(Student student) {
        this.updatedAt = LocalDateTime.now();
    }
    //donne sucessRate et ses getter and setter
    @Column(name = "success_rate")
    private Double successRate;

    @Override
    public School getSchool() { return school; }
    @Override
    public void setSchool(School school) { this.school = school; }

    public Double getSuccessRate() {
        return successRate;
    }

    public void setSuccessRate(Double successRate) {
        this.successRate = successRate;
    }

    public Performance() {
        
    }

    public User getStudent() {
        return student;
    }

    public void setStudent(User student) {
        this.student = student;
    }

    public Subject getSubject() {
        return subject;
    }

    public void setSubject(Subject subject) {
        this.subject = subject;
    }

    public Double getAverageGrade() {
        return averageGrade;
    }

    public void setAverageGrade(Double averageGrade) {
        this.averageGrade = averageGrade;
    }

    public Double getClassAverage() {
        return classAverage;
    }

    public void setClassAverage(Double classAverage) {
        this.classAverage = classAverage;
    }

    public Integer getRank() {
        return rank;
    }

    public void setRank(Integer rank) {
        this.rank = rank;
    }

    public Integer getTotalStudents() {
        return totalStudents;
    }

    public void setTotalStudents(Integer totalStudents) {
        this.totalStudents = totalStudents;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }
}
