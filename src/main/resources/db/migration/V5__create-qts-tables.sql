-- V5__create_qts_tables.sql

-- Criação da tabela qts
CREATE TABLE qts (
    id BIGSERIAL PRIMARY KEY,
    course_id BIGINT NOT NULL,
    FOREIGN KEY (course_id) REFERENCES courses(id)
);

-- Criação da tabela qts_schedule
CREATE TABLE qts_schedule (
    id BIGSERIAL PRIMARY KEY,
    qts_id BIGINT NOT NULL,
    discipline_id BIGINT NOT NULL,
    professor_id BIGINT NOT NULL,
    schedule_id BIGINT NOT NULL,
    dia_semana VARCHAR(50) NOT NULL,
    FOREIGN KEY (qts_id) REFERENCES qts(id),
    FOREIGN KEY (discipline_id) REFERENCES disciplines(id),
    FOREIGN KEY (professor_id) REFERENCES professors(id),
    FOREIGN KEY (schedule_id) REFERENCES schedules(id)
);
