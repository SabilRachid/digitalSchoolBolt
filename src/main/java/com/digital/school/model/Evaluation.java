package com.digital.school.model;

import com.digital.school.model.enumerated.EventStatus;
import jakarta.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "evaluations")
@DiscriminatorValue("evaluations")
@PrimaryKeyJoinColumn(name = "id")
public abstract class Evaluation extends Event {

    @Column(name = "due_date")
    private LocalDate dueDate;

    @ManyToOne(optional = false)
    @JoinColumn(name = "professor_id")
    private Professor professor;

    @Column(name = "maxScore", nullable = true)
    private Double maxScore;

    // Nouveau champ pour indiquer si l'évaluation est notée
    @Column(name = "graded", nullable = true)
    private boolean graded = false;

    // Ajout de la relation vers EvaluationGrade
    @OneToMany(mappedBy = "evaluation", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<EvaluationGrade> evaluationGrades = new ArrayList<>();

    // Getters and setters
    public LocalDate getDueDate() {
        return dueDate;
    }

    public void setDueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
    }
    public Professor getProfessor() {
        return professor;
    }

    public void setProfessor(Professor professor) {
        this.professor = professor;
    }


    public Double getMaxScore() {
        return maxScore;
    }

    public void setMaxScore(Double maxScore) {
        this.maxScore = maxScore;
    }

    public boolean isGraded() {
        return graded;
    }

    public void setGraded(boolean graded) {
        this.graded = graded;
    }

    @Override
    public String toString() {
        return "Evaluation{" +
                ", professorUserName=" + professor.getUsername() +
                ", maxScore=" + maxScore +
                ", graded=" + graded +
                '}';
    }

    public List<EvaluationGrade> getEvaluationGrades() {
        return evaluationGrades;
    }

    public void setEvaluationGrades(List<EvaluationGrade> evaluationGrades) {
        this.evaluationGrades = evaluationGrades;
    }
}
