package com.example.qts.schedule;


import com.example.qts.days_of_week.DaysOfWeek;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
@Entity(name = "schedules")
@Table(name = "schedules")
public class Schedule {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String descricao;
    private String horario_inicio;
    private String horario_fim;

    @ManyToMany
    @JoinTable(
            name = "days_of_week_schedules",
            joinColumns = @JoinColumn(name = "schedule_id"),
            inverseJoinColumns = @JoinColumn(name = "day_of_week_id")
    )
    private List<DaysOfWeek> daysOfWeek = new ArrayList<>();

    public Schedule(ScheduleRequestDTO data){
        this.descricao = data.descricao();
        this.horario_inicio = data.horario_inicio();
        this.horario_fim = data.horario_fim();
    }
}
