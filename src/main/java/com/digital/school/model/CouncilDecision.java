package com.digital.school.model;

import com.digital.school.config.school.SchoolAware;
import com.digital.school.model.enumerated.CouncilDecisionEnum;
import com.digital.school.model.enumerated.Trimester;
import jakarta.persistence.*;
import org.hibernate.annotations.Filter;

import java.time.LocalDateTime;

@Entity
@Table(name = "council_decisions")
@Filter(name = "schoolFilter", condition = "school_id = :schoolId")
public class CouncilDecision extends AuditableEntity implements SchoolAware {

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "school_id", nullable = false)
    private School school;

    @Column(name = "student_id", nullable = false)
    private Long studentId;

    // Utilisation de l'enum Trimester
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Trimester trimester;

    // Utilisation de l'enum CouncilDecisionEnum pour la décision
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private CouncilDecisionEnum decision;

    @Column(columnDefinition = "TEXT")
    private String comments;

    @Column(name = "created_by", nullable = false)
    private Long createdBy;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    // Getters and setters...

    @Override
    public School getSchool() { return school; }
    @Override
    public void setSchool(School school) { this.school = school; }
    public Long getStudentId() {
        return studentId;
    }
    public void setStudentId(Long studentId) {
        this.studentId = studentId;
    }
    public Trimester getTrimester() { return trimester; }
    public void setTrimester(Trimester trimester) { this.trimester = trimester; }
    public CouncilDecisionEnum getDecision() { return decision; }
    public void setDecision(CouncilDecisionEnum decision) { this.decision = decision; }
    public String getComments() {
        return comments;
    }
    public void setComments(String comments) {
        this.comments = comments;
    }
    public Long getCreatedBy() {
        return createdBy;
    }
    public void setCreatedBy(Long createdBy) {
        this.createdBy = createdBy;
    }
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}
