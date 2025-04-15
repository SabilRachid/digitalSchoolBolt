package com.digital.school.model;

import com.digital.school.model.enumerated.MeetingCategory;
import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "meetings")
public class Meeting extends Event {

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private MeetingCategory  category;

    @ManyToOne
    @JoinColumn(name = "organizer_id")
    private User organizer;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(name = "meeting_link")
    private String meetingLink;

    @Column(name = "last_reminder_sent")
    private LocalDateTime lastReminderSent;

    @Column(name = "reminder_count")
    private Integer reminderCount = 0;

    public Meeting() {
    }

    public MeetingCategory getCategory() {
        return category;
    }

    public void setCategory(MeetingCategory category) {
        this.category = category;
    }

    public User getOrganizer() {
        return organizer;
    }

    public void setOrganizer(User organizer) {
        this.organizer = organizer;
    }

    @Override
    public String getDescription() {
        return description;
    }

    @Override
    public void setDescription(String description) {
        this.description = description;
    }

    public String getMeetingLink() {
        return meetingLink;
    }

    public void setMeetingLink(String meetingLink) {
        this.meetingLink = meetingLink;
    }

    public LocalDateTime getLastReminderSent() {
        return lastReminderSent;
    }

    public void setLastReminderSent(LocalDateTime lastReminderSent) {
        this.lastReminderSent = lastReminderSent;
    }

    public Integer getReminderCount() {
        return reminderCount;
    }

    public void setReminderCount(Integer reminderCount) {
        this.reminderCount = reminderCount;
    }

    @Override
    public String toString() {
        return "Meeting{" +
                "category=" + category +
                ", organizer=" + organizer +
                ", description='" + description + '\'' +
                ", meetingLink='" + meetingLink + '\'' +
                ", lastReminderSent=" + lastReminderSent +
                ", reminderCount=" + reminderCount +
                "} " + super.toString();
    }
}
