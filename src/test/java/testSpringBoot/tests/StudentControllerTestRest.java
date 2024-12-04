package testSpringBoot.tests;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import testSpringBoot.tests.controller.StudentController;
import testSpringBoot.tests.model.Faculty;
import testSpringBoot.tests.model.Student;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class StudentControllerTestRest {
    @LocalServerPort
    private int port;

    @Autowired
    private StudentController studentController;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void testGetFaculty() throws Exception {
        Student student = new Student();
        student.setStudentId(1L);
        student.setName("Kate");
        student.setAge(4);

        Student actual = this.restTemplate.getForObject("http://localhost:" + port + "/faculty/{1L}", Student.class, student.getStudentId());

        Assertions.assertThat(actual.getStudentId()).isEqualTo(1L);
        Assertions.assertThat(actual.getName()).isEqualTo("Kate");
        Assertions.assertThat(actual.getAge()).isEqualTo(4);
    }

    @Test
    public void testPostStudent() throws Exception {
        Student student = new Student();
        student.setName("Kate");
        student.setAge(4);
        Student actual = this.restTemplate.postForObject("http://localhost:" + port + "/student", student, Student.class);

        Assertions.assertThat(actual.getStudentId()).isNotNull();
        Assertions.assertThat(actual.getName()).isEqualTo("Kate");
        Assertions.assertThat(actual.getAge()).isEqualTo(4);
    }

    @Test
    public void testPutStudent() throws Exception {
        Student student = new Student();
        student.setName("Mary");
        student.setAge(30);
        Student actual = this.restTemplate.postForObject("http://localhost:" + port + "/student", student, Student.class);
        actual.setName("Robin");
        actual.setAge(8);
        ResponseEntity<Student> updated = this.restTemplate.exchange
                ("http://localhost:" + port + "/student/" + actual.getStudentId(), HttpMethod.PUT, new HttpEntity<>(actual), Student.class);

        Assertions.assertThat(updated.getBody().getStudentId()).isNotNull();
        Assertions.assertThat(updated.getBody().getName()).isEqualTo("Robin");
        Assertions.assertThat(updated.getBody().getAge()).isEqualTo(8);

    }

    @Test
    public void testDeleteStudent() throws Exception {
        Student student = new Student();
        student.setName("Mary");
        student.setAge(30);
        Faculty actual = this.restTemplate.postForObject("http://localhost:" + port + "/faculty", student, Faculty.class);
        ResponseEntity<String> response = restTemplate.exchange
                ("http://localhost:" + port + "/student/" + actual.getStudents(), HttpMethod.DELETE, null, String.class);

        Student s = restTemplate.getForObject("http://localhost:" + port + "/student/" +
                actual.getStudents(), Student.class);
        Assertions.assertThat(s).isNull();

    }

    @Test
    public void testGetStudentsByAge() throws Exception {

    }

    public void TestGetFacultyByStudentId() throws Exception {

    }
}
