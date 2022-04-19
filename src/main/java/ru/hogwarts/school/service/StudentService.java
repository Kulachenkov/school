package ru.hogwarts.school.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repository.StudentRepository;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class StudentService {

    private final StudentRepository studentRepository;

    @Autowired
    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    public Student createStudent (Student student) {
        return studentRepository.save(student);
    }

    public Student findStudent(long id) {
        return studentRepository.findById(id).get();
    }

    public Student editStudent(Student student) {
        return studentRepository.save(student);
    }

    public void deleteStudent(long id) {
        studentRepository.deleteById(id);
    }

    public Collection<Student> getStudentsByAge(int age) {
        return studentRepository.findAll().stream()
                .filter(student -> student.getAge() == age)
                .collect(Collectors.toList());
    }
    public Collection<Student> findStudentsByAgeBetween(int min, int max) {
        return studentRepository.findStudentsByAgeBetween(min, max);
    }

    public Collection<Student> getAllStudents() {
        return studentRepository.findAllBy();
    }
    public Collection<Student> findStudentsByFaculty(String name) {
        return studentRepository.findStudentsByFacultyNameIgnoreCase(name);
    }

    public int getNumberOfStudents() {
        return studentRepository.getNumberOfStudents();
    }

    public int getAverageAgeOfStudents() {
        return studentRepository.getAverageAgeOfStudents();
    }

    public List<Student> getLastFiveStudents() {
        return studentRepository.getLastFiveStudents();
    }
}
