-- V4__alter_schedule_time_columns.sql

-- Alterar o tipo dos atributos horario_inicio e horario_fim para VARCHAR
ALTER TABLE schedules
    ALTER COLUMN horario_inicio TYPE VARCHAR(50),
    ALTER COLUMN horario_fim TYPE VARCHAR(50);
