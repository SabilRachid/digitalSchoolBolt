package com.digital.school.model;

import com.digital.school.service.ChatRoom;
import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "chat_participants")
public class ChatParticipant {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne
    @JoinColumn(name = "room_id", nullable = false)
    private ChatRoom room;
    
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
    
    @Column(name = "joined_at")
    private LocalDateTime joinedAt = LocalDateTime.now();
    
    @Column(name = "last_read_at")
    private LocalDateTime lastReadAt = LocalDateTime.now();
    
    @Column(name = "is_muted")
    private boolean muted = false;
    
    @Column(name = "muted_until")
    private LocalDateTime mutedUntil;

    // Getters and setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ChatRoom getRoom() {
        return room;
    }

    public void setRoom(ChatRoom room) {
        this.room = room;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public LocalDateTime getJoinedAt() {
        return joinedAt;
    }

    public void setJoinedAt(LocalDateTime joinedAt) {
        this.joinedAt = joinedAt;
    }

    public LocalDateTime getLastReadAt() {
        return lastReadAt;
    }

    public void setLastReadAt(LocalDateTime lastReadAt) {
        this.lastReadAt = lastReadAt;
    }

    public boolean isMuted() {
        return muted;
    }

    public void setMuted(boolean muted) {
        this.muted = muted;
    }

    public LocalDateTime getMutedUntil() {
        return mutedUntil;
    }

    public void setMutedUntil(LocalDateTime mutedUntil) {
        this.mutedUntil = mutedUntil;
    }

    // Méthodes utilitaires
    public void updateLastRead() {
        this.lastReadAt = LocalDateTime.now();
    }

    public void mute(int minutes) {
        this.muted = true;
        this.mutedUntil = LocalDateTime.now().plusMinutes(minutes);
    }

    public void unmute() {
        this.muted = false;
        this.mutedUntil = null;
    }

    public boolean canSendMessages() {
        if (!muted) return true;
        return mutedUntil != null && mutedUntil.isBefore(LocalDateTime.now());
    }
}