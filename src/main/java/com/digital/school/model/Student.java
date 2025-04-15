package com.digital.school.model;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "students")
@PrimaryKeyJoinColumn(name = "user_id")
public class Student extends User {

    private String specialNeeds; // Besoins spécifiques

    @Column(name = "registration_date", nullable = false)
    private LocalDateTime registrationDate;

    @ManyToOne
    @JoinColumn(name = "parent_id")
    private Parent parent; // Premier parent de l'élève

    @ManyToOne
    @JoinColumn(name = "parent2_id")
    private Parent parent2; // Deuxième parent de l'élève

    @ManyToOne
    @JoinColumn(name = "class_id")
    private Classe classe; // Classe de l'élève

    @Column(name = "birth_date")
    private LocalDate birthDate;

    // Constructeur par défaut requis par JPA
    public Student() { }

     // Constructeur avec identifiant
    public Student(Long id) {
        this.id = id;
    }

    public String getSpecialNeeds() {
        return specialNeeds;
    }

    public void setSpecialNeeds(String specialNeeds) {
        this.specialNeeds = specialNeeds;
    }

    public Parent getParent() {
        return parent;
    }

    public void setParent(Parent parent) {
        this.parent = parent;
    }

    public Parent getParent2() {
        return parent2;
    }

    public void setParent2(Parent parent2) {
        this.parent2 = parent2;
    }

    public Classe getClasse() {
        return classe;
    }

    public void setClasse(Classe classe) {
        this.classe = classe;
    }

    public LocalDateTime getRegistrationDate() {
        return registrationDate;
    }

    public void setRegistrationDate(LocalDateTime registrationDate) {
        this.registrationDate = registrationDate;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    @Override
    public String toString() {
        return super.toString() + "Student{" +
                "specialNeeds='" + specialNeeds + '\'' +
                ", registrationDate=" + registrationDate +
                ", parent=" + parent +
                ", parent2=" + parent2 +
                ", classe=" + classe +
                ", birthDate=" + birthDate +
                '}';
    }
}
