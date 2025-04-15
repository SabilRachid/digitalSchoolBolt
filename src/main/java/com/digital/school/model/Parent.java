package com.digital.school.model;

import jakarta.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "parents")
@PrimaryKeyJoinColumn(name = "user_id")
public class Parent extends User {

    @OneToMany(mappedBy = "parent")
    private Set<Student> children = new HashSet<>(); // Liste des enfants

    @Column(name = "primary_contact", nullable = false)
    private boolean primaryContact = false; // Détermine si ce parent est le contact principal

    // Getters et setters pour children
    public Set<Student> getChildren() {
        return children;
    }
    public void setChildren(Set<Student> children) {
        this.children = children;
    }

    // Getters et setters pour primaryContact
    public boolean isPrimaryContact() {
        return primaryContact;
    }
    public void setPrimaryContact(boolean primaryContact) {
        this.primaryContact = primaryContact;
    }

    @Override
    public String toString() {
        return "return super.toString()+ + Parent{" +
                ", primaryContact=" + primaryContact +
                '}';
    }
}
