package com.digital.school.model;

import com.digital.school.model.enumerated.GradeType;
import jakarta.persistence.*;
import org.hibernate.annotations.Filter;

import java.time.LocalDateTime;

@Entity
@Table(name = "evaluation_grades")
@Filter(name = "schoolFilter", condition = "school_id = :schoolId")
public class EvaluationGrade extends AuditableEntity {

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "school_id", nullable = false)
    private School school;

    // L'évaluation à laquelle correspond cette note
    @ManyToOne(optional = false)
    @JoinColumn(name = "evaluation_id")
    private Evaluation evaluation;

    // L'étudiant concerné par cette note
    @ManyToOne(optional = false)
    @JoinColumn(name = "student_id")
    private Student student;

    // La note attribuée par le professeur
    @Column(nullable = true)
    private Double grade;

    // Nouvelle propriété pour le type de note, calculé à partir de grade
    @Enumerated(EnumType.STRING)
    @Column(name = "grade_type")
    private GradeType gradeType;

    // Remarques ou commentaires du professeur
    @Column(columnDefinition = "TEXT")
    private String remark;

    // Date et heure de la saisie de la note (optionnel)
    @Column(name = "graded_at")
    private LocalDateTime gradedAt;

    // Champ indiquant si l'étudiant a soumis son devoir
    @Column(name = "submitted", nullable = false)
    private boolean submitted = false;

    @Column(name = "submission_date")
    private LocalDateTime submissionDate;

    // Getters et setters

    public School getSchool() { return school; }

    public void setSchool(School school) { this.school = school; }

    public Evaluation getEvaluation() {
        return evaluation;
    }

    public void setEvaluation(Evaluation evaluation) {
        this.evaluation = evaluation;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public Double getGrade() {
        return grade;
    }

    public void setGrade(Double grade) {
        this.grade = grade;
        // Met à jour automatiquement le type de note si la valeur est renseignée
        if (grade != null) {
            this.gradeType = GradeType.fromScore(grade);
        } else {
            this.gradeType = null;
        }
    }

    public GradeType getGradeType() {
        return gradeType;
    }

    public void setGradeType(GradeType gradeType) {
        this.gradeType = gradeType;
    }


    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public LocalDateTime getGradedAt() {
        return gradedAt;
    }

    public void setGradedAt(LocalDateTime gradedAt) {
        this.gradedAt = gradedAt;
    }

    public boolean isSubmitted() {
        return submitted;
    }

    public void setSubmitted(boolean submitted) {
        this.submitted = submitted;
    }


    public LocalDateTime getSubmissionDate() {
        return submissionDate;
    }

    public void setSubmissionDate(LocalDateTime submissionDate) {
        this.submissionDate = submissionDate;
    }

    @Override
    public String toString() {
        return "EvaluationGrade{" +
                "id=" + id +
                ", evaluation=" + (evaluation != null ? evaluation.getId() : "null") +
                ", student=" + (student != null ? student.getId() : "null") +
                ", grade=" + grade +
                ", remark='" + remark + '\'' +
                ", gradedAt=" + gradedAt +
                ", submitted=" + submitted +
                '}';
    }
}
