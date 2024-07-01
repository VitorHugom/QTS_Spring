package com.example.qts.service;

import com.example.qts.course.Course;
import com.example.qts.course.CourseRepository;
import com.example.qts.course.CourseRequestDTO;
import com.example.qts.course.CourseResponseDTO;
import com.example.qts.discipline.Discipline;
import com.example.qts.discipline.DisciplineRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CourseService {

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private DisciplineRepository disciplineRepository;

    public List<CourseResponseDTO> getAllCourses() {
        return courseRepository.findAll().stream().map(CourseResponseDTO::new).collect(Collectors.toList());
    }

    public CourseResponseDTO getCourseById(Long id) {
        Course course = courseRepository.findById(id).orElseThrow(() -> new RuntimeException("Curso não encontrado"));
        return new CourseResponseDTO(course);
    }

    public CourseResponseDTO createCourse(CourseRequestDTO request) {
        List<Discipline> disciplines = disciplineRepository.findAllById(request.disciplineIds());
        Course course = new Course(request);
        course.setDisciplines(disciplines);
        courseRepository.save(course);
        return new CourseResponseDTO(course);
    }

    public CourseResponseDTO updateCourse(Long id, CourseRequestDTO request) {
        Course course = courseRepository.findById(id).orElseThrow(() -> new RuntimeException("Curso não encontrado"));
        List<Discipline> disciplines = disciplineRepository.findAllById(request.disciplineIds());
        course.setNomeCurso(request.nome_curso());
        course.setCarga_horaria(request.carga_horaria());
        course.setDisciplines(disciplines);
        courseRepository.save(course);
        return new CourseResponseDTO(course);
    }

    public void deleteCourse(Long id) {
        courseRepository.deleteById(id);
    }
}
