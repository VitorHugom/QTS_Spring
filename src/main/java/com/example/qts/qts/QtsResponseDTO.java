package com.example.qts.qts;

import com.example.qts.course.Course;

public record QtsResponseDTO(Long id, Course course) {
    public QtsResponseDTO(Qts qts){
        this(qts.getId(), qts.getCourse());
    }
}
