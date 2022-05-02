package ru.hogwarts.school.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repository.StudentRepository;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class StudentService {

    Logger logger = LoggerFactory.getLogger(StudentService.class);

    private final StudentRepository studentRepository;

    public StudentService(StudentRepository studentRepository) {
        logger.info("The Faculty Service is run");
        this.studentRepository = studentRepository;
    }

    public Student createStudent (Student student) {
        logger.info("The createStudent is run");
        return studentRepository.save(student);
    }

    public Student findStudent(long id) {
        logger.info("The findStudent is run");
        return studentRepository.findById(id).get();
    }

    public Student editStudent(Student student) {
        logger.info("The editStudent is run");
        return studentRepository.save(student);
    }

    public void deleteStudent(long id) {
        logger.info("The deleteStudent is run");
        studentRepository.deleteById(id);
    }

    public Collection<Student> getStudentsByAge(int age) {
        logger.info("The getStudentByAge is run");
        return studentRepository.findAll().stream()
                .filter(student -> student.getAge() == age)
                .collect(Collectors.toList());
    }
    public Collection<Student> findStudentsByAgeBetween(int min, int max) {
        logger.info("The findStudentsByAgeBetween is run");
        return studentRepository.findStudentsByAgeBetween(min, max);
    }

    public Collection<Student> getAllStudents() {
        logger.info("The getAllStudents is run");
        return studentRepository.findAllBy();
    }
    public Collection<Student> findStudentsByFaculty(String name) {
        logger.info("The findStudentsByFaculty is run");
        return studentRepository.findStudentsByFacultyNameIgnoreCase(name);
    }

    public int getNumberOfStudents() {
        logger.info("The  getNumberOfStudents is run");
        return studentRepository.getNumberOfStudents();
    }

    public int getAverageAgeOfStudents() {
        logger.info("The  getAverageAgeOfStudents is run");
        return studentRepository.getAverageAgeOfStudents();
    }

    public List<Student> getLastFiveStudents() {
        logger.info("The getLastFiveStudents is run");
        return studentRepository.getLastFiveStudents();
    }
}
