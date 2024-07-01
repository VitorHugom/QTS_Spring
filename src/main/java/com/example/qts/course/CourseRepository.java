package com.example.qts.course;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CourseRepository extends JpaRepository<Course, Long> {
    Optional<Course> findByNomeCurso(String nomeCurso);
}
