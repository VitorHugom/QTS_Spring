package com.example.qts.schedule;

public record ScheduleResponseDTO(Long id, String descricao, String horario_inicio, String horario_fim) {
    public ScheduleResponseDTO(Schedule schedule){
        this(schedule.getId(), schedule.getDescricao(), schedule.getHorario_inicio(), schedule.getHorario_fim());
    }
}
