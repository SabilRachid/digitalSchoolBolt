```java
package com.digital.school.model;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Map;

@Entity
@Table(name = "subscription_plans")
public class SubscriptionPlan {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false)
    private String name;
    
    @Column(nullable = false, unique = true)
    private String code;
    
    private String description;
    
    @Column(nullable = false)
    private BigDecimal price;
    
    @Column(name = "duration_months", nullable = false)
    private Integer durationMonths;
    
    @Column(name = "max_students")
    private Integer maxStudents;
    
    @Column(name = "max_teachers")
    private Integer maxTeachers;
    
    // Pour H2, on utilise un VARCHAR pour stocker le JSON
    @Column(columnDefinition = "VARCHAR(4000)")
    private String features;
    
    @Column(name = "is_active")
    private boolean active = true;
    
    @Column(name = "created_at")
    private LocalDateTime createdAt = LocalDateTime.now();
    
    @Column(name = "updated_at")
    private LocalDateTime updatedAt = LocalDateTime.now();

    // Getters and setters avec conversion JSON pour features
    public Map<String, Object> getFeatures() {
        if (features == null) return null;
        try {
            return new com.fasterxml.jackson.databind.ObjectMapper().readValue(features, Map.class);
        } catch (Exception e) {
            return null;
        }
    }

    public void setFeatures(Map<String, Object> features) {
        try {
            this.features = new com.fasterxml.jackson.databind.ObjectMapper().writeValueAsString(features);
        } catch (Exception e) {
            this.features = null;
        }
    }

    // Autres getters et setters...
}
```