package testSpringBoot.tests;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import testSpringBoot.tests.controller.StudentController;
import testSpringBoot.tests.model.Student;
import testSpringBoot.tests.repository.FacultyRepository;
import testSpringBoot.tests.repository.StudentRepository;
import testSpringBoot.tests.service.FacultyService;
import testSpringBoot.tests.service.StudentService;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest
public class StudentControllerTestWeb {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private StudentRepository studentRepository;

    @MockBean
    private FacultyRepository facultyRepository;

    @MockBean
    private StudentService studentService;

    @MockBean
    private FacultyService facultyService;

    @InjectMocks
    private StudentController studentController;

    @Test
    public void testGetStudent() throws Exception {
        Student student = new Student();
        student.setStudentId(1L);
        student.setName("John");
        student.setAge(20);

        when(studentService.getStudentById(1)).thenReturn(student);

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/student", 1)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.studentId").value(1))
                .andExpect(jsonPath("$.name").value("John"))
                .andExpect(jsonPath("$.age").value(20));
    }
}
