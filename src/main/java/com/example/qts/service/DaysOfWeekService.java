package com.example.qts.service;

import com.example.qts.days_of_week.*;
import com.example.qts.schedule.Schedule;
import com.example.qts.schedule.ScheduleRepository;
import com.example.qts.schedule.ScheduleResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class DaysOfWeekService {

    @Autowired
    private DaysOfWeekRepository daysOfWeekRepository;

    @Autowired
    private ScheduleRepository scheduleRepository;

    public List<DaysOfWeekResponseDTO> getAllDaysOfWeek() {
        List<DaysOfWeek> days = daysOfWeekRepository.findAll();
        return days.stream().map(DaysOfWeekResponseDTO::new).collect(Collectors.toList());
    }

    public DaysOfWeekResponseDTO getDayOfWeekById(Long id) {
        DaysOfWeek day = daysOfWeekRepository.findById(id).orElseThrow();
        return new DaysOfWeekResponseDTO(day);
    }

    public DaysOfWeekResponseDTO createDayOfWeek(DaysOfWeekRequestDTO request) {
        List<Schedule> schedules = scheduleRepository.findAllById(request.scheduleIds());
        DaysOfWeek dayOfWeek = new DaysOfWeek(request, schedules);
        dayOfWeek = daysOfWeekRepository.save(dayOfWeek);
        return new DaysOfWeekResponseDTO(dayOfWeek);
    }

    public DaysOfWeekResponseDTO updateDayOfWeek(Long id, DaysOfWeekRequestDTO request) {
        DaysOfWeek dayOfWeek = daysOfWeekRepository.findById(id).orElseThrow();
        dayOfWeek.setDia_da_semana(request.dia_da_semana());
        dayOfWeek.setStatus(request.status());
        List<Schedule> schedules = scheduleRepository.findAllById(request.scheduleIds());
        dayOfWeek.setSchedules(schedules);
        dayOfWeek = daysOfWeekRepository.save(dayOfWeek);
        return new DaysOfWeekResponseDTO(dayOfWeek);
    }

    public void deleteDayOfWeek(Long id) {
        daysOfWeekRepository.deleteById(id);
    }

    public List<ScheduleResponseDTO> getSchedulesByDayOfWeek(Long id) {
        DaysOfWeek dayOfWeek = daysOfWeekRepository.findById(id).orElseThrow();
        return dayOfWeek.getSchedules().stream().map(ScheduleResponseDTO::new).collect(Collectors.toList());
    }
}
