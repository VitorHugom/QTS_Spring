package com.example.qts.controller;

import com.example.qts.professor.ProfessorRequestDTO;
import com.example.qts.professor.ProfessorResponseDTO;
import com.example.qts.service.ProfessorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("professors")
public class ProfessorController {

    @Autowired
    private ProfessorService professorService;

    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @GetMapping
    public ResponseEntity<List<ProfessorResponseDTO>> getAllProfessors() {
        List<ProfessorResponseDTO> professors = professorService.getAllProfessors();
        return ResponseEntity.ok(professors);
    }

    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @GetMapping("/{id}")
    public ResponseEntity<ProfessorResponseDTO> getProfessorById(@PathVariable Long id) {
        ProfessorResponseDTO professor = professorService.getProfessorById(id);
        return ResponseEntity.ok(professor);
    }

    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @PostMapping
    public ResponseEntity<ProfessorResponseDTO> createProfessor(@RequestBody ProfessorRequestDTO request) {
        ProfessorResponseDTO professor = professorService.createProfessor(request);
        return ResponseEntity.status(201).body(professor);
    }

    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @PutMapping("/{id}")
    public ResponseEntity<ProfessorResponseDTO> updateProfessor(@PathVariable Long id, @RequestBody ProfessorRequestDTO request) {
        ProfessorResponseDTO professor = professorService.updateProfessor(id, request);
        return ResponseEntity.ok(professor);
    }

    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProfessor(@PathVariable Long id) {
        professorService.deleteProfessor(id);
        return ResponseEntity.noContent().build();
    }
}
