package com.example.qts.controller;

import com.example.qts.discipine.Discipline;
import com.example.qts.discipine.DisciplineRepository;
import com.example.qts.discipine.DisciplineRequestDTO;
import com.example.qts.discipine.DisciplineResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("disciplines")
public class DisciplineController {
    @Autowired
    private DisciplineRepository repository;

    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @PostMapping
    public void saveDiscipline(@RequestBody DisciplineRequestDTO data){
        Discipline disciplineData = new Discipline(data);
        repository.save(disciplineData);
        return;
    }

    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @GetMapping
    public List<DisciplineResponseDTO> getAll(){
        return repository.findAll().stream().map(DisciplineResponseDTO::new).toList();
    }
}