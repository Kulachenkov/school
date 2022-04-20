package ru.hogwarts.school.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.hogwarts.school.model.Student;

import java.util.Collection;
import java.util.List;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {

    Collection<Student> findStudentsByAgeBetween(int min, int max);

    Collection<Student> findStudentsByFacultyNameIgnoreCase(String name);

    Collection<Student> findAllBy();

    Student getStudentById(long id);

    @Query(value= "SELECT count(id)  FROM student", nativeQuery = true)
    int getNumberOfStudents();

    @Query(value= "SELECT avg(age)  FROM student", nativeQuery = true)
    int getAverageAgeOfStudents();

    @Query(value= "select * from student order by id DESC LIMIT 5", nativeQuery = true)
    List<Student> getLastFiveStudents();
}
