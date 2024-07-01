package com.example.qts.course;

import com.example.qts.discipline.DisciplineResponseDTO;

import java.util.List;
import java.util.stream.Collectors;

public record CourseResponseDTO(Long id, String nome_curso, Integer carga_horaria, List<DisciplineResponseDTO> disciplines) {
    public CourseResponseDTO(Course course) {
        this(
                course.getId(),
                course.getNomeCurso(),
                course.getCarga_horaria(),
                course.getDisciplines().stream().map(DisciplineResponseDTO::new).collect(Collectors.toList())
        );
    }
}
