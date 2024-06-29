package com.example.qts.schedule;


import jakarta.persistence.*;
import lombok.*;

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

    public Schedule(ScheduleRequestDTO data){
        this.descricao = data.descricao();
        this.horario_inicio = data.horario_inicio();
        this.horario_fim = data.horario_fim();
    }
}
