package com.digital.school.model;

import jakarta.persistence.*;
import org.hibernate.annotations.Filter;

import java.time.LocalDateTime;

@Entity
@Table(name = "registration_submitted_documents")
@Filter(name = "schoolFilter", condition = "school_id = :schoolId")
public class RegistrationSubmittedDocument extends AuditableEntity {

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "school_id", nullable = false)
    private School school;

    @ManyToOne
    @JoinColumn(name = "registration_id", nullable = false)
    private Registration registration;

    @ManyToOne
    @JoinColumn(name = "document_id", nullable = false)
    private RegistrationDocument document;

    @Column(name = "file_path", nullable = false)
    private String filePath;

    @Column(name = "file_name", nullable = false)
    private String fileName;

    @Column(name = "file_size", nullable = false)
    private Long fileSize;

    @Column(name = "mime_type", nullable = false)
    private String mimeType;

    @Column(name = "uploaded_at")
    private LocalDateTime uploadedAt = LocalDateTime.now();

    @Column(nullable = false)
    private String status = "PENDING";

    @Column(name = "reviewed_at")
    private LocalDateTime reviewedAt;

    @ManyToOne
    @JoinColumn(name = "reviewed_by_id")
    private User reviewedBy;

    @Column(name = "review_notes", columnDefinition = "TEXT")
    private String reviewNotes;

    // Getters and setters
    public School getSchool() { return school; }

    public void setSchool(School school) { this.school = school; }

    public Registration getRegistration() {
        return registration;
    }

    public void setRegistration(Registration registration) {
        this.registration = registration;
    }

    public RegistrationDocument getDocument() {
        return document;
    }

    public void setDocument(RegistrationDocument document) {
        this.document = document;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public Long getFileSize() {
        return fileSize;
    }

    public void setFileSize(Long fileSize) {
        this.fileSize = fileSize;
    }

    public String getMimeType() {
        return mimeType;
    }

    public void setMimeType(String mimeType) {
        this.mimeType = mimeType;
    }

    public LocalDateTime getUploadedAt() {
        return uploadedAt;
    }

    public void setUploadedAt(LocalDateTime uploadedAt) {
        this.uploadedAt = uploadedAt;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
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

    public String getReviewNotes() {
        return reviewNotes;
    }

    public void setReviewNotes(String reviewNotes) {
        this.reviewNotes = reviewNotes;
    }

    // Méthodes utilitaires
    public boolean isPending() {
        return "PENDING".equals(status);
    }

    public boolean isApproved() {
        return "APPROVED".equals(status);
    }

    public boolean isRejected() {
        return "REJECTED".equals(status);
    }

    public void approve(User reviewer, String notes) {
        this.status = "APPROVED";
        this.reviewedBy = reviewer;
        this.reviewedAt = LocalDateTime.now();
        this.reviewNotes = notes;
    }

    public void reject(User reviewer, String notes) {
        this.status = "REJECTED";
        this.reviewedBy = reviewer;
        this.reviewedAt = LocalDateTime.now();
        this.reviewNotes = notes;
    }

    public boolean isValid() {
        if (document == null) return false;

        // Vérifier la taille du fichier
        if (document.getMaxSizeMb() != null) {
            long maxSizeBytes = document.getMaxSizeMb() * 1024 * 1024L;
            if (fileSize > maxSizeBytes) return false;
        }

        // Vérifier le type de fichier
        if (document.getFileTypes() != null && document.getFileTypes().length > 0) {
            String extension = fileName.substring(fileName.lastIndexOf(".") + 1).toLowerCase();
            boolean validType = false;
            for (String type : document.getFileTypes()) {
                if (type.equalsIgnoreCase(extension)) {
                    validType = true;
                    break;
                }
            }
            if (!validType) return false;
        }

        return true;
    }
}