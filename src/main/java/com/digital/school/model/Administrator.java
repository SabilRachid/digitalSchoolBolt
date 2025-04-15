package com.digital.school.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.PrimaryKeyJoinColumn;

@Entity
@Table(name = "admins")
@PrimaryKeyJoinColumn(name = "user_id")
public class Administrator extends User {
     // Vous pouvez ajouter des attributs spécifiques à l'administrateur ici si nécessaire
}
