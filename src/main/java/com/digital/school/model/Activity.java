package com.digital.school.model;

import com.digital.school.config.school.SchoolAware;
import jakarta.persistence.*;
import org.hibernate.annotations.Filter;

import java.time.LocalDateTime;
import java.util.*;

@Entity
@Table(name = "activities")
@Filter(name = "schoolFilter", condition = "school_id = :schoolId")
public class Activity extends AuditableEntity implements SchoolAware {

    public enum ActivityType {
        SPORT,          // Activités sportives
        CULTURE,        // Activités culturelles
        ACADEMIC,       // Soutien scolaire
        ARTISTIC,       // Arts plastiques, musique, théâtre
        TECHNOLOGY,     // Clubs technologiques
        LANGUAGE,       // Clubs de langues
        COMMUNITY,      // Actions communautaires
        OTHER          // Autres activités
    }

    public enum ActivityStatus {
        DRAFT,          // En cours de création
        PUBLISHED,      // Ouverte aux inscriptions
        FULL,           // Complet
        ONGOING,        // En cours
        COMPLETED,      // Terminée
        CANCELLED      // Annulée
    }

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "school_id", nullable = false)
    private School school;

    @Column(nullable = false)
    private String name;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ActivityType type;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ActivityStatus status = ActivityStatus.DRAFT;

    @ManyToOne
    @JoinColumn(name = "organizer_id", nullable = false)
    private User organizer;

    @ManyToMany
    @JoinTable(
            name = "activity_supervisors",
            joinColumns = @JoinColumn(name = "activity_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    private Set<User> supervisors = new HashSet<>();

    @ManyToMany
    @JoinTable(
            name = "activity_participants",
            joinColumns = @JoinColumn(name = "activity_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    private Set<User> participants = new HashSet<>();

    @Column(name = "min_participants")
    private Integer minParticipants;

    @Column(name = "max_participants")
    private Integer maxParticipants;

    @Column(name = "min_age")
    private Integer minAge;

    @Column(name = "max_age")
    private Integer maxAge;

    @ManyToMany
    @JoinTable(
            name = "activity_target_levels",
            joinColumns = @JoinColumn(name = "activity_id"),
            inverseJoinColumns = @JoinColumn(name = "level_id")
    )
    private Set<Level> targetLevels = new HashSet<>();

    @Column(name = "start_date", nullable = false)
    private LocalDateTime startDate;

    @Column(name = "end_date", nullable = false)
    private LocalDateTime endDate;

    @Column(name = "registration_deadline")
    private LocalDateTime registrationDeadline;

    private String location;

    @Column(name = "equipment_needed", columnDefinition = "TEXT")
    private String equipmentNeeded;

    @Column(name = "additional_info", columnDefinition = "TEXT")
    private String additionalInfo;

    @Column(name = "created_at")
    private LocalDateTime createdAt = LocalDateTime.now();

    @Column(name = "updated_at")
    private LocalDateTime updatedAt = LocalDateTime.now();

    @OneToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "activity_documents")
    private List<Document> documents = new ArrayList<>();

    @ElementCollection
    @CollectionTable(name = "activity_schedule")
    private Set<ActivitySession> schedule = new HashSet<>();

    @Column(name = "has_fee")
    private boolean hasFee = false;

    @Column(name = "fee_amount")
    private Double feeAmount;

    @Column(name = "fee_description")
    private String feeDescription;

    @ElementCollection
    @CollectionTable(name = "activity_tags")
    private Set<String> tags = new HashSet<>();

    @OneToMany(mappedBy = "activity", cascade = CascadeType.ALL)
    private List<ActivityRegistration> registrations = new ArrayList<>();

    @Override
    public School getSchool() { return school; }
    @Override
    public void setSchool(School school) { this.school = school; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public ActivityType getType() { return type; }
    public void setType(ActivityType type) { this.type = type; }

    public ActivityStatus getStatus() { return status; }
    public void setStatus(ActivityStatus status) { this.status = status; }

    public User getOrganizer() { return organizer; }
    public void setOrganizer(User organizer) { this.organizer = organizer; }

    public Set<User> getSupervisors() { return supervisors; }
    public void setSupervisors(Set<User> supervisors) {
        this.supervisors = supervisors != null ? supervisors : new HashSet<>();
    }

    public Set<User> getParticipants() { return participants; }
    public void setParticipants(Set<User> participants) {
        this.participants = participants != null ? participants : new HashSet<>();
    }

    public Integer getMinParticipants() { return minParticipants; }
    public void setMinParticipants(Integer minParticipants) { this.minParticipants = minParticipants; }

    public Integer getMaxParticipants() { return maxParticipants; }
    public void setMaxParticipants(Integer maxParticipants) { this.maxParticipants = maxParticipants; }

    public Integer getMinAge() { return minAge; }
    public void setMinAge(Integer minAge) { this.minAge = minAge; }

    public Integer getMaxAge() { return maxAge; }
    public void setMaxAge(Integer maxAge) { this.maxAge = maxAge; }

    public Set<Level> getTargetLevels() { return targetLevels; }
    public void setTargetLevels(Set<Level> targetLevels) {
        this.targetLevels = targetLevels != null ? targetLevels : new HashSet<>();
    }

    public LocalDateTime getStartDate() { return startDate; }
    public void setStartDate(LocalDateTime startDate) { this.startDate = startDate; }

    public LocalDateTime getEndDate() { return endDate; }
    public void setEndDate(LocalDateTime endDate) { this.endDate = endDate; }

    public LocalDateTime getRegistrationDeadline() { return registrationDeadline; }
    public void setRegistrationDeadline(LocalDateTime registrationDeadline) {
        this.registrationDeadline = registrationDeadline;
    }

    public String getLocation() { return location; }
    public void setLocation(String location) { this.location = location; }

    public String getEquipmentNeeded() { return equipmentNeeded; }
    public void setEquipmentNeeded(String equipmentNeeded) { this.equipmentNeeded = equipmentNeeded; }

    public String getAdditionalInfo() { return additionalInfo; }
    public void setAdditionalInfo(String additionalInfo) { this.additionalInfo = additionalInfo; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }

    public List<Document> getDocuments() { return documents; }
    public void setDocuments(List<Document> documents) {
        this.documents = documents != null ? documents : new ArrayList<>();
    }

    public Set<ActivitySession> getSchedule() { return schedule; }
    public void setSchedule(Set<ActivitySession> schedule) {
        this.schedule = schedule != null ? schedule : new HashSet<>();
    }

    public boolean isHasFee() { return hasFee; }
    public void setHasFee(boolean hasFee) { this.hasFee = hasFee; }

    public Double getFeeAmount() { return feeAmount; }
    public void setFeeAmount(Double feeAmount) { this.feeAmount = feeAmount; }

    public String getFeeDescription() { return feeDescription; }
    public void setFeeDescription(String feeDescription) { this.feeDescription = feeDescription; }

    public Set<String> getTags() { return tags; }
    public void setTags(Set<String> tags) {
        this.tags = tags != null ? tags : new HashSet<>();
    }

    public List<ActivityRegistration> getRegistrations() { return registrations; }
    public void setRegistrations(List<ActivityRegistration> registrations) {
        this.registrations = registrations != null ? registrations : new ArrayList<>();
    }

    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }

    @Override
    public String toString() {
        return "Activity{" +
                "school=" + school +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", type=" + type +
                ", status=" + status +
                ", organizer=" + organizer +
                ", supervisors=" + supervisors +
                ", participants=" + participants +
                ", minParticipants=" + minParticipants +
                ", maxParticipants=" + maxParticipants +
                ", minAge=" + minAge +
                ", maxAge=" + maxAge +
                ", targetLevels=" + targetLevels +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                ", registrationDeadline=" + registrationDeadline +
                ", location='" + location + '\'' +
                ", equipmentNeeded='" + equipmentNeeded + '\'' +
                ", additionalInfo='" + additionalInfo + '\'' +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                ", documents=" + documents +
                ", schedule=" + schedule +
                ", hasFee=" + hasFee +
                ", feeAmount=" + feeAmount +
                ", feeDescription='" + feeDescription + '\'' +
                ", tags=" + tags +
                ", registrations=" + registrations +
                "} " + super.toString();
    }


}
