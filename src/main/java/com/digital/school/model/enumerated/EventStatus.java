package com.digital.school.model.enumerated;

/**
 * Représente le statut global d'un devoir tel que défini par le professeur.
 */
public enum EventStatus {
    /**  * Le devoir est en cours de préparation et n'est pas encore publié*/
    SCHEDULED,

    /*** Le devoir est publié et accessible aux étudiants.*/
    UPCOMING,

    /*** Le devoir est clôturé (la date d'échéance est passée).*/
    CLOSED,

    /** evennement complété */
    COMPLETED,

    DRAFT,

    CANCELED
}
