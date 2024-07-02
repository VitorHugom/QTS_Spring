package com.example.qts.service;

import com.example.qts.course.CourseRepository;
import com.example.qts.days_of_week.DaysOfWeek;
import com.example.qts.days_of_week.DaysOfWeekRepository;
import com.example.qts.discipline.Discipline;
import com.example.qts.professor.Professor;
import com.example.qts.professor.ProfessorRepository;
import com.example.qts.qts.Qts;
import com.example.qts.qts.QtsBasicResponseDTO;
import com.example.qts.qts.QtsRepository;
import com.example.qts.qts.QtsResponseDTO;
import com.example.qts.qts_schedule.QtsSchedule;
import com.example.qts.qts_schedule.QtsScheduleRepository;
import com.example.qts.qts_schedule.QtsScheduleResponseDTO;
import com.example.qts.schedule.Schedule;
import com.example.qts.schedule.ScheduleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class QtsService {

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private ScheduleRepository scheduleRepository;

    @Autowired
    private ProfessorRepository professorRepository;

    @Autowired
    private QtsRepository qtsRepository;

    @Autowired
    private QtsScheduleRepository qtsScheduleRepository;

    @Autowired
    private DaysOfWeekRepository daysOfWeekRepository;

    public List<QtsBasicResponseDTO> getAllQtsBasic(){
        List<Qts> qtsList = qtsRepository.findAll();
        return  qtsList.stream().map(QtsBasicResponseDTO::new).collect(Collectors.toList());
    }
    public QtsResponseDTO getQtsById(Long id) {
        // Buscar QTS pelo ID
        Optional<Qts> qtsOptional = qtsRepository.findById(id);
        if (!qtsOptional.isPresent()) {
            throw new RuntimeException("QTS não encontrado para o ID: " + id);
        }

        Qts qts = qtsOptional.get();

        // Buscar detalhes do QTS
        List<QtsSchedule> qtsScheduleList = qtsScheduleRepository.findByQts(qts);

        // Mapear para QtsResponseDTO
        List<QtsScheduleResponseDTO> qtsScheduleResponseDTOList = qtsScheduleList.stream()
                .map(QtsScheduleResponseDTO::new)
                .collect(Collectors.toList());

        return new QtsResponseDTO(qts, qtsScheduleResponseDTOList);
    }


    public List<QtsResponseDTO> getAllQts() {
        List<Qts> qtsList = qtsRepository.findAll();
        List<QtsResponseDTO> qtsResponseDTOList = new ArrayList<>();

        for (Qts qts : qtsList) {
            List<QtsScheduleResponseDTO> qtsScheduleResponseDTOList = qtsScheduleRepository.findByQts(qts)
                    .stream()
                    .map(QtsScheduleResponseDTO::new)
                    .collect(Collectors.toList());

            QtsResponseDTO qtsResponseDTO = new QtsResponseDTO(qts, qtsScheduleResponseDTOList);
            qtsResponseDTOList.add(qtsResponseDTO);
        }

        return qtsResponseDTOList;
    }

    @Transactional
    public void generateQts(String courseName) {
        var course = courseRepository.findByNomeCurso(courseName)
                .orElseThrow(() -> new RuntimeException("Curso '" + courseName + "' não encontrado."));

        List<Discipline> disciplines = course.getDisciplines();
        disciplines.sort(Comparator.comparing(Discipline::getWorkload).reversed());

        List<Professor> professors = professorRepository.findAll();

        Map<Long, Map<Long, List<Long>>> professorDisponibilidade = new HashMap<>();
        for (Professor professor : professors) {
            Map<Long, List<Long>> disponibilidade = new HashMap<>();
            for (DaysOfWeek day : professor.getDaysOfWeek()) {
                disponibilidade.put(day.getId(), new ArrayList<>());
                for (Schedule schedule : day.getSchedules()) {
                    disponibilidade.get(day.getId()).add(schedule.getId());
                }
            }
            professorDisponibilidade.put(professor.getId(), disponibilidade);
        }

        Map<Long, List<Long>> disciplinaProfessores = new HashMap<>();
        for (Professor professor : professors) {
            for (Discipline discipline : professor.getDisciplines()) {
                disciplinaProfessores
                        .computeIfAbsent(discipline.getId(), k -> new ArrayList<>())
                        .add(professor.getId());
            }
        }

        List<QtsSchedule> qtsSchedules = new ArrayList<>();
        Set<String> horarioOcupado = new HashSet<>();
        Set<Long> disciplinasAlocadas = new HashSet<>();

        professors.sort(Comparator.comparingInt(p -> professorDisponibilidade.get(p.getId()).size()));

        for (Professor professor : professors) {
            var disponibilidade = professorDisponibilidade.get(professor.getId());
            for (Discipline disciplina : disciplines) {
                if (disciplinasAlocadas.contains(disciplina.getId())) {
                    continue;
                }
                if (!disciplinaProfessores.getOrDefault(disciplina.getId(), Collections.emptyList()).contains(professor.getId())) {
                    continue;
                }

                int horasRestantes = Math.round(disciplina.getWorkload());
                boolean alocada = false;

                if (horasRestantes >= 80) {
                    for (var entry : disponibilidade.entrySet()) {
                        if (entry.getValue().size() >= 4) {
                            DaysOfWeek dayOfWeek = daysOfWeekRepository.findById(entry.getKey())
                                    .orElseThrow(() -> new RuntimeException("Dia da semana não encontrado: " + entry.getKey()));
                            for (Long horario : entry.getValue().subList(0, 4)) {
                                qtsSchedules.add(new QtsSchedule(null, disciplina, professor, scheduleRepository.findById(horario).orElse(null), dayOfWeek));
                                horarioOcupado.add(entry.getKey() + "-" + horario);
                            }
                            disciplinasAlocadas.add(disciplina.getId());
                            alocada = true;
                            break;
                        }
                    }
                }

                if (alocada) {
                    continue;
                }

                for (var entry : disponibilidade.entrySet()) {
                    if (horasRestantes <= 0) {
                        break;
                    }
                    DaysOfWeek dayOfWeek = daysOfWeekRepository.findById(entry.getKey())
                            .orElseThrow(() -> new RuntimeException("Dia da semana não encontrado: " + entry.getKey()));
                    for (Long horario : entry.getValue()) {
                        if (horasRestantes <= 0) {
                            break;
                        }
                        if (!horarioOcupado.contains(entry.getKey() + "-" + horario)) {
                            qtsSchedules.add(new QtsSchedule(null, disciplina, professor, scheduleRepository.findById(horario).orElse(null), dayOfWeek));
                            horarioOcupado.add(entry.getKey() + "-" + horario);
                            horasRestantes -= 20;
                        }
                    }
                }
                if (horasRestantes <= 0) {
                    disciplinasAlocadas.add(disciplina.getId());
                }
            }
        }

        Qts qts = new Qts(null, course);
        qtsRepository.save(qts);

        for (QtsSchedule qtsSchedule : qtsSchedules) {
            qtsSchedule.setQts(qts);
            qtsScheduleRepository.save(qtsSchedule);
        }
    }
}
