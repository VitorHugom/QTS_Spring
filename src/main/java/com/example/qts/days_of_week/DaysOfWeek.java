package com.example.qts.days_of_week;

import jakarta.persistence.*;
import lombok.*;

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


}
