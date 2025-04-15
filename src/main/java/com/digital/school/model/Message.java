package com.digital.school.model;


import com.digital.school.config.school.SchoolAware;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import org.hibernate.annotations.Filter;

import java.time.LocalDateTime;
import java.util.*;

@Entity
@Table(name = "messages")
@Filter(name = "schoolFilter", condition = "school_id = :schoolId")
public class Message extends AuditableEntity implements SchoolAware {

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "school_id", nullable = false)
    private School school;

    @ManyToOne
    @JoinColumn(name = "sender_id", nullable = false)
    private User sender;

    @ManyToMany
    @JoinTable(
            name = "message_recipients",
            joinColumns = @JoinColumn(name = "message_id"),
            inverseJoinColumns = @JoinColumn(name = "recipient_id")
    )
    private Set<User> recipients = new HashSet<>();

    @Column(nullable = false)
    private String subject;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String content;

    @Column(name = "sent_at")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime sentAt = LocalDateTime.now();

    @ElementCollection
    @CollectionTable(name = "message_read_status")
    private Map<User, LocalDateTime> readStatus = new HashMap<>();

    @ElementCollection
    @CollectionTable(name = "message_labels")
    private Set<String> labels = new HashSet<>();

    @OneToMany(mappedBy = "parentMessage", cascade = CascadeType.ALL)
    private List<Message> replies = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "parent_message_id")
    private Message parentMessage;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "message_attachments")
    private List<Document> attachments = new ArrayList<>();

    private boolean draft = false;
    private boolean archived = false;
    private boolean starred = false;
    private boolean important = false;

    // Getters and setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    @Override
    public School getSchool() { return school; }
    @Override
    public void setSchool(School school) { this.school = school; }

    public User getSender() { return sender; }
    public void setSender(User sender) { this.sender = sender; }

    public Set<User> getRecipients() { return recipients; }
    public void setRecipients(Set<User> recipients) {
        this.recipients = recipients != null ? recipients : new HashSet<>();
    }

    public String getSubject() { return subject; }
    public void setSubject(String subject) { this.subject = subject; }

    public String getContent() { return content; }
    public void setContent(String content) { this.content = content; }

    public LocalDateTime getSentAt() { return sentAt; }
    public void setSentAt(LocalDateTime sentAt) { this.sentAt = sentAt; }

    public Map<User, LocalDateTime> getReadStatus() { return readStatus; }
    public void setReadStatus(Map<User, LocalDateTime> readStatus) {
        this.readStatus = readStatus != null ? readStatus : new HashMap<>();
    }

    public Set<String> getLabels() { return labels; }
    public void setLabels(Set<String> labels) {
        this.labels = labels != null ? labels : new HashSet<>();
    }

    public List<Message> getReplies() { return replies; }
    public void setReplies(List<Message> replies) {
        this.replies = replies != null ? replies : new ArrayList<>();
    }

    public Message getParentMessage() { return parentMessage; }
    public void setParentMessage(Message parentMessage) { this.parentMessage = parentMessage; }

    public List<Document> getAttachments() { return attachments; }
    public void setAttachments(List<Document> attachments) {
        this.attachments = attachments != null ? attachments : new ArrayList<>();
    }

    public boolean isDraft() { return draft; }
    public void setDraft(boolean draft) { this.draft = draft; }

    public boolean isArchived() { return archived; }
    public void setArchived(boolean archived) { this.archived = archived; }

    public boolean isStarred() { return starred; }
    public void setStarred(boolean starred) { this.starred = starred; }

    public boolean isImportant() { return important; }
    public void setImportant(boolean important) { this.important = important; }
}
