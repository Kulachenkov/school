package ru.hogwarts.school.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;

import java.util.Collection;

public interface FacultyRepository extends JpaRepository<Faculty, Long> {
    Collection<Faculty> findFacultiesByNameIgnoreCaseOrColorIgnoreCase(String name, String color);

    Collection<Faculty> findAllBy();

    @Query(nativeQuery = true, value = "select faculty.name from student, faculty where student.faculty_id = faculty.id and student.id=:id")
    String findFacultyNameIgnoreCase(long id);

    Collection<Faculty> findFacultyByName(String facultyName);

}
