package com.digital.school.service;

import com.digital.school.converter.JsonAttributeConverter;
import com.digital.school.model.ChatMessage;
import com.digital.school.model.ChatParticipant;
import com.digital.school.model.Classe;
import com.digital.school.model.Subject;
import com.digital.school.model.User;
import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@Entity
@Table(name = "chat_rooms")
public class ChatRoom {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(columnDefinition = "TEXT")
    private String description;

    @ManyToOne
    @JoinColumn(name = "professor_id", nullable = false)
    private User professor;

    @ManyToOne
    @JoinColumn(name = "class_id")
    private Classe classe;

    @ManyToOne
    @JoinColumn(name = "subject_id")
    private Subject subject;

    @Column(name = "is_active")
    private boolean active = true;

    @Column(name = "created_at")
    private LocalDateTime createdAt = LocalDateTime.now();

    @Column(name = "closed_at")
    private LocalDateTime closedAt;

    @OneToMany(mappedBy = "room", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<ChatMessage> messages = new HashSet<>();

    @OneToMany(mappedBy = "room", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<ChatParticipant> participants = new HashSet<>();

    // Utilisation du convertisseur personnalisé pour persister le champ settings en JSON (type jsonb dans PostgreSQL)
    @Convert(converter = JsonAttributeConverter.class)
    @Column(columnDefinition = "jsonb")
    @org.hibernate.annotations.ColumnTransformer(write = "?::jsonb")
    private Map<String, Object> settings = Map.of(
            "allow_student_attachments", false,
            "allow_student_reactions", true,
            "moderation_enabled", true
    );

    // Getters et setters

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public User getProfessor() {
        return professor;
    }
    public void setProfessor(User professor) {
        this.professor = professor;
    }
    public Classe getClasse() {
        return classe;
    }
    public void setClasse(Classe classe) {
        this.classe = classe;
    }
    public Subject getSubject() {
        return subject;
    }
    public void setSubject(Subject subject) {
        this.subject = subject;
    }
    public boolean isActive() {
        return active;
    }
    public void setActive(boolean active) {
        this.active = active;
    }
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
    public LocalDateTime getClosedAt() {
        return closedAt;
    }
    public void setClosedAt(LocalDateTime closedAt) {
        this.closedAt = closedAt;
    }
    public Set<ChatMessage> getMessages() {
        return messages;
    }
    public void setMessages(Set<ChatMessage> messages) {
        this.messages = messages != null ? messages : new HashSet<>();
    }
    public Set<ChatParticipant> getParticipants() {
        return participants;
    }
    public void setParticipants(Set<ChatParticipant> participants) {
        this.participants = participants != null ? participants : new HashSet<>();
    }
    public Map<String, Object> getSettings() {
        return settings;
    }
    public void setSettings(Map<String, Object> settings) {
        this.settings = settings;
    }

    // Méthodes utilitaires

    public void addMessage(ChatMessage message) {
        messages.add(message);
        message.setRoom(this);
    }
    public void removeMessage(ChatMessage message) {
        messages.remove(message);
        message.setRoom(null);
    }
    public void addParticipant(ChatParticipant participant) {
        participants.add(participant);
        participant.setRoom(this);
    }
    public void removeParticipant(ChatParticipant participant) {
        participants.remove(participant);
        participant.setRoom(null);
    }
    public boolean isParticipant(User user) {
        return participants.stream()
                .anyMatch(p -> p.getUser().equals(user));
    }
    public boolean canSendMessage(User user) {
        if (!active) return false;
        if (user.equals(professor)) return true;
        return participants.stream()
                .filter(p -> p.getUser().equals(user))
                .findFirst()
                .map(p -> !p.isMuted() || (p.getMutedUntil() != null && p.getMutedUntil().isBefore(LocalDateTime.now())))
                .orElse(false);
    }
}
