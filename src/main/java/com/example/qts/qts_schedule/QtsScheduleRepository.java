package com.example.qts.qts_schedule;

import com.example.qts.qts.Qts;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface QtsScheduleRepository extends JpaRepository<QtsSchedule, Long> {
    List<QtsSchedule> findByQts(Qts qts);
    @Modifying
    @Transactional
    @Query("DELETE FROM QtsSchedule qs WHERE qs.qts.id = :qtsId")
    void deleteByQtsId(Long qtsId);
}
