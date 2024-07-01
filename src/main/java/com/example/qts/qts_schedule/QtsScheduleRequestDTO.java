package com.example.qts.qts_schedule;

import com.example.qts.days_of_week.DaysOfWeek;
import com.example.qts.discipline.Discipline;
import com.example.qts.professor.Professor;
import com.example.qts.qts.Qts;
import com.example.qts.schedule.Schedule;

public record QtsScheduleRequestDTO(Qts qts, Discipline discipline, Professor professor, Schedule schedule, DaysOfWeek daysOfWeek) {
}
