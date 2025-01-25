/*
  # Ajout des tables pour la gestion scolaire

  1. Nouvelles Tables
    - `documents` : Stockage des documents
    - `rooms` : Gestion des salles
    - `parent_student_associations` : Associations parent-élève
    - `student_profiles` : Profils des élèves
    - `parent_profiles` : Profils des parents
    - `room_equipment` : Équipements des salles

  2. Sécurité
    - Enable RLS sur toutes les tables
    - Politiques d'accès basées sur les rôles
*/

-- Table des documents
CREATE TABLE IF NOT EXISTS documents (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    type VARCHAR(50) NOT NULL,
    category VARCHAR(50) NOT NULL,
    file_path TEXT NOT NULL,
    mime_type VARCHAR(100),
    file_size BIGINT,
    uploaded_by_id UUID REFERENCES auth.users(id),
    uploaded_at TIMESTAMPTZ DEFAULT NOW(),
    student_id UUID REFERENCES auth.users(id),
    parent_id UUID REFERENCES auth.users(id),
    description TEXT,
    is_validated BOOLEAN DEFAULT false,
    validated_by_id UUID REFERENCES auth.users(id),
    validated_at TIMESTAMPTZ
);

-- Table des salles
CREATE TABLE IF NOT EXISTS rooms (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(100) NOT NULL UNIQUE,
    max_capacity INTEGER,
    description TEXT,
    status VARCHAR(20) NOT NULL DEFAULT 'AVAILABLE',
    floor_number INTEGER,
    building_name VARCHAR(100),
    is_accessible BOOLEAN DEFAULT false,
    maintenance_notes TEXT
);

-- Table des équipements des salles
CREATE TABLE IF NOT EXISTS room_equipment (
    room_id BIGINT REFERENCES rooms(id),
    equipment VARCHAR(100) NOT NULL,
    PRIMARY KEY (room_id, equipment)
);

-- Table des associations parent-élève
CREATE TABLE IF NOT EXISTS parent_student_associations (
    id BIGSERIAL PRIMARY KEY,
    parent_id UUID NOT NULL REFERENCES auth.users(id),
    student_id UUID NOT NULL REFERENCES auth.users(id),
    relationship VARCHAR(50) NOT NULL,
    is_primary_contact BOOLEAN DEFAULT false,
    has_custody BOOLEAN DEFAULT true,
    emergency_contact BOOLEAN DEFAULT false,
    created_at TIMESTAMPTZ DEFAULT NOW(),
    created_by_id UUID REFERENCES auth.users(id),
    is_validated BOOLEAN DEFAULT false,
    validated_by_id UUID REFERENCES auth.users(id),
    validated_at TIMESTAMPTZ,
    UNIQUE(parent_id, student_id)
);

-- Table des profils élèves
CREATE TABLE IF NOT EXISTS student_profiles (
    id BIGSERIAL PRIMARY KEY,
    student_id UUID NOT NULL UNIQUE REFERENCES auth.users(id),
    birth_date DATE,
    birth_place VARCHAR(100),
    nationality VARCHAR(100),
    gender VARCHAR(20),
    photo_path TEXT,
    medical_info TEXT,
    emergency_contact VARCHAR(100),
    emergency_phone VARCHAR(20),
    blood_type VARCHAR(10),
    has_allergies BOOLEAN DEFAULT false,
    allergies_details TEXT,
    special_needs TEXT
);

-- Table des profils parents
CREATE TABLE IF NOT EXISTS parent_profiles (
    id BIGSERIAL PRIMARY KEY,
    parent_id UUID NOT NULL UNIQUE REFERENCES auth.users(id),
    profession VARCHAR(100),
    work_phone VARCHAR(20),
    work_address TEXT,
    preferred_contact_method VARCHAR(50),
    preferred_contact_time VARCHAR(50),
    marital_status VARCHAR(50),
    additional_info TEXT
);

-- Enable Row Level Security
ALTER TABLE documents ENABLE ROW LEVEL SECURITY;
ALTER TABLE rooms ENABLE ROW LEVEL SECURITY;
ALTER TABLE room_equipment ENABLE ROW LEVEL SECURITY;
ALTER TABLE parent_student_associations ENABLE ROW LEVEL SECURITY;
ALTER TABLE student_profiles ENABLE ROW LEVEL SECURITY;
ALTER TABLE parent_profiles ENABLE ROW LEVEL SECURITY;

