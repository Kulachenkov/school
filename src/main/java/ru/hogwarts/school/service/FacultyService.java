package ru.hogwarts.school.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.repository.FacultyRepository;

import java.util.Set;

@Service
public class FacultyService {

    Logger logger = LoggerFactory.getLogger(FacultyService.class);

    private final FacultyRepository facultyRepository;

    public FacultyService(FacultyRepository facultyRepository) {
        logger.info("The Faculty Service is run");
        this.facultyRepository = facultyRepository;
    }

    public Faculty createFaculty(Faculty faculty) {
        logger.info("The createFaculty method  is run");
        return facultyRepository.save(faculty);
    }

    public Faculty findFaculty(long id) {
        logger.debug("The findFaculty method  is run");
        return facultyRepository.getById(id);
    }

    public Faculty editFaculty(Faculty faculty) {
        logger.info("The editFaculty method  is run");
        return facultyRepository.save(faculty);
    }

    public Faculty deleteFaculty(long id) {
        logger.info("The deleteFaculty method  is run");
        Faculty deletedFaculty = facultyRepository.getById(id);
        logger.debug("The deletedFaculty object is created");
        facultyRepository.deleteById(id);
        logger.debug("The facullty was delete");
        return deletedFaculty;
    }

    public Set<Faculty> findFacultiesByNameIgnoreCaseOrColorIgnoreCase(String name, String color) {
        logger.info("The findFacultiesByNameIgnoreCaseOrColorIgnoreCase method  is run");
        return facultyRepository.findFacultiesByNameIgnoreCaseOrColorIgnoreCase(name, color);
    }


}
