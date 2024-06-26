package com.example.qts.discipine;

public record DisciplineResponseDTO(Long id, String description, Float workload) {
    public DisciplineResponseDTO(Discipline discipline){
        this(discipline.getId(), discipline.getDescription(), discipline.getWorkload());
    }
}
