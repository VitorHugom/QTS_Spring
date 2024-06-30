package com.example.qts.discipline;

public record DisciplineResponseDTO(Long id, String description, Float workload) {
    public DisciplineResponseDTO(Discipline discipline){
        this(discipline.getId(), discipline.getDescription(), discipline.getWorkload());
    }
}
