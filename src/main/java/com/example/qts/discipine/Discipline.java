package com.example.qts.discipine;

import jakarta.persistence.*;
import lombok.*;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
@Table(name = "disciplines")
@Entity(name = "disciplines")
public class Discipline {
    // Getters and setters
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String description;
    private Float workload;

    public Discipline(DisciplineRequestDTO data){
        this.description = data.description();
        this.workload = data.workload();
    }
}
