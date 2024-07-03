package com.example.qts.professor;

import java.util.List;

public record ProfessorRequestDTO(String nome_professor, List<Long> disciplineIds, List<Long> daysOfWeekIds, List<Long> coursesIds) {
}
