-- Insertion des événements de test
INSERT INTO events (title, description, start_time, end_time, location, type, background_color, text_color, all_day)
VALUES 
('Cours de Mathématiques', 'Chapitre sur les équations différentielles', '2024-01-15 08:00:00', '2024-01-15 10:00:00', 'Salle 101', 'COURSE', '#4C51BF', '#FFFFFF', false),
('Examen de Physique', 'Contrôle sur la mécanique quantique', '2024-01-16 14:00:00', '2024-01-16 16:00:00', 'Amphithéâtre A', 'EXAM', '#ED8936', '#FFFFFF', false),
('Réunion des délégués', 'Préparation des événements du trimestre', '2024-01-17 12:00:00', '2024-01-17 13:00:00', 'Salle de réunion', 'MEETING', '#4299E1', '#FFFFFF', false),
('Journée portes ouvertes', 'Présentation de l''école aux futurs étudiants', '2024-01-20 09:00:00', '2024-01-20 17:00:00', 'Campus', 'EVENT', '#48BB78', '#FFFFFF', true),
('Cours de Français', 'Littérature du XVIIIe siècle', '2024-01-15 10:30:00', '2024-01-15 12:30:00', 'Salle 203', 'COURSE', '#4C51BF', '#FFFFFF', false),
('TP de Chimie', 'Travaux pratiques sur les réactions organiques', '2024-01-16 08:00:00', '2024-01-16 11:00:00', 'Laboratoire 2', 'COURSE', '#4C51BF', '#FFFFFF', false),
('Conseil de classe', 'Bilan du premier trimestre', '2024-01-18 17:00:00', '2024-01-18 19:00:00', 'Salle des professeurs', 'MEETING', '#4299E1', '#FFFFFF', false),
('Examen d''Anglais', 'Test de compréhension orale', '2024-01-19 10:00:00', '2024-01-19 11:30:00', 'Salle 105', 'EXAM', '#ED8936', '#FFFFFF', false);

-- Ajout des participants aux événements (à adapter selon vos utilisateurs)
INSERT INTO event_participants (event_id, user_id)
SELECT e.id, u.id
FROM events e
CROSS JOIN users u
WHERE u.id = 1;  -- Remplacer par l'ID de l'utilisateur test