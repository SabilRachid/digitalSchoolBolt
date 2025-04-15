package com.digital.school.model;

import com.digital.school.model.enumerated.RegistrationStatus;
import jakarta.persistence.*;
import org.hibernate.annotations.Filter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "registrations")
@Filter(name = "schoolFilter", condition = "school_id = :schoolId")
public class Registration extends AuditableEntity {

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "school_id", nullable = false)
    private School school;

    // Informations personnelles
    @Column(name = "student_id")
    private Long studentId;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "birth_date")
    private LocalDate birthDate;

    @Column(name = "birth_place")
    private String birthPlace;

    @Column(name = "nationality")
    private String nationality;

    @Column(name = "gender")
    private String gender;

    @Column(name = "address")
    private String address;

    // Informations relatives aux parents
    @Column(name = "parent1_first_name")
    private String parent1FirstName;

    @Column(name = "parent1_last_name")
    private String parent1LastName;

    @Column(name = "parent1_email")
    private String parent1Email;

    @Column(name = "parent1_phone")
    private String parent1Phone;

    // Option de contact principal pour chaque parent
    @Column(name = "parent1_primary_contact", nullable = false)
    private boolean parent1PrimaryContact;

    @Column(name = "parent2_primary_contact", nullable = false)
    private boolean parent2PrimaryContact;

    @Column(name = "parent2_first_name")
    private String parent2FirstName;

    @Column(name = "parent2_last_name")
    private String parent2LastName;

    @Column(name = "parent2_email")
    private String parent2Email;

    @Column(name = "parent2_phone")
    private String parent2Phone;

    // Informations académiques et sur le parcours
    @Column(name = "academic_year", nullable = false)
    private String academicYear;

    @ManyToOne
    @JoinColumn(name = "level_id")
    private Level level;

    @Column(name = "previous_school")
    private String previousSchool;

    @Column(name = "previous_level")
    private String previousLevel;

    // Champs liés au processus d'inscription / revue
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private RegistrationStatus status;

    @Column(name = "submitted_at")
    private LocalDateTime submittedAt;

    @Column(name = "reviewed_at")
    private LocalDateTime reviewedAt;

    @ManyToOne
    @JoinColumn(name = "reviewed_by_id")
    private User reviewedBy;

    private String decision;


    @Column(name = "decision_date")
    private LocalDateTime decisionDate;

    @Column(columnDefinition = "TEXT")
    private String notes;

    @Column(columnDefinition = "TEXT")
    private String comments;

    @OneToMany(mappedBy = "registration", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<RegistrationSubmittedDocument> submittedDocuments = new ArrayList<>();

    // Ajout des getters et setters pour submittedDocuments

    public School getSchool() { return school; }

    public void setSchool(School school) { this.school = school; }

    public List<RegistrationSubmittedDocument> getSubmittedDocuments() {
        return submittedDocuments;
    }
    public void setSubmittedDocuments(List<RegistrationSubmittedDocument> submittedDocuments) {
        this.submittedDocuments = submittedDocuments;
    }
    // Getters et setters pour le champ comments

    public String getComments() {
        return comments;
    }
    public void setComments(String comments) {
        this.comments = comments;
    }

    // Getters and setters

    public Long getStudentId() {
        return studentId;
    }
    public void setStudentId(Long studentId) {
        this.studentId = studentId;
    }

    public String getFirstName() {
        return firstName;
    }
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }
    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    public String getBirthPlace() {
        return birthPlace;
    }
    public void setBirthPlace(String birthPlace) {
        this.birthPlace = birthPlace;
    }

    public String getNationality() {
        return nationality;
    }
    public void setNationality(String nationality) {
        this.nationality = nationality;
    }

    public String getGender() {
        return gender;
    }
    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getAddress() {
        return address;
    }
    public void setAddress(String address) {
        this.address = address;
    }

    public String getParent1FirstName() {
        return parent1FirstName;
    }
    public void setParent1FirstName(String parent1FirstName) {
        this.parent1FirstName = parent1FirstName;
    }

    public String getParent1LastName() {
        return parent1LastName;
    }
    public void setParent1LastName(String parent1LastName) {
        this.parent1LastName = parent1LastName;
    }

    public String getParent1Email() {
        return parent1Email;
    }
    public void setParent1Email(String parent1Email) {
        this.parent1Email = parent1Email;
    }

    public String getParent1Phone() {
        return parent1Phone;
    }
    public void setParent1Phone(String parent1Phone) {
        this.parent1Phone = parent1Phone;
    }

    public boolean isParent1PrimaryContact() {
        return parent1PrimaryContact;
    }
    public void setParent1PrimaryContact(boolean parent1PrimaryContact) {
        this.parent1PrimaryContact = parent1PrimaryContact;
    }

    public boolean isParent2PrimaryContact() {
        return parent2PrimaryContact;
    }
    public void setParent2PrimaryContact(boolean parent2PrimaryContact) {
        this.parent2PrimaryContact = parent2PrimaryContact;
    }

    public String getParent2FirstName() {
        return parent2FirstName;
    }
    public void setParent2FirstName(String parent2FirstName) {
        this.parent2FirstName = parent2FirstName;
    }

    public String getParent2LastName() {
        return parent2LastName;
    }
    public void setParent2LastName(String parent2LastName) {
        this.parent2LastName = parent2LastName;
    }

    public String getParent2Email() {
        return parent2Email;
    }
    public void setParent2Email(String parent2Email) {
        this.parent2Email = parent2Email;
    }

    public String getParent2Phone() {
        return parent2Phone;
    }
    public void setParent2Phone(String parent2Phone) {
        this.parent2Phone = parent2Phone;
    }

    public String getAcademicYear() {
        return academicYear;
    }
    public void setAcademicYear(String academicYear) {
        this.academicYear = academicYear;
    }

    public Level getLevel() {
        return level;
    }
    public void setLevel(Level level) {
        this.level = level;
    }

    public String getPreviousSchool() {
        return previousSchool;
    }
    public void setPreviousSchool(String previousSchool) {
        this.previousSchool = previousSchool;
    }

    public String getPreviousLevel() {
        return previousLevel;
    }
    public void setPreviousLevel(String previousLevel) {
        this.previousLevel = previousLevel;
    }

    public RegistrationStatus getStatus() {
        return status;
    }

    public void setStatus(RegistrationStatus status) {
        this.status = status;
    }
    public LocalDateTime getSubmittedAt() {
        return submittedAt;
    }
    public void setSubmittedAt(LocalDateTime submittedAt) {
        this.submittedAt = submittedAt;
    }

    public LocalDateTime getReviewedAt() {
        return reviewedAt;
    }
    public void setReviewedAt(LocalDateTime reviewedAt) {
        this.reviewedAt = reviewedAt;
    }

    public User getReviewedBy() {
        return reviewedBy;
    }
    public void setReviewedBy(User reviewedBy) {
        this.reviewedBy = reviewedBy;
    }

    public String getDecision() {
        return decision;
    }
    public void setDecision(String decision) {
        this.decision = decision;
    }

    public LocalDateTime getDecisionDate() {
        return decisionDate;
    }
    public void setDecisionDate(LocalDateTime decisionDate) {
        this.decisionDate = decisionDate;
    }

    public String getNotes() {
        return notes;
    }
    public void setNotes(String notes) {
        this.notes = notes;
    }

    // Méthodes utilitaires pour vérifier l'état de l'inscription
    public boolean isDraft() {
        return "DRAFT".equals(status);
    }

    public boolean isSubmitted() {
        return "SUBMITTED".equals(status);
    }

    public boolean isUnderReview() {
        return "REVIEWING".equals(status);
    }

    public boolean isAccepted() {
        return "ACCEPTED".equals(status);
    }

    public boolean isRejected() {
        return "REJECTED".equals(status);
    }

    public boolean needsMoreInfo() {
        return "NEEDS_INFO".equals(status);
    }
}
