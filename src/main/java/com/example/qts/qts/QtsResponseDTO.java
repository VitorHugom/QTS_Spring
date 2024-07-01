package com.example.qts.qts;

import com.example.qts.course.Course;
import com.example.qts.qts_schedule.QtsScheduleResponseDTO;

import java.util.List;

public record QtsResponseDTO(Long id, Course course, List<QtsScheduleResponseDTO> qtsSchedules) {
    public QtsResponseDTO(Qts qts, List<QtsScheduleResponseDTO> qtsSchedules) {
        this(qts.getId(), qts.getCourse(), qtsSchedules);
    }
}
