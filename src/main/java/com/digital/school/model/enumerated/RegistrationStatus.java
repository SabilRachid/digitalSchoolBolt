package com.digital.school.model.enumerated;

public enum RegistrationStatus {
    DRAFT,        // Brouillon : inscription en cours de création
    SUBMITTED,    // Soumise : inscription finalisée et envoyée pour révision
    REVIEWING,    // En révision : dossier en cours d'examen
    NEEDS_INFO,   // Besoin d'information : informations complémentaires demandées
    COMPLETED
}
