package ru.hogwarts.school;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import ru.hogwarts.school.controller.StudentController;
import ru.hogwarts.school.model.Student;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class StudentControllerTest {
    @LocalServerPort
    private int port;

    @Autowired
    private StudentController studentController;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void contextLoads() throws Exception {
        Assertions.assertThat(studentController).isNotNull();
    }

    @Test // POST
    public void postStudentTest() {
        Student student = createStudent("TestStudent", 18);
        ResponseEntity<Student> response = requestForStudentCreating(student);
        testingStudentAfterCreating(response);
        deleteStudent(student);
    }

    @Test // GET
    public void getStudentByIdTest() {
        Student student = createStudent("TestStudent", 19);
        ResponseEntity<Student> response = requestForStudentCreating(student);
        testingStudentAfterCreating(response);
        Assertions.assertThat(this.restTemplate.getForEntity("http://localhost:" + port + "/student/" + student.getId() , Student.class)).isNotNull();
        deleteStudent(student);
    }

    @Test //GET
    public void getStudentsByAgeTest() {
        Student student = createStudent("TestStudent", 19);
        ResponseEntity<Student> response = requestForStudentCreating(student);
        testingStudentAfterCreating(response);
        Assertions.assertThat(this.restTemplate.getForEntity("http://localhost:" + port + "/student/" + student.getAge() , Student.class)).isNotNull();
        deleteStudent(student);
    }

    @Test //DELETE
    public void deleteStudentTest() {
        Student student = createStudent("TestStudent", 19);
        ResponseEntity<Student> response = requestForStudentCreating(student);
        testingStudentAfterCreating(response);
        Student createdStudent = response.getBody();
        deleteStudent(createdStudent);
        ResponseEntity<Student> emptyResponse = restTemplate.getForEntity("http://localhost:" + port
                + "/student/" + student.getId(), Student.class);
        Assertions.assertThat(emptyResponse.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }

    private void deleteStudent(Student student) {
        restTemplate.delete("http://localhost:" + port + "/student/" + student.getId());
    }

    private Student createStudent(String name, int age) {
        Student student = new Student();
        student.setName(name);
        student.setAge(age);
        return student;
    }

    private ResponseEntity<Student> requestForStudentCreating(Student student) {
        return restTemplate.postForEntity("http://localhost:" + port + "/student", student, Student.class);
    }

    private void testingStudentAfterCreating(ResponseEntity<Student> response) {
        Assertions.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        Assertions.assertThat(response.getBody()).isNotNull();
        Assertions.assertThat(response.getBody().getId()).isNotNull();
    }

}
