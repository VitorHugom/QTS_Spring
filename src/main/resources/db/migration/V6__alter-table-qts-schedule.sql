-- V6__alter_qts_schedule_add_day_of_week.sql

-- Adicionar a coluna day_of_week_id na tabela qts_schedule
ALTER TABLE qts_schedule
ADD COLUMN day_of_week_id BIGINT NOT NULL,
DROP COLUMN dia_semana;

-- Adicionar chave estrangeira para day_of_week_id
ALTER TABLE qts_schedule
ADD CONSTRAINT fk_day_of_week
FOREIGN KEY (day_of_week_id) REFERENCES days_of_week(id);
