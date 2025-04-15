package com.digital.school.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "shared_documents")
public class SharedDocument {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne
    @JoinColumn(name = "space_id", nullable = false)
    private DocumentSpace space;
    
    @Column(nullable = false)
    private String name;
    
    private String description;
    
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
    
    @Column(name = "last_modified_at")
    private LocalDateTime lastModifiedAt = LocalDateTime.now();
    
    @ManyToOne
    @JoinColumn(name = "last_modified_by_id")
    private User lastModifiedBy;
    
    @ManyToOne
    @JoinColumn(name = "parent_folder_id")
    private SharedDocument parentFolder;
    
    @Column(name = "is_folder")
    private boolean folder = false;
    
    @ElementCollection
    @CollectionTable(name = "document_tags", 
                    joinColumns = @JoinColumn(name = "document_id"))
    @Column(name = "tag")
    private Set<String> tags = new HashSet<>();
    
    private Integer version = 1;
    
    @Column(name = "is_archived")
    private boolean archived = false;
    
    @OneToMany(mappedBy = "document", cascade = CascadeType.ALL)
    private Set<DocumentVersion> versions = new HashSet<>();
    
    @OneToMany(mappedBy = "document", cascade = CascadeType.ALL)
    private Set<DocumentComment> comments = new HashSet<>();

    // Getters and setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public DocumentSpace getSpace() {
        return space;
    }

    public void setSpace(DocumentSpace space) {
        this.space = space;
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

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public String getMimeType() {
        return mimeType;
    }

    public void setMimeType(String mimeType) {
        this.mimeType = mimeType;
    }

    public Long getFileSize() {
        return fileSize;
    }

    public void setFileSize(Long fileSize) {
        this.fileSize = fileSize;
    }

    public User getUploadedBy() {
        return uploadedBy;
    }

    public void setUploadedBy(User uploadedBy) {
        this.uploadedBy = uploadedBy;
    }

    public LocalDateTime getUploadedAt() {
        return uploadedAt;
    }

    public void setUploadedAt(LocalDateTime uploadedAt) {
        this.uploadedAt = uploadedAt;
    }

    public LocalDateTime getLastModifiedAt() {
        return lastModifiedAt;
    }

    public void setLastModifiedAt(LocalDateTime lastModifiedAt) {
        this.lastModifiedAt = lastModifiedAt;
    }

    public User getLastModifiedBy() {
        return lastModifiedBy;
    }

    public void setLastModifiedBy(User lastModifiedBy) {
        this.lastModifiedBy = lastModifiedBy;
    }

    public SharedDocument getParentFolder() {
        return parentFolder;
    }

    public void setParentFolder(SharedDocument parentFolder) {
        this.parentFolder = parentFolder;
    }

    public boolean isFolder() {
        return folder;
    }

    public void setFolder(boolean folder) {
        this.folder = folder;
    }

    public Set<String> getTags() {
        return tags;
    }

    public void setTags(Set<String> tags) {
        this.tags = tags != null ? tags : new HashSet<>();
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    public boolean isArchived() {
        return archived;
    }

    public void setArchived(boolean archived) {
        this.archived = archived;
    }

    public Set<DocumentVersion> getVersions() {
        return versions;
    }

    public void setVersions(Set<DocumentVersion> versions) {
        this.versions = versions != null ? versions : new HashSet<>();
    }

    public Set<DocumentComment> getComments() {
        return comments;
    }

    public void setComments(Set<DocumentComment> comments) {
        this.comments = comments != null ? comments : new HashSet<>();
    }
}