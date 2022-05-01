package ru.hogwarts.school.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;

import java.util.Collection;
import java.util.Set;
@Repository
public interface FacultyRepository extends JpaRepository<Faculty, Long> {
    Set<Faculty> findFacultiesByNameIgnoreCaseOrColorIgnoreCase(String name, String color);



}
