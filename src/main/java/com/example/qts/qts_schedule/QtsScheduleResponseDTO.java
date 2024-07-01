package com.example.qts.qts_schedule;

import com.example.qts.days_of_week.DaysOfWeek;
import com.example.qts.discipline.Discipline;
import com.example.qts.professor.Professor;
import com.example.qts.schedule.Schedule;

public record QtsScheduleResponseDTO(Long id, Discipline discipline, Professor professor, Schedule schedule, DaysOfWeek daysOfWeek) {
    public  QtsScheduleResponseDTO(QtsSchedule qtsSchedule){
        this(
                qtsSchedule.getId(),
                qtsSchedule.getDiscipline(),
                qtsSchedule.getProfessor(),
                qtsSchedule.getSchedule(),
                qtsSchedule.getDaysOfWeek()
        );
    }
}
