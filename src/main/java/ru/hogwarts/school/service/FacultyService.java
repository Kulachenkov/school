package ru.hogwarts.school.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.repository.FacultyRepository;

import java.util.Collection;
import java.util.Set;

@Service
public class FacultyService {

    private final FacultyRepository facultyRepository;

    @Autowired
    public FacultyService(FacultyRepository facultyRepository) {
        this.facultyRepository = facultyRepository;
    }

    public Faculty createFaculty(Faculty faculty) {
        return facultyRepository.save(faculty);
    }

    public Faculty findFaculty(long id) {
        return facultyRepository.getById(id);
    }

    public Faculty editFaculty(Faculty faculty) {
        return facultyRepository.save(faculty);
    }

    public Faculty deleteFaculty(long id) {
        Faculty deletedFaculty = facultyRepository.getById(id);
        facultyRepository.deleteById(id);
        return deletedFaculty;
    }

    public Set<Faculty> findFacultiesByNameIgnoreCaseOrColorIgnoreCase(String name, String color) {
        return facultyRepository.findFacultiesByNameIgnoreCaseOrColorIgnoreCase(name, color);
    }

    public Collection<Faculty> getAllFaculties() {
        return facultyRepository.findAllBy();
    }

    public Collection<Faculty> findFacultyByStudents(long id) {
        return facultyRepository.findFacultyByName(facultyRepository.findFacultyNameIgnoreCase(id));
    }

}
