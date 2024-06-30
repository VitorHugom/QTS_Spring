/*package com.example.qts.service;

import com.example.qts.course.Course;
import com.example.qts.course.CourseRepository;
import com.example.qts.days_of_week.DaysOfWeek;
import com.example.qts.days_of_week.DaysOfWeekRepository;
import com.example.qts.discipline.Discipline;
import com.example.qts.discipline.DisciplineRepository;
import com.example.qts.professor.ProfessorRepository;
import com.example.qts.qts.QtsRepository;
import com.example.qts.qts_schedule.QtsSchedule;
import com.example.qts.qts_schedule.QtsScheduleRepository;
import com.example.qts.schedule.Schedule;
import com.example.qts.schedule.ScheduleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class QtsService {

    @Autowired
    private QtsRepository qtsRepository;

    @Autowired
    private QtsScheduleRepository qtsScheduleRepository;

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private DisciplineRepository disciplineRepository;

    @Autowired
    private ProfessorRepository professorRepository;

    @Autowired
    private ScheduleRepository scheduleRepository;

    @Autowired
    private DaysOfWeekRepository daysOfWeekRepository;

    public List<QtsSchedule> generateQts(String courseName) {
        Course course = courseRepository.findByNomeCurso(courseName)
                .orElseThrow(() -> new RuntimeException("Course not found"));

        List<Discipline> disciplines = disciplineRepository.findByCourseId(course.getId());

        List<ProfessorAvailability> professorAvailabilities = professorRepository.findAllAvailability();

        Map<Long, Map<Long, List<Long>>> professorDisponibilidade = new HashMap<>();
        for (ProfessorAvailability availability : professorAvailabilities) {
            professorDisponibilidade
                    .computeIfAbsent(availability.getProfessorId(), k -> new HashMap<>())
                    .computeIfAbsent(availability.getDiaSemanaId(), k -> new ArrayList<>())
                    .add(availability.getHorarioId());
        }

        List<ProfessorDiscipline> professorDisciplines = professorRepository.findAllDisciplines();
        Map<Long, List<Long>> disciplinaProfessores = new HashMap<>();
        Map<Long, String> professorNomes = new HashMap<>();
        for (ProfessorDiscipline pd : professorDisciplines) {
            disciplinaProfessores
                    .computeIfAbsent(pd.getDisciplinaId(), k -> new ArrayList<>())
                    .add(pd.getProfessorId());
            professorNomes.put(pd.getProfessorId(), pd.getNomeProfessor());
        }

        List<Schedule> horarios = scheduleRepository.findAll();
        Map<Long, String> horarioDescricao = new HashMap<>();
        for (Schedule schedule : horarios) {
            horarioDescricao.put(schedule.getId(), schedule.getDescricao());
        }

        List<DaysOfWeek> diasSemanaDb = daysOfWeekRepository.findAll();
        Map<Long, String> diasSemana = new HashMap<>();
        for (DaysOfWeek dayOfWeek : diasSemanaDb) {
            diasSemana.put(dayOfWeek.getId(), dayOfWeek.getDiaDaSemana());
        }

        List<QtsSchedule> qtsSchedule = new ArrayList<>();
        Set<String> horarioOcupado = new HashSet<>();
        Set<Long> disciplinasAlocadas = new HashSet<>();

        List<Long> professores = new ArrayList<>(professorDisponibilidade.keySet());
        professores.sort(Comparator.comparingInt(prof -> professorDisponibilidade.get(prof).size()));

        for (Long professorId : professores) {
            Map<Long, List<Long>> disponibilidade = professorDisponibilidade.get(professorId);
            for (Discipline disciplina : disciplines) {
                Long disciplinaId = disciplina.getId();
                if (disciplinasAlocadas.contains(disciplinaId)) {
                    continue;
                }
                if (!disciplinaProfessores.getOrDefault(disciplinaId, Collections.emptyList()).contains(professorId)) {
                    continue;

                    int horasRestantes = disciplina.getCargaHoraria();
                    boolean alocada = false;

                    if (disciplina.getCargaHoraria() >= 80) {
                        for (Map.Entry<Long, List<Long>> entry : disponibilidade.entrySet()) {
                            Long diaId = entry.getKey();
                            List<Long> horariosDia = entry.getValue();
                            if (horariosDia.stream().filter(h -> !horarioOcupado.contains(diaId + h)).count() >= 4) {
                                for (Long horario : horariosDia.subList(0, 4)) {
                                    qtsSchedule.add(new QtsSchedule(null, null, disciplina, professorRepository.findById(professorId).orElse(null), scheduleRepository.findById(horario).orElse(null), daysOfWeekRepository.findById(diaId).orElse(null)));
                                    horarioOcupado.add(diaId + horario);
                                }
                                disciplinasAlocadas.add(disciplinaId);
                                alocada = true;
                                break;
                            }
                        }
                    }

                    if (alocada) {
                        continue;
                    }

                    for (Map.Entry<Long, List<Long>> entry : disponibilidade.entrySet()) {
                        if (horasRestantes <= 0) {
                            break;
                        }
                        Long diaId = entry.getKey();
                        List<Long> horariosDia = entry.getValue();
                        for (Long horario : horariosDia) {
                            if (horasRestantes <= 0) {
                                break;
                            }
                            if (!horarioOcupado.contains(diaId + horario)) {
                                qtsSchedule.add(new QtsSchedule(null, null, disciplina, professorRepository.findById(professorId).orElse(null), scheduleRepository.findById(horario).orElse(null), daysOfWeekRepository.findById(diaId).orElse(null)));
                                horasRestantes -= 20;
                                horarioOcupado.add(diaId + horario);
                            }
                        }
                    }
                    if (horasRestantes <= 0) {
                        disciplinasAlocadas.add(disciplinaId);
                    }
                }
            }

            Qts qts = new Qts();
            qts.setCourse(course);
            qtsRepository.save(qts);

            for (QtsSchedule schedule : qtsSchedule) {
                schedule.setQts(qts);
                qtsScheduleRepository.save(schedule);
            }

            return qtsSchedule;
        }
    }*/
