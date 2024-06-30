package com.example.qts.course;

import java.util.List;

public record CourseRequestDTO(String nome_curso, Integer carga_horaria, List<Long> disciplineIds) {
}
