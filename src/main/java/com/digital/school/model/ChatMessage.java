package com.digital.school.model;

import com.digital.school.converter.JsonAttributeConverter;
import com.digital.school.converter.JsonListConverter;
import com.digital.school.dto.ChatMessageDTO;
import com.digital.school.service.ChatRoom;
import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.Set;

@SqlResultSetMapping(
        name = "ChatMessageDTOMapping",
        classes = @ConstructorResult(
                targetClass = com.digital.school.dto.ChatMessageDTO.class,
                columns = {
                        @ColumnResult(name = "id", type = Long.class),
                        @ColumnResult(name = "content", type = String.class),
                        @ColumnResult(name = "attachmentUrl", type = String.class),
                        @ColumnResult(name = "attachmentType", type = String.class),
                        @ColumnResult(name = "isPinned", type = Boolean.class),
                        @ColumnResult(name = "createdAt", type = LocalDateTime.class),
                        @ColumnResult(name = "parentId", type = Long.class),
                        @ColumnResult(name = "reactions", type = String.class), // ou adapter le type selon votre usage
                        @ColumnResult(name = "senderName", type = String.class),
                        @ColumnResult(name = "isProfessor", type = Boolean.class)
                }
        )
)
@NamedNativeQuery(
        name = "ChatMessageRepository.findMessages",
        query = """
        SELECT 
            m.id,
            m.content,
            m.attachment_url as attachmentUrl,
            m.attachment_type as attachmentType,
            m.is_pinned as isPinned,
            m.created_at as createdAt,
            m.parent_id as parentId,
            m.reactions,
            u.first_name || ' ' || u.last_name as senderName,
            CASE 
                WHEN r.name = 'ROLE_PROFESSOR' THEN true
                ELSE false
            END as isProfessor
        FROM chat_messages m
        JOIN users u ON m.sender_id = u.id
        JOIN user_roles ur ON u.id = ur.user_id
        JOIN roles r ON ur.role_id = r.id
        WHERE m.room_id = :roomId
        AND (:lastMessageId IS NULL OR m.id < :lastMessageId)
        ORDER BY m.created_at DESC
        LIMIT :limit
        """,
        resultSetMapping = "ChatMessageDTOMapping"
)
@Entity
@Table(name = "chat_messages")
public class ChatMessage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "room_id", nullable = false)
    private ChatRoom room;

    @ManyToOne
    @JoinColumn(name = "sender_id", nullable = false)
    private User sender;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String content;

    @Column(name = "attachment_url")
    private String attachmentUrl;

    @Column(name = "attachment_type")
    private String attachmentType;

    @Column(name = "is_pinned")
    private boolean pinned = false;

    @Column(name = "is_edited")
    private boolean edited = false;

    @Column(name = "edited_at")
    private LocalDateTime editedAt;

    @Column(name = "created_at")
    private LocalDateTime createdAt = LocalDateTime.now();

    @ManyToOne
    @JoinColumn(name = "parent_id")
    private ChatMessage parent;

    @OneToMany(mappedBy = "parent")
    private Set<ChatMessage> replies = new HashSet<>();

    // Utilisation de notre convertisseur personnalisé pour une liste de chaînes
    @Convert(converter = JsonListConverter.class)
    @Column(columnDefinition = "jsonb")
    @org.hibernate.annotations.ColumnTransformer(write = "?::jsonb")
    private List<String> reactions = List.of();

    @Convert(converter = JsonAttributeConverter.class)
    @Column(columnDefinition = "jsonb")
    @org.hibernate.annotations.ColumnTransformer(write = "?::jsonb")
    private Map<String, Object> metadata = new HashMap<>();
    // Getters et setters

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
    public User getSender() {
        return sender;
    }
    public void setSender(User sender) {
        this.sender = sender;
    }
    public String getContent() {
        return content;
    }
    public void setContent(String content) {
        this.content = content;
    }
    public String getAttachmentUrl() {
        return attachmentUrl;
    }
    public void setAttachmentUrl(String attachmentUrl) {
        this.attachmentUrl = attachmentUrl;
    }
    public String getAttachmentType() {
        return attachmentType;
    }
    public void setAttachmentType(String attachmentType) {
        this.attachmentType = attachmentType;
    }
    public boolean isPinned() {
        return pinned;
    }
    public void setPinned(boolean pinned) {
        this.pinned = pinned;
    }
    public boolean isEdited() {
        return edited;
    }
    public void setEdited(boolean edited) {
        this.edited = edited;
    }
    public LocalDateTime getEditedAt() {
        return editedAt;
    }
    public void setEditedAt(LocalDateTime editedAt) {
        this.editedAt = editedAt;
    }
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
    public ChatMessage getParent() {
        return parent;
    }
    public void setParent(ChatMessage parent) {
        this.parent = parent;
    }
    public Set<ChatMessage> getReplies() {
        return replies;
    }
    public void setReplies(Set<ChatMessage> replies) {
        this.replies = replies != null ? replies : new HashSet<>();
    }
    public List<String> getReactions() {
        return reactions;
    }
    public void setReactions(List<String> reactions) {
        this.reactions = reactions;
    }
    public Map<String, Object> getMetadata() {
        return metadata;
    }
    public void setMetadata(Map<String, Object> metadata) {
        this.metadata = metadata;
    }

    // Méthodes utilitaires
    public void addReply(ChatMessage reply) {
        replies.add(reply);
        reply.setParent(this);
    }

    public void removeReply(ChatMessage reply) {
        replies.remove(reply);
        reply.setParent(null);
    }

    public void edit(String newContent) {
        this.content = newContent;
        this.edited = true;
        this.editedAt = LocalDateTime.now();
    }

    public boolean canEdit(User user) {
        return user.equals(sender) &&
                !pinned &&
                LocalDateTime.now().minusHours(24).isBefore(createdAt);
    }

    public boolean canDelete(User user) {
        return user.equals(sender) ||
                user.equals(room.getProfessor()) ||
                user.getRoles().stream().anyMatch(r -> r.getName().toString().equals("ROLE_ADMIN"));
    }


}
