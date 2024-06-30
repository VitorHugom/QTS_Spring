package com.example.qts.professor;

import com.example.qts.days_of_week.DaysOfWeekResponseDTO;
import com.example.qts.discipline.DisciplineResponseDTO;

import java.util.List;
import java.util.stream.Collectors;

public record ProfessorResponseDTO(Long id, String nome_professor, List<DisciplineResponseDTO> disciplines, List<DaysOfWeekResponseDTO> daysOfWeek) {
    public ProfessorResponseDTO(Professor professor) {
        this(
                professor.getId(),
                professor.getNome_professor(),
                professor.getDisciplines().stream().map(DisciplineResponseDTO::new).collect(Collectors.toList()),
                professor.getDaysOfWeek().stream().map(DaysOfWeekResponseDTO::new).collect(Collectors.toList())
        );
    }
}
