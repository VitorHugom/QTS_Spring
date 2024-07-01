package com.example.qts.qts_schedule;

import com.example.qts.days_of_week.DaysOfWeek;
import com.example.qts.discipline.Discipline;
import com.example.qts.professor.Professor;
import com.example.qts.qts.Qts;
import com.example.qts.schedule.Schedule;
import jakarta.persistence.*;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@EqualsAndHashCode(of = "id")
@Entity
@Table(name = "qts_schedule")
public class QtsSchedule {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "qts_id", nullable = false)
    private Qts qts;

    @ManyToOne
    @JoinColumn(name = "discipline_id", nullable = false)
    private Discipline discipline;

    @ManyToOne
    @JoinColumn(name = "professor_id", nullable = false)
    private Professor professor;

    @ManyToOne
    @JoinColumn(name = "schedule_id", nullable = false)
    private Schedule schedule;

    @ManyToOne
    @JoinColumn(name = "day_of_week_id", nullable = false)
    private DaysOfWeek daysOfWeek;

    public QtsSchedule(Qts qts, Discipline discipline, Professor professor, Schedule schedule, DaysOfWeek daysOfWeek) {
        this.qts = qts;
        this.discipline = discipline;
        this.professor = professor;
        this.schedule = schedule;
        this.daysOfWeek = daysOfWeek;
    }
}
