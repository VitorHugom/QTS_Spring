package com.example.qts.days_of_week;

import com.example.qts.schedule.ScheduleRequestDTO;

import java.util.List;

public record DaysOfWeekRequestDTO(String dia_da_semana, Boolean status, List<Long> scheduleIds) {
}
