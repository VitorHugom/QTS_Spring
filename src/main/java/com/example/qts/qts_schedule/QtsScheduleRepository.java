package com.example.qts.qts_schedule;

import com.example.qts.qts.Qts;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface QtsScheduleRepository extends JpaRepository<QtsSchedule, Long> {
    List<QtsSchedule> findByQts(Qts qts);
}
