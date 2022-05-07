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

    public List<String> getStudentsByName() {
        return studentRepository.findAll().stream()
                .map(Student::getName)
                .map(String::toUpperCase)
                .filter(s -> s.startsWith("A"))
                .sorted()
                .collect(Collectors.toList());
    }

    public Double getAverageAge() {
        return studentRepository.findAll().stream()
                .mapToInt(Student::getAge)
                .average().getAsDouble();
    }

    public void getStudentsFromStreams() {
        studentRepository.findAll().stream().parallel().limit(6).forEach(s -> {
                System.out.println(s.toString());
            });

//        System.out.println(studentRepository.findAll().get(1).toString() + " is a student # 1");
//        System.out.println(studentRepository.findAll().get(2).toString() + " is a student # 2");
//
//        new Thread(() -> {
//            System.out.println(studentRepository.findAll().get(3).toString() + " is a student # 3");
//            System.out.println(studentRepository.findAll().get(4).toString() + " is a student # 4");
//        }).start();
//
//
//        new Thread(() -> {
//            System.out.println(studentRepository.findAll().get(5).toString() + " is a student # 5");
//            System.out.println(studentRepository.findAll().get(6).toString() + " is a student # 6");
//        }).start();
    }

    public synchronized void getStudentsFromStreamsSync() {
        getStudent(1);
        getStudent(2);
        new Thread(() -> {
            getStudent(3);
            getStudent(4);
        }).start();
        new Thread(() -> {
            getStudent(5);
            getStudent(6);
        }).start();
    }

    private synchronized void getStudent(int number) {
        System.out.println(studentRepository.findAll().get(number).toString());
    }

}
