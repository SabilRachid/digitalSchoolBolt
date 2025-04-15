package com.digital.school.model;

import com.digital.school.config.school.SchoolAware;
import com.digital.school.model.enumerated.DocumentCategory;
import com.digital.school.model.enumerated.DocumentType;
import jakarta.persistence.*;
import org.hibernate.annotations.Filter;

import java.time.LocalDateTime;

@Entity
@Table(name = "documents")
@Filter(name = "schoolFilter", condition = "school_id = :schoolId")
public class Document extends AuditableEntity implements SchoolAware {

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "school_id", nullable = false)
    private School school;

    @Column(nullable = false)
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private DocumentType type;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private DocumentCategory category;

    @Column(name = "file_path", nullable = false)
    private String filePath;

    @Column(name = "mime_type")
    private String mimeType;

    @Column(name = "file_size")
    private Long fileSize;

    @ManyToOne
    @JoinColumn(name = "uploaded_by_id")
    private User uploadedBy;

    @Column(name = "uploaded_at")
    private LocalDateTime uploadedAt = LocalDateTime.now();

    @ManyToOne
    @JoinColumn(name = "student_id")
    private User student;

    @ManyToOne
    @JoinColumn(name = "parent_id")
    private User parent;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(name = "is_validated")
    private boolean validated;

    @ManyToOne
    @JoinColumn(name = "validated_by_id")
    private User validatedBy;

    @Column(name = "validated_at")
    private LocalDateTime validatedAt;

    @Column(name = "expiry_date")
    private LocalDateTime expiryDate;

    @Column(name = "is_confidential")
    private boolean confidential;

    @Column(name = "version_number")
    private Integer versionNumber = 1;

    @ManyToOne
    @JoinColumn(name = "previous_version_id")
    private Document previousVersion;

    // Nouvelle relation : association au Event
    @ManyToOne
    @JoinColumn(name = "event_id")
    private Event event;

    // Getters and setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    @Override
    public School getSchool() { return school; }
    @Override
    public void setSchool(School school) { this.school = school; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public DocumentType getType() { return type; }
    public void setType(DocumentType type) { this.type = type; }

    public DocumentCategory getCategory() { return category; }
    public void setCategory(DocumentCategory category) { this.category = category; }

    public String getFilePath() { return filePath; }
    public void setFilePath(String filePath) { this.filePath = filePath; }

    public String getMimeType() { return mimeType; }
    public void setMimeType(String mimeType) { this.mimeType = mimeType; }

    public Long getFileSize() { return fileSize; }
    public void setFileSize(Long fileSize) { this.fileSize = fileSize; }

    public User getUploadedBy() { return uploadedBy; }
    public void setUploadedBy(User uploadedBy) { this.uploadedBy = uploadedBy; }

    public LocalDateTime getUploadedAt() { return uploadedAt; }
    public void setUploadedAt(LocalDateTime uploadedAt) { this.uploadedAt = uploadedAt; }

    public User getStudent() { return student; }
    public void setStudent(User student) { this.student = student; }

    public User getParent() { return parent; }
    public void setParent(User parent) { this.parent = parent; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public boolean isValidated() { return validated; }
    public void setValidated(boolean validated) { this.validated = validated; }

    public User getValidatedBy() { return validatedBy; }
    public void setValidatedBy(User validatedBy) { this.validatedBy = validatedBy; }

    public LocalDateTime getValidatedAt() { return validatedAt; }
    public void setValidatedAt(LocalDateTime validatedAt) { this.validatedAt = validatedAt; }

    public LocalDateTime getExpiryDate() { return expiryDate; }
    public void setExpiryDate(LocalDateTime expiryDate) { this.expiryDate = expiryDate; }

    public boolean isConfidential() { return confidential; }
    public void setConfidential(boolean confidential) { this.confidential = confidential; }

    public Integer getVersionNumber() { return versionNumber; }
    public void setVersionNumber(Integer versionNumber) { this.versionNumber = versionNumber; }

    public Document getPreviousVersion() { return previousVersion; }
    public void setPreviousVersion(Document previousVersion) { this.previousVersion = previousVersion; }

    // Méthodes utilitaires
    public boolean isExpired() {
        return expiryDate != null && LocalDateTime.now().isAfter(expiryDate);
    }

    public boolean isImage() {
        return mimeType != null && mimeType.startsWith("image/");
    }

    public boolean isPDF() {
        return mimeType != null && mimeType.equals("application/pdf");
    }

    public String getFileExtension() {
        return filePath != null ? filePath.substring(filePath.lastIndexOf(".") + 1) : null;
    }

    public String getFormattedSize() {
        if (fileSize == null) return "0 B";
        final String[] units = new String[] { "B", "KB", "MB", "GB", "TB" };
        int digitGroups = (int) (Math.log10(fileSize) / Math.log10(1024));
        return String.format("%.1f %s", fileSize / Math.pow(1024, digitGroups), units[digitGroups]);
    }


    public Event getEvent() {
        return event;
    }

    public void setEvent(Event event) {
        this.event = event;
    }

}
