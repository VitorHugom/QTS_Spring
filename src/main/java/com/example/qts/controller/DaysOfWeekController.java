package com.example.qts.controller;

import com.example.qts.days_of_week.DaysOfWeekRequestDTO;
import com.example.qts.days_of_week.DaysOfWeekResponseDTO;
import com.example.qts.schedule.ScheduleResponseDTO;
import com.example.qts.service.DaysOfWeekService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("days_of_week")
public class DaysOfWeekController {

    @Autowired
    private DaysOfWeekService daysOfWeekService;

    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @GetMapping
    public ResponseEntity<List<DaysOfWeekResponseDTO>> getAllDaysOfWeek() {
        List<DaysOfWeekResponseDTO> days = daysOfWeekService.getAllDaysOfWeek();
        return ResponseEntity.ok(days);
    }

    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @GetMapping("/{id}")
    public ResponseEntity<DaysOfWeekResponseDTO> getDayOfWeekById(@PathVariable Long id) {
        DaysOfWeekResponseDTO day = daysOfWeekService.getDayOfWeekById(id);
        return ResponseEntity.ok(day);
    }

    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @PostMapping
    public ResponseEntity<DaysOfWeekResponseDTO> createDayOfWeek(@RequestBody DaysOfWeekRequestDTO request) {
        DaysOfWeekResponseDTO day = daysOfWeekService.createDayOfWeek(request);
        return ResponseEntity.status(201).body(day);
    }

    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @PutMapping("/{id}")
    public ResponseEntity<DaysOfWeekResponseDTO> updateDayOfWeek(@PathVariable Long id, @RequestBody DaysOfWeekRequestDTO request) {
        DaysOfWeekResponseDTO day = daysOfWeekService.updateDayOfWeek(id, request);
        return ResponseEntity.ok(day);
    }

    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDayOfWeek(@PathVariable Long id) {
        daysOfWeekService.deleteDayOfWeek(id);
        return ResponseEntity.noContent().build();
    }

    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @GetMapping("/{id}/schedules")
    public ResponseEntity<List<ScheduleResponseDTO>> getSchedulesByDayOfWeek(@PathVariable Long id) {
        List<ScheduleResponseDTO> schedules = daysOfWeekService.getSchedulesByDayOfWeek(id);
        return ResponseEntity.ok(schedules);
    }
}
