-- V7__Add_Professor_Course_Relationship.sql

-- Tabela de junção para relacionamento N para N entre professors e courses
CREATE TABLE professors_courses (
    professor_id BIGINT,
    course_id BIGINT,
    FOREIGN KEY (professor_id) REFERENCES professors(id) ON DELETE CASCADE,
    FOREIGN KEY (course_id) REFERENCES courses(id) ON DELETE CASCADE,
    PRIMARY KEY (professor_id, course_id)
);
