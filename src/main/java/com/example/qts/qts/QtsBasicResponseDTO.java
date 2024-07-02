package com.example.qts.qts;

import com.example.qts.course.Course;

public record QtsBasicResponseDTO(Long id, Course course) {
    public QtsBasicResponseDTO( Qts qts){
        this(qts.getId(), qts.getCourse());
    }
}
