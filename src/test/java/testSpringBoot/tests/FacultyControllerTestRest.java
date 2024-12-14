package testSpringBoot.tests;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import testSpringBoot.tests.controller.FacultyController;
import testSpringBoot.tests.model.Faculty;
import testSpringBoot.tests.model.Student;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class FacultyControllerTestRest {

    @LocalServerPort
    private int port;

    @Autowired
    private FacultyController facultyController;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void testGetFaculty() throws Exception {
        Faculty faculty = new Faculty();
        faculty.setFacultyId(1L);
        faculty.setName("Nikki");
        faculty.setColor("Green");

        Faculty actual = this.restTemplate.getForObject(
                "http://localhost:" + port + "/faculty/{facultyId}", Faculty.class, faculty.getFacultyId());

        Assertions.assertThat(actual.getFacultyId()).isEqualTo(faculty.getFacultyId());
        Assertions.assertThat(actual.getName()).isEqualTo(faculty.getName());
        Assertions.assertThat(actual.getColor()).isEqualTo(faculty.getColor());
    }

    @Test
    public void testPostFaculty() throws Exception {
        Faculty faculty = new Faculty();
        faculty.setName("Zoo");
        faculty.setColor("Pink");
        Faculty actual = this.restTemplate.postForObject("http://localhost:" + port + "/faculty", faculty, Faculty.class);

        Assertions.assertThat(actual.getFacultyId()).isNotNull();
        Assertions.assertThat(actual.getName()).isEqualTo("Zoo");
        Assertions.assertThat(actual.getColor()).isEqualTo("Pink");
    }

    @Test
    public void testPutFaculty() throws Exception {
        Faculty faculty = new Faculty();
        faculty.setName("Zoo");
        faculty.setColor("Pink");
        Faculty actual = this.restTemplate.postForObject("http://localhost:" + port + "/faculty", faculty, Faculty.class);
        actual.setName("Cat");
        actual.setColor("Red");
        ResponseEntity<Faculty> updated = this.restTemplate.exchange
                ("http://localhost:" + port + "/faculty/" + actual.getFacultyId(), HttpMethod.PUT, new HttpEntity<>(actual), Faculty.class);
        Assertions.assertThat(updated.getBody().getFacultyId()).isNotNull();
        Assertions.assertThat(updated.getBody().getName()).isEqualTo("Cat");
        Assertions.assertThat(updated.getBody().getColor()).isEqualTo("Red");
    }

    @Test
    public void testDeleteFaculty() throws Exception {
        Faculty faculty = new Faculty();
        faculty.setName("Zoo");
        faculty.setColor("Pink");
        Faculty actual = this.restTemplate.postForObject("http://localhost:" + port + "/faculty", faculty, Faculty.class);
        ResponseEntity<String> response = restTemplate.exchange
                ("http://localhost:" + port + "/faculty/" + actual.getFacultyId(), HttpMethod.DELETE, null, String.class);
        Faculty f = restTemplate.getForObject("http://localhost:" + port + "/faculty/" +
                actual.getFacultyId(), Faculty.class);
        Assertions.assertThat(f).isNull();
    }

    @Test
    public void testGetFacultyByColor() throws Exception {
        String color = "Blue";
        ResponseEntity <List<Faculty>> responseEntity = restTemplate.exchange(
                "http://localhost:" + port + "/faculty/color/" + color,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference <List<Faculty>>() {}
        );
        List<Faculty> facultyList = responseEntity.getBody();

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(facultyList).isNotNull();
    }

    @Test
    public void testGetStudentByFaculty() throws Exception {
        Long facultyId = 1L;
        ResponseEntity<List<Student>> responseEntity = restTemplate.exchange
                ("http://localhost:" + port + "/faculty/" + facultyId + "/students", HttpMethod.GET, null, new ParameterizedTypeReference<List<Student>>() {});
        List<Student> studentList = responseEntity.getBody();
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(studentList).isNotNull();
    }
}
