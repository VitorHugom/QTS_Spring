package com.example.qts.course;

import com.example.qts.discipline.Discipline;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
@Entity(name = "courses")
@Table(name = "courses")
public class Course {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome_curso;
    private Integer carga_horaria;

    @ManyToMany
    @JoinTable(
            name = "courses_disciplines",
            joinColumns = @JoinColumn(name = "course_id"),
            inverseJoinColumns = @JoinColumn(name = "discipline_id")
    )
    private List<Discipline> disciplines;

    public Course(CourseRequestDTO data) {
        this.nome_curso = data.nome_curso();
        this.carga_horaria = data.carga_horaria();
    }
}
