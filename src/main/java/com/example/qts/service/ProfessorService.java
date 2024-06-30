package com.example.qts.service;

import com.example.qts.days_of_week.DaysOfWeek;
import com.example.qts.days_of_week.DaysOfWeekRepository;
import com.example.qts.discipline.Discipline;
import com.example.qts.discipline.DisciplineRepository;
import com.example.qts.professor.Professor;
import com.example.qts.professor.ProfessorRepository;
import com.example.qts.professor.ProfessorRequestDTO;
import com.example.qts.professor.ProfessorResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProfessorService {

    @Autowired
    private ProfessorRepository professorRepository;

    @Autowired
    private DisciplineRepository disciplineRepository;

    @Autowired
    private DaysOfWeekRepository daysOfWeekRepository;

    public List<ProfessorResponseDTO> getAllProfessors() {
        return professorRepository.findAll().stream().map(ProfessorResponseDTO::new).collect(Collectors.toList());
    }

    public ProfessorResponseDTO getProfessorById(Long id) {
        Professor professor = professorRepository.findById(id).orElseThrow(() -> new RuntimeException("Professor não encontrado"));
        return new ProfessorResponseDTO(professor);
    }

    public ProfessorResponseDTO createProfessor(ProfessorRequestDTO request) {
        List<Discipline> disciplines = disciplineRepository.findAllById(request.disciplineIds());
        List<DaysOfWeek> daysOfWeek = daysOfWeekRepository.findAllById(request.daysOfWeekIds());
        Professor professor = new Professor(request);
        professor.setDisciplines(disciplines);
        professor.setDaysOfWeek(daysOfWeek);
        professorRepository.save(professor);
        return new ProfessorResponseDTO(professor);
    }

    public ProfessorResponseDTO updateProfessor(Long id, ProfessorRequestDTO request) {
        Professor professor = professorRepository.findById(id).orElseThrow(() -> new RuntimeException("Professor não encontrado"));
        List<Discipline> disciplines = disciplineRepository.findAllById(request.disciplineIds());
        List<DaysOfWeek> daysOfWeek = daysOfWeekRepository.findAllById(request.daysOfWeekIds());
        professor.setNome_professor(request.nome_professor());
        professor.setDisciplines(disciplines);
        professor.setDaysOfWeek(daysOfWeek);
        professorRepository.save(professor);
        return new ProfessorResponseDTO(professor);
    }

    public void deleteProfessor(Long id) {
        professorRepository.deleteById(id);
    }
}
