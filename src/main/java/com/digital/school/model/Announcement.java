package com.digital.school.model;

import com.digital.school.config.school.SchoolAware;
import jakarta.persistence.*;
import org.hibernate.annotations.Filter;

import java.time.LocalDateTime;
import java.util.*;

@Entity
@Table(name = "announcements")
@Filter(name = "schoolFilter", condition = "school_id = :schoolId")
public class Announcement extends AuditableEntity implements SchoolAware {

    public enum AnnouncementType {
        ALERT,      // Urgence, notification immédiate requise
        WARNING,    // Avertissement important mais non urgent
        INFO       // Information générale
    }

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "school_id", nullable = false)
    private School school;

    @Column(nullable = false)
    private String title;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String content;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private AnnouncementType type;

    @ManyToOne
    @JoinColumn(name = "author_id", nullable = false)
    private User author;

    @Column(name = "published_at")
    private LocalDateTime publishedAt = LocalDateTime.now();

    @Column(name = "expires_at")
    private LocalDateTime expiresAt;

    @Column(name = "is_pinned")
    private boolean pinned = false;

    @Column(name = "requires_acknowledgment")
    private boolean requiresAcknowledgment = false;

    @ElementCollection
    @CollectionTable(name = "announcement_acknowledgments")
    private Set<User> acknowledgedBy = new HashSet<>();

    @ManyToMany
    @JoinTable(
            name = "announcement_target_roles",
            joinColumns = @JoinColumn(name = "announcement_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private Set<Role> targetRoles = new HashSet<>();

    @ManyToMany
    @JoinTable(
            name = "announcement_target_classes",
            joinColumns = @JoinColumn(name = "announcement_id"),
            inverseJoinColumns = @JoinColumn(name = "class_id")
    )
    private Set<Classe> targetClasses = new HashSet<>();

    @OneToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "announcement_attachments")
    private List<Document> attachments = new ArrayList<>();

    @ElementCollection
    @CollectionTable(name = "announcement_tags")
    private Set<String> tags = new HashSet<>();

    // Getters and setters

    @Override
    public School getSchool() { return school; }
    @Override
    public void setSchool(School school) { this.school = school; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getContent() { return content; }
    public void setContent(String content) { this.content = content; }

    public AnnouncementType getType() { return type; }
    public void setType(AnnouncementType type) { this.type = type; }

    public User getAuthor() { return author; }
    public void setAuthor(User author) { this.author = author; }

    public LocalDateTime getPublishedAt() { return publishedAt; }
    public void setPublishedAt(LocalDateTime publishedAt) { this.publishedAt = publishedAt; }

    public LocalDateTime getExpiresAt() { return expiresAt; }
    public void setExpiresAt(LocalDateTime expiresAt) { this.expiresAt = expiresAt; }

    public boolean isPinned() { return pinned; }
    public void setPinned(boolean pinned) { this.pinned = pinned; }

    public boolean isRequiresAcknowledgment() { return requiresAcknowledgment; }
    public void setRequiresAcknowledgment(boolean requiresAcknowledgment) {
        this.requiresAcknowledgment = requiresAcknowledgment;
    }

    public Set<User> getAcknowledgedBy() { return acknowledgedBy; }
    public void setAcknowledgedBy(Set<User> acknowledgedBy) {
        this.acknowledgedBy = acknowledgedBy != null ? acknowledgedBy : new HashSet<>();
    }

    public Set<Role> getTargetRoles() { return targetRoles; }
    public void setTargetRoles(Set<Role> targetRoles) {
        this.targetRoles = targetRoles != null ? targetRoles : new HashSet<>();
    }

    public Set<Classe> getTargetClasses() { return targetClasses; }
    public void setTargetClasses(Set<Classe> targetClasses) {
        this.targetClasses = targetClasses != null ? targetClasses : new HashSet<>();
    }

    public List<Document> getAttachments() { return attachments; }
    public void setAttachments(List<Document> attachments) {
        this.attachments = attachments != null ? attachments : new ArrayList<>();
    }

    public Set<String> getTags() { return tags; }
    public void setTags(Set<String> tags) {
        this.tags = tags != null ? tags : new HashSet<>();
    }
}
