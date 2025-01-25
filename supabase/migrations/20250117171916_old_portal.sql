/*
  # Ajout des données de test pour le calendrier

  1. Contenu
    - Ajout d'événements de test pour le calendrier
    - Différents types d'événements : cours, examens, réunions, événements
    - Période : janvier-février 2024
*/

-- Insertion des événements de test
INSERT INTO events (title, description, start_time, end_time, location, type, background_color, text_color, all_day)
VALUES 
-- Cours réguliers
('Mathématiques - 3ème A', 'Chapitre sur les équations', '2024-01-22 08:00:00', '2024-01-22 10:00:00', 'Salle 101', 'COURSE', '#4C51BF', '#FFFFFF', false),
('Français - 4ème B', 'Littérature classique', '2024-01-22 10:30:00', '2024-01-22 12:30:00', 'Salle 203', 'COURSE', '#4C51BF', '#FFFFFF', false),
('Physique-Chimie - 3ème A', 'TP sur l''électricité', '2024-01-23 14:00:00', '2024-01-23 16:00:00', 'Labo 2', 'COURSE', '#4C51BF', '#FFFFFF', false),
('Histoire-Géo - 5ème C', 'La Renaissance', '2024-01-24 08:00:00', '2024-01-24 10:00:00', 'Salle 105', 'COURSE', '#4C51BF', '#FFFFFF', false),

-- Examens
('Contrôle de Maths - 3ème A', 'Évaluation sur les équations', '2024-01-29 09:00:00', '2024-01-29 11:00:00', 'Salle 301', 'EXAM', '#ED8936', '#FFFFFF', false),
('Devoir Français - 4ème B', 'Dissertation', '2024-01-30 14:00:00', '2024-01-30 16:00:00', 'Salle 202', 'EXAM', '#ED8936', '#FFFFFF', false),
('Évaluation SVT - 5ème C', 'Contrôle sur la reproduction', '2024-01-31 10:00:00', '2024-01-31 11:00:00', 'Salle 103', 'EXAM', '#ED8936', '#FFFFFF', false),

-- Réunions
('Conseil de classe - 3ème A', 'Bilan du 2ème trimestre', '2024-01-25 17:00:00', '2024-01-25 19:00:00', 'Salle des profs', 'MEETING', '#4299E1', '#FFFFFF', false),
('Réunion parents-profs', 'Rencontre trimestrielle', '2024-01-26 16:30:00', '2024-01-26 20:00:00', 'Amphithéâtre', 'MEETING', '#4299E1', '#FFFFFF', false),
('Réunion pédagogique', 'Préparation examens blancs', '2024-01-24 17:00:00', '2024-01-24 18:30:00', 'Salle de réunion', 'MEETING', '#4299E1', '#FFFFFF', false),

-- Événements spéciaux
('Journée portes ouvertes', 'Présentation de l''établissement', '2024-02-03 09:00:00', '2024-02-03 17:00:00', 'Tout l''établissement', 'EVENT', '#48BB78', '#FFFFFF', true),
('Forum des métiers', 'Orientation post-brevet', '2024-02-10 09:00:00', '2024-02-10 16:00:00', 'Gymnase', 'EVENT', '#48BB78', '#FFFFFF', true),
('Sortie scolaire - 4ème B', 'Visite du musée d''histoire', '2024-02-15 08:30:00', '2024-02-15 16:30:00', 'Musée municipal', 'EVENT', '#48BB78', '#FFFFFF', true);

-- Ajout des participants (à adapter selon vos utilisateurs)
INSERT INTO event_participants (event_id, user_id)
SELECT e.id, u.id
FROM events e
CROSS JOIN users u
WHERE u.id IN (1, 2, 3)  -- Remplacer par les IDs de vos utilisateurs tests
AND (
    (e.type = 'COURSE' AND u.id IN (SELECT id FROM users WHERE roles @> ARRAY['ROLE_PROFESSOR']::varchar[]))
    OR
    (e.type = 'MEETING' AND u.id IN (SELECT id FROM users WHERE roles @> ARRAY['ROLE_PROFESSOR', 'ROLE_ADMIN']::varchar[]))
    OR
    (e.type IN ('EVENT', 'EXAM'))
);