-- Policies for documents
CREATE POLICY "Documents are viewable by admin and owners"
    ON documents FOR SELECT
    USING (
        auth.uid() IN (uploaded_by_id, student_id, parent_id) OR
        EXISTS (
            SELECT 1 FROM users u
            WHERE u.id = auth.uid() AND u.role = 'ROLE_ADMIN'
        )
    );

CREATE POLICY "Documents are insertable by admin and teachers"
    ON documents FOR INSERT
    WITH CHECK (
        EXISTS (
            SELECT 1 FROM users u
            WHERE u.id = auth.uid() AND u.role IN ('ROLE_ADMIN', 'ROLE_PROFESSOR')
        )
    );

CREATE POLICY "Documents are updatable by admin"
    ON documents FOR UPDATE
    USING (
        EXISTS (
            SELECT 1 FROM users u
            WHERE u.id = auth.uid() AND u.role = 'ROLE_ADMIN'
        )
    );

-- Policies for rooms
CREATE POLICY "Rooms are viewable by all authenticated users"
    ON rooms FOR SELECT
    USING (auth.uid() IS NOT NULL);

CREATE POLICY "Rooms are manageable by admin"
    ON rooms FOR ALL
    USING (
        EXISTS (
            SELECT 1 FROM users u
            WHERE u.id = auth.uid() AND u.role = 'ROLE_ADMIN'
        )
    );

-- Policies for parent-student associations
CREATE POLICY "Associations are viewable by involved parties and admin"
    ON parent_student_associations FOR SELECT
    USING (
        auth.uid() IN (parent_id, student_id) OR
        EXISTS (
            SELECT 1 FROM users u
            WHERE u.id = auth.uid() AND u.role = 'ROLE_ADMIN'
        )
    );

CREATE POLICY "Associations are manageable by admin"
    ON parent_student_associations FOR ALL
    USING (
        EXISTS (
            SELECT 1 FROM users u
            WHERE u.id = auth.uid() AND u.role = 'ROLE_ADMIN'
        )
    );

-- Policies for student profiles
CREATE POLICY "Student profiles are viewable by student, parents and admin"
     ON student_profiles FOR SELECT
    USING (
        auth.uid() = student_id OR
        EXISTS (
            SELECT 1 FROM parent_student_associations psa
            WHERE psa.student_id = student_profiles.student_id
            AND psa.parent_id = auth.uid()
            AND psa.is_validated = true
        ) OR
        EXISTS (
            SELECT 1 FROM users u
            WHERE u.id = auth.uid() AND u.role IN ('ROLE_ADMIN', 'ROLE_PROFESSOR')
        )
    );

CREATE POLICY "Student profiles are manageable by admin"
    ON student_profiles FOR ALL
    USING (
        EXISTS (
            SELECT 1 FROM users u
            WHERE u.id = auth.uid() AND u.role = 'ROLE_ADMIN'
        )
    );

-- Policies for parent profiles
CREATE POLICY "Parent profiles are viewable by owner and admin"
    ON parent_profiles FOR SELECT
    USING (
        auth.uid() = parent_id OR
        EXISTS (
            SELECT 1 FROM users u
            WHERE u.id = auth.uid() AND u.role = 'ROLE_ADMIN'
        )
    );

CREATE POLICY "Parent profiles are manageable by admin"
    ON parent_profiles FOR ALL
    USING (
        EXISTS (
            SELECT 1 FROM users u
            WHERE u.id = auth.uid() AND u.role = 'ROLE_ADMIN'
        )
    );

-- Indexes for better performance
CREATE INDEX idx_documents_student ON documents(student_id);
CREATE INDEX idx_documents_parent ON documents(parent_id);
CREATE INDEX idx_documents_type ON documents(type);
CREATE INDEX idx_documents_category ON documents(category);

CREATE INDEX idx_rooms_status ON rooms(status);
CREATE INDEX idx_rooms_building ON rooms(building_name);

CREATE INDEX idx_parent_student_parent ON parent_student_associations(parent_id);
CREATE INDEX idx_parent_student_student ON parent_student_associations(student_id);
CREATE INDEX idx_parent_student_validated ON parent_student_associations(is_validated);

CREATE INDEX idx_student_profiles_student ON student_profiles(student_id);
CREATE INDEX idx_student_profiles_birth_date ON student_profiles(birth_date);

CREATE INDEX idx_parent_profiles_parent ON parent_profiles(parent_id);
CREATE INDEX idx_parent_profiles_profession ON parent_profiles(profession);