package com.example.qts.days_of_week;

import com.example.qts.schedule.Schedule;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
@Entity(name = "days_of_week")
@Table(name = "days_of_week")
public class DaysOfWeek {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String dia_da_semana;
    private Boolean status;

    @JsonIgnore
    @ManyToMany
    @JoinTable(
            name = "days_of_week_schedules",
            joinColumns = @JoinColumn(name = "day_of_week_id"),
            inverseJoinColumns = @JoinColumn(name = "schedule_id")
    )
    private List<Schedule> schedules = new ArrayList<>();

    public DaysOfWeek (DaysOfWeekRequestDTO data, List<Schedule> schedules){
        this.dia_da_semana = data.dia_da_semana();
        this.status = data.status();
        this.schedules = schedules;
    }
}
