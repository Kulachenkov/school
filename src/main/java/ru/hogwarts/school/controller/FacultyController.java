package ru.hogwarts.school.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.service.FacultyService;

import java.util.Collection;
import java.util.Set;


@RestController
@RequestMapping("faculty") // http://localhost:8080/faculty
public class FacultyController {

    private FacultyService facultyService;


    public FacultyController(FacultyService facultyService) {
        this.facultyService = facultyService;
    }

    @GetMapping("/{id}") // GET
    public ResponseEntity<Faculty> getFaculty(@PathVariable long id) {
        Faculty faculty = facultyService.findFaculty(id);
        if (faculty == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.ok(faculty);
    }

    @PostMapping // POST
    public ResponseEntity<Faculty> createFaculty(@RequestBody Faculty faculty) {
        return ResponseEntity.status(HttpStatus.CREATED).body(facultyService.createFaculty(faculty));
    }

    @PutMapping // PUT
    public ResponseEntity<Faculty> editFaculty(@RequestBody Faculty faculty) {
        Faculty foundStudent = facultyService.editFaculty(faculty);
        if (foundStudent == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        return ResponseEntity.ok(foundStudent);
    }

    @DeleteMapping("/{id}") // DELETE
    public ResponseEntity<Faculty> deleteFaculty(@PathVariable Long id) {
        Faculty deletedFaculty = facultyService.deleteFaculty(id);
        if (deletedFaculty == null) {
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.ok(deletedFaculty);
        }
    }


    @GetMapping // GET
    public Set<Faculty> getFacultyByColorOrName(@RequestParam(required = false) String color,
                                                @RequestParam(required = false) String name) {
        return facultyService.findFacultiesByNameIgnoreCaseOrColorIgnoreCase(name, color);
    }



 }
