package ru.hogwarts.school.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;

import java.util.Collection;
import java.util.Set;

public interface FacultyRepository extends JpaRepository<Faculty, Long> {
    Set<Faculty> findFacultiesByNameIgnoreCaseOrColorIgnoreCase(String name, String color);

    Collection<Faculty> findAllBy();

    @Query(nativeQuery = true, value = "select faculty.name from student, faculty where student.faculty_id = faculty.id and student.id=:id")
    String findFacultyNameIgnoreCase(long id);

    Collection<Faculty> findFacultyByName(String facultyName);

}
