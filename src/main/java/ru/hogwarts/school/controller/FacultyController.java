package ru.hogwarts.school.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.service.FacultyService;

import java.util.Collection;


@RestController
@RequestMapping("faculty") // http://localhost:8080/faculty
public class FacultyController {

    private final FacultyService facultyService;

    public FacultyController(FacultyService facultyService) {
        this.facultyService = facultyService;
    }

    @GetMapping("{id}") // GET
    public ResponseEntity<Faculty> getFaculty(@PathVariable long id) {
        Faculty faculty = facultyService.findFaculty(id);
        if (faculty == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.ok(faculty);
    }

    @PostMapping // POST
    public Faculty createFaculty(@RequestBody Faculty faculty) {
        return facultyService.createFaculty(faculty);
    }

    @PutMapping // PUT
    public ResponseEntity<Faculty> editFaculty(@RequestBody Faculty faculty) {
        Faculty foundStudent = facultyService.editFaculty(faculty);
        if (foundStudent == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        return ResponseEntity.ok(foundStudent);
    }

    @DeleteMapping("{id}") // DELETE
    public ResponseEntity<Void> deleteFaculty(@PathVariable long id) {
        facultyService.deleteFaculty(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("filters/") // GET
    public ResponseEntity<Collection<Faculty>> getFacultyByColorOrName(@RequestParam(required = false) String color,
                                                                       @RequestParam(required = false) String name,
                                                                       @RequestParam(required = false) long id) {
        if ((color != null && !color.isBlank()) || (name!=null && !name.isBlank())) {
            return ResponseEntity.ok(facultyService.findFacultiesByNameIgnoreCaseOrColorIgnoreCase(name, color));
        }
        if (id != 0) {
            return ResponseEntity.ok(facultyService.findFacultyByStudents(id));
        }
        return ResponseEntity.ok(facultyService.getAllFaculties());
    }

 }
