package com.example.qts.professor;

import com.example.qts.days_of_week.DaysOfWeek;
import com.example.qts.discipline.Discipline;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
@Entity(name = "professors")
@Table(name = "professors")
public class Professor {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome_professor;

    @ManyToMany
    @JoinTable(
            name = "professors_disciplines",
            joinColumns = @JoinColumn(name = "professor_id"),
            inverseJoinColumns = @JoinColumn(name = "discipline_id")
    )
    private List<Discipline> disciplines;

    @ManyToMany
    @JoinTable(
            name = "professors_days_of_week",
            joinColumns = @JoinColumn(name = "professor_id"),
            inverseJoinColumns = @JoinColumn(name = "day_of_week_id")
    )
    private List<DaysOfWeek> daysOfWeek;

    public Professor(ProfessorRequestDTO data) {
        this.nome_professor = data.nome_professor();
    }
}
