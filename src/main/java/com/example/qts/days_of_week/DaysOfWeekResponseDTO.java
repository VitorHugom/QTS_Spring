package com.example.qts.days_of_week;

import com.example.qts.schedule.ScheduleResponseDTO;

import java.util.List;
import java.util.stream.Collectors;

public record DaysOfWeekResponseDTO(Long id, String dia_da_semana, Boolean status, List<ScheduleResponseDTO> schedules) {
    public DaysOfWeekResponseDTO(DaysOfWeek daysOfWeek){
        this(
                daysOfWeek.getId(),
                daysOfWeek.getDia_da_semana(),
                daysOfWeek.getStatus(),
                daysOfWeek.getSchedules().stream().map(ScheduleResponseDTO::new).collect(Collectors.toList())
        );
    }
}
