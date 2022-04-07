package ru.hogwarts.school.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.service.StudentService;

import java.util.Collection;


@RestController
@RequestMapping("student") // http://localhost:8080/student
public class StudentController {

    private final StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @GetMapping("{id}") // GET
    public ResponseEntity<Student> getStudent(@PathVariable long id) {
        Student student = studentService.findStudent(id);
        if (student == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.ok(student);
    }

    @PostMapping // POST
    public Student createStudent(@RequestBody Student student) {
        return studentService.createStudent(student);
    }

    @PutMapping // PUT
    public ResponseEntity<Student> editStudent(@RequestBody Student student) {
        Student foundStudent = studentService.editStudent(student);
        if (foundStudent == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        return ResponseEntity.ok(foundStudent);
    }

    @DeleteMapping("{id}") // DELETE
    public ResponseEntity deleteStudent(@PathVariable long id) {
        studentService.deleteStudent(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping ("filter/")// GET
    public ResponseEntity<Collection<Student>> getStudentsByAge(@RequestParam (required = false) Integer age,
                                                       @RequestParam (required = false) Integer min,
                                                       @RequestParam (required = false) Integer max,
                                                       @RequestParam (required = false) String facultyName) {
        if (min != null && max != null && min <= max) {
            return ResponseEntity.ok(studentService.findStudentsByAgeBetween(min, max));
        }
        if (age != null) {
            return ResponseEntity.ok(studentService.getStudentsByAge(age));
        }
        if (facultyName != null && !facultyName.isBlank()) {
            return ResponseEntity.ok(studentService.findStudentsByFaculty(facultyName));
        }
        return ResponseEntity.ok(studentService.getAllStudents());
    }

}