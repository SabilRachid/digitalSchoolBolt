package com.digital.school.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.time.LocalDate;

@Entity
@DiscriminatorValue("course")
@Table(name = "courses")
@PrimaryKeyJoinColumn(name = "id")
public class Course extends Event {

    private LocalDate date;

    // Association spécifique pour le professeur qui enseigne le cours.
    @ManyToOne(optional = false)
    @JoinColumn(name = "professor_id", nullable = false)
    private Professor professor;

    // Nombre de ressources associées au cours
    @Column(name = "resource_count")
    private int resourceCount = 0;

    // Lien en ligne pour les cours virtuels (optionnel)
    private String onlineLink;

    // Raison d'annulation (si le cours est annulé)
    @Column(columnDefinition = "TEXT")
    private String cancellationReason;

    // Notes de l'instructeur
    @Column(columnDefinition = "TEXT")
    private String instructorNotes;

    // --- Nouvelle propriété Attendance ---
    // Chaque cours possède une fiche d'attendance unique.
    @OneToOne(mappedBy = "course", cascade = CascadeType.ALL, orphanRemoval = true)
    private Attendance attendance;

    // Getters et setters

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public Professor getProfessor() {
        return professor;
    }

    public void setProfessor(Professor professor) {
        this.professor = professor;
    }


    public int getResourceCount() {
        return resourceCount;
    }

    public void setResourceCount(int resourceCount) {
        this.resourceCount = resourceCount;
    }

    public String getOnlineLink() {
        return onlineLink;
    }

    public void setOnlineLink(String onlineLink) {
        this.onlineLink = onlineLink;
    }

    public String getCancellationReason() {
        return cancellationReason;
    }

    public void setCancellationReason(String cancellationReason) {
        this.cancellationReason = cancellationReason;
    }

    public String getInstructorNotes() {
        return instructorNotes;
    }

    public void setInstructorNotes(String instructorNotes) {
        this.instructorNotes = instructorNotes;
    }

    public Attendance getAttendance() {
        return attendance;
    }

    public void setAttendance(Attendance attendance) {
        this.attendance = attendance;
    }

}
