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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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

    @Transactional
    public void deleteQts(Long id) {
        qtsScheduleRepository.deleteByQtsId(id);
        qtsRepository.deleteById(id);
    }

    public List<QtsBasicResponseDTO> getAllQtsBasic(){
        List<Qts> qtsList = qtsRepository.findAll();
        return  qtsList.stream().map(QtsBasicResponseDTO::new).collect(Collectors.toList());
    }

    public QtsResponseDTO getQtsById(Long id) {
        Optional<Qts> qtsOptional = qtsRepository.findById(id);
        if (!qtsOptional.isPresent()) {
            throw new RuntimeException("QTS não encontrado para o ID: " + id);
        }

        Qts qts = qtsOptional.get();
        List<QtsSchedule> qtsScheduleList = qtsScheduleRepository.findByQts(qts);
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
        Logger logger = LoggerFactory.getLogger(getClass());

        var course = courseRepository.findByNomeCurso(courseName)
                .orElseThrow(() -> new RuntimeException("Curso '" + courseName + "' não encontrado."));

        List<Discipline> disciplines = course.getDisciplines();
        disciplines.sort(Comparator.comparing(Discipline::getWorkload).reversed());

        List<Professor> professors = professorRepository.findAll().stream()
                .filter(professor -> professor.getCourses().contains(course))
                .collect(Collectors.toList());

        List<QtsSchedule> allQtsSchedules = qtsScheduleRepository.findAll();

        Map<Long, Set<String>> professorHorarioOcupado = new HashMap<>();
        for (QtsSchedule qtsSchedule : allQtsSchedules) {
            Long professorId = qtsSchedule.getProfessor().getId();
            String horarioKey = qtsSchedule.getDaysOfWeek().getId() + "-" + qtsSchedule.getSchedule().getId();
            professorHorarioOcupado
                    .computeIfAbsent(professorId, k -> new HashSet<>())
                    .add(horarioKey);
        }

        Map<Long, Map<Long, List<Schedule>>> professorDisponibilidade = new HashMap<>();
        for (Professor professor : professors) {
            Map<Long, List<Schedule>> disponibilidade = new HashMap<>();
            for (DaysOfWeek day : professor.getDaysOfWeek()) {
                List<Schedule> schedules = new ArrayList<>(day.getSchedules());
                schedules.sort(Comparator.comparing(Schedule::getHorario_inicio));
                disponibilidade.put(day.getId(), schedules);
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

        for (Discipline disciplina : disciplines) {
            if (disciplinasAlocadas.contains(disciplina.getId())) {
                continue;
            }
            for (Professor professor : professors) {
                if (!disciplinaProfessores.getOrDefault(disciplina.getId(), Collections.emptyList()).contains(professor.getId())) {
                    continue;
                }

                int horasRestantes = Math.round(disciplina.getWorkload());
                boolean alocada = false;

                var disponibilidade = professorDisponibilidade.get(professor.getId());

                // Tentar alocar todas as aulas no mesmo dia
                for (var entry : disponibilidade.entrySet()) {
                    if (entry.getValue().size() * 20 >= horasRestantes) {
                        DaysOfWeek dayOfWeek = daysOfWeekRepository.findById(entry.getKey())
                                .orElseThrow(() -> new RuntimeException("Dia da semana não encontrado: " + entry.getKey()));
                        boolean podeAlocar = true;
                        for (Schedule horario : entry.getValue().subList(0, horasRestantes / 20)) {
                            String horarioKey = entry.getKey() + "-" + horario.getId();
                            if (horarioOcupado.contains(horarioKey) || professorHorarioOcupado.getOrDefault(professor.getId(), new HashSet<>()).contains(horarioKey)) {
                                podeAlocar = false;
                                break;
                            }
                        }
                        if (podeAlocar) {
                            for (Schedule horario : entry.getValue().subList(0, horasRestantes / 20)) {
                                String horarioKey = entry.getKey() + "-" + horario.getId();
                                qtsSchedules.add(new QtsSchedule(null, disciplina, professor, scheduleRepository.findById(horario.getId()).orElse(null), dayOfWeek));
                                horarioOcupado.add(horarioKey);
                            }
                            entry.getValue().subList(0, horasRestantes / 20).clear();
                            disciplinasAlocadas.add(disciplina.getId());
                            alocada = true;
                            break;
                        }
                    }
                }

                if (alocada) {
                    break;
                }

                // Alocação normal caso não encontre um dia disponível para todas as aulas
                for (var entry : disponibilidade.entrySet()) {
                    if (horasRestantes <= 0) {
                        break;
                    }
                    DaysOfWeek dayOfWeek = daysOfWeekRepository.findById(entry.getKey())
                            .orElseThrow(() -> new RuntimeException("Dia da semana não encontrado: " + entry.getKey()));
                    List<Schedule> horariosParaRemover = new ArrayList<>();
                    for (Schedule horario : new ArrayList<>(entry.getValue())) {
                        if (horasRestantes <= 0) {
                            break;
                        }
                        String horarioKey = entry.getKey() + "-" + horario.getId();
                        if (!horarioOcupado.contains(horarioKey) &&
                                !professorHorarioOcupado.getOrDefault(professor.getId(), new HashSet<>()).contains(horarioKey)) {
                            qtsSchedules.add(new QtsSchedule(null, disciplina, professor, scheduleRepository.findById(horario.getId()).orElse(null), dayOfWeek));
                            horarioOcupado.add(horarioKey);
                            horariosParaRemover.add(horario);
                            professorHorarioOcupado
                                    .computeIfAbsent(professor.getId(), k -> new HashSet<>())
                                    .add(horarioKey);
                            horasRestantes -= 20;
                        }
                    }
                    entry.getValue().removeAll(horariosParaRemover);
                }
                if (horasRestantes <= 0) {
                    disciplinasAlocadas.add(disciplina.getId());
                    break;
                }
            }
        }

        qtsSchedules.sort(Comparator.comparing(qtsSchedule -> qtsSchedule.getSchedule().getHorario_inicio()));

        Qts qts = new Qts(null, course);
        qtsRepository.save(qts);

        for (QtsSchedule qtsSchedule : qtsSchedules) {
            qtsSchedule.setQts(qts);
            qtsScheduleRepository.save(qtsSchedule);
        }

        logger.info("Total de QtsSchedules gerados: {}", qtsSchedules.size());
        for (QtsSchedule qtsSchedule : qtsSchedules) {
            logger.info("QtsSchedule: Disciplina {}, Professor {}, Dia {}, Horário {}",
                    qtsSchedule.getDiscipline().getDescription(),
                    qtsSchedule.getProfessor().getNome_professor(),
                    qtsSchedule.getDaysOfWeek().getDia_da_semana(),
                    qtsSchedule.getSchedule().getHorario_inicio());
        }
    }

}
