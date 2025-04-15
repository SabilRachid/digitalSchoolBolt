package com.digital.school.model;

import jakarta.persistence.*;
import com.digital.school.model.enumerated.StudentAttendanceStatus;
import org.hibernate.annotations.Filter;

import java.time.LocalDateTime;

@Entity
@Table(name = "student_attendances")
@Filter(name = "schoolFilter", condition = "school_id = :schoolId")
public class StudentAttendance extends AuditableEntity {

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "school_id", nullable = false)
    private School school;

    // Référence à l'entité Attendance
    @ManyToOne
    @JoinColumn(name = "attendance_id", nullable = false)
    private Attendance attendance;

    // Référence à l'étudiant concerné
    @ManyToOne
    @JoinColumn(name = "student_id", nullable = false)
    private Student student;

    // Statut de la présence (PRESENT, RETARD, ABSENT, etc.)
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private StudentAttendanceStatus status;

    // Date et heure d'enregistrement de ce statut
    @Column(nullable = false)
    private LocalDateTime recordedAt = LocalDateTime.now();

    // Justification textuelle (optionnelle)
    @Column(columnDefinition = "TEXT")
    private String justification;

    // Chemin ou nom du fichier justificatif (optionnel)
    @Column(name = "justification_file")
    private String justificationFile;

    public StudentAttendance() {
    }

    public StudentAttendance(Student student, StudentAttendanceStatus status) {
        this.student = student;
        this.status = status;
    }

    // Getters et setters

    public School getSchool() { return school; }

    public void setSchool(School school) { this.school = school; }

    public Attendance getAttendance() {
        return attendance;
    }

    public void setAttendance(Attendance attendance) {
        this.attendance = attendance;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public StudentAttendanceStatus getStatus() {
        return status;
    }

    public void setStatus(StudentAttendanceStatus status) {
        this.status = status;
    }

    public LocalDateTime getRecordedAt() {
        return recordedAt;
    }

    public void setRecordedAt(LocalDateTime recordedAt) {
        this.recordedAt = recordedAt;
    }

    public String getJustification() {
        return justification;
    }

    public void setJustification(String justification) {
        this.justification = justification;
    }

    public String getJustificationFile() {
        return justificationFile;
    }

    public void setJustificationFile(String justificationFile) {
        this.justificationFile = justificationFile;
    }
}
