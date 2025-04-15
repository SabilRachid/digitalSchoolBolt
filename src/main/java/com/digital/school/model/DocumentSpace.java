package com.digital.school.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "document_spaces")
public class DocumentSpace {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false)
    private String name;
    
    private String description;
    
    @Column(name = "space_type", nullable = false)
    private String spaceType;
    
    @Column(name = "reference_id")
    private Long referenceId;
    
    @ManyToOne
    @JoinColumn(name = "created_by_id")
    private User createdBy;
    
    @Column(name = "created_at")
    private LocalDateTime createdAt = LocalDateTime.now();
    
    @Column(name = "updated_at")
    private LocalDateTime updatedAt = LocalDateTime.now();
    
    @Column(name = "is_archived")
    private boolean archived = false;
    
    @OneToMany(mappedBy = "space", cascade = CascadeType.ALL)
    private Set<DocumentSpaceMember> members = new HashSet<>();
    
    @OneToMany(mappedBy = "space", cascade = CascadeType.ALL)
    private Set<SharedDocument> documents = new HashSet<>();

    // Getters and setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getSpaceType() {
        return spaceType;
    }

    public void setSpaceType(String spaceType) {
        this.spaceType = spaceType;
    }

    public Long getReferenceId() {
        return referenceId;
    }

    public void setReferenceId(Long referenceId) {
        this.referenceId = referenceId;
    }

    public User getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(User createdBy) {
        this.createdBy = createdBy;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public boolean isArchived() {
        return archived;
    }

    public void setArchived(boolean archived) {
        this.archived = archived;
    }

    public Set<DocumentSpaceMember> getMembers() {
        return members;
    }

    public void setMembers(Set<DocumentSpaceMember> members) {
        this.members = members != null ? members : new HashSet<>();
    }

    public Set<SharedDocument> getDocuments() {
        return documents;
    }

    public void setDocuments(Set<SharedDocument> documents) {
        this.documents = documents != null ? documents : new HashSet<>();
    }

    @Override
    public String toString() {
        return "DocumentSpace{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", spaceType='" + spaceType + '\'' +
                ", referenceId=" + referenceId +
                ", createdBy=" + createdBy +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                ", archived=" + archived +
                ", members=" + members +
                ", documents=" + documents +
                '}';
    }
}