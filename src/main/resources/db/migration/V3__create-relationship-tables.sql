-- Script para criar as tabelas de relacionamento

-- Tabela courses_disciplines
CREATE TABLE courses_disciplines (
    course_id BIGINT,
    discipline_id BIGINT,
    FOREIGN KEY (course_id) REFERENCES courses(id) ON DELETE CASCADE,
    FOREIGN KEY (discipline_id) REFERENCES disciplines(id) ON DELETE CASCADE,
    PRIMARY KEY (course_id, discipline_id)
);

-- Tabela professors_disciplines
CREATE TABLE professors_disciplines (
    professor_id BIGINT,
    discipline_id BIGINT,
    FOREIGN KEY (professor_id) REFERENCES professors(id) ON DELETE CASCADE,
    FOREIGN KEY (discipline_id) REFERENCES disciplines(id) ON DELETE CASCADE,
    PRIMARY KEY (professor_id, discipline_id)
);

-- Tabela professors_days_of_week
CREATE TABLE professors_days_of_week (
    professor_id BIGINT,
    day_of_week_id BIGINT,
    FOREIGN KEY (professor_id) REFERENCES professors(id) ON DELETE CASCADE,
    FOREIGN KEY (day_of_week_id) REFERENCES days_of_week(id) ON DELETE CASCADE,
    PRIMARY KEY (professor_id, day_of_week_id)
);

-- Tabela days_of_week_schedules
CREATE TABLE days_of_week_schedules (
    day_of_week_id BIGINT,
    schedule_id BIGINT,
    FOREIGN KEY (day_of_week_id) REFERENCES days_of_week(id) ON DELETE CASCADE,
    FOREIGN KEY (schedule_id) REFERENCES schedules(id) ON DELETE CASCADE,
    PRIMARY KEY (day_of_week_id, schedule_id)
);
