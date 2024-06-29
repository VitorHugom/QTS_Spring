package com.example.qts.controller;

import com.example.qts.discipine.Discipline;
import com.example.qts.discipine.DisciplineRequestDTO;
import com.example.qts.discipine.DisciplineResponseDTO;
import com.example.qts.schedule.Schedule;
import com.example.qts.schedule.ScheduleRepository;
import com.example.qts.schedule.ScheduleRequestDTO;
import com.example.qts.schedule.ScheduleResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("schedules")
public class ScheduleController {
    @Autowired
    private ScheduleRepository repository;

    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @PostMapping
    public void saveSchedule(@RequestBody ScheduleRequestDTO data){
        Schedule scheduleData = new Schedule(data);
        repository.save(scheduleData);
    }

    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @GetMapping
    public List<ScheduleResponseDTO> getAll(){
        return repository.findAll().stream().map(ScheduleResponseDTO::new).toList();
    }

    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @GetMapping("/{id}")
    public ScheduleResponseDTO getScheduleById(@PathVariable Long id){
        Schedule schedule = repository.findById(id).orElseThrow(() -> new RuntimeException("Schedule not found"));
        return new ScheduleResponseDTO(schedule);
    }

    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @PutMapping("/{id}")
    public void updateSchedule(@PathVariable Long id, @RequestBody ScheduleRequestDTO data){
        Schedule schedule = repository.findById(id).orElseThrow(() -> new RuntimeException("Schedule not found"));
        schedule.setDescricao(data.descricao());
        schedule.setHorario_inicio(data.horario_inicio());
        schedule.setHorario_fim(data.horario_fim());
        repository.save(schedule);
    }

    public void deleteSchedule(@PathVariable long id){
        repository.deleteById(id);
    }
}
