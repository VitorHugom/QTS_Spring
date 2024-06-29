-- Script para criar as tabelas base

-- Tabela courses
CREATE TABLE courses (
    id BIGSERIAL PRIMARY KEY,
    nome_curso VARCHAR(40) NOT NULL,
    carga_horaria INTEGER NOT NULL
);

-- Tabela professors
CREATE TABLE professors (
    id BIGSERIAL PRIMARY KEY,
    nome_professor VARCHAR(255) NOT NULL
);

-- Tabela days_of_week
CREATE TABLE days_of_week (
    id BIGSERIAL PRIMARY KEY,
    dia_da_semana VARCHAR(50) NOT NULL,
    status BOOLEAN
);

-- Tabela schedules
CREATE TABLE schedules (
    id BIGSERIAL PRIMARY KEY,
    descricao VARCHAR(255),
    horario_inicio TIME,
    horario_fim TIME
);
