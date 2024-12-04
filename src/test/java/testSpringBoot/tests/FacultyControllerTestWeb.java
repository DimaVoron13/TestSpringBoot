package testSpringBoot.tests;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import testSpringBoot.tests.controller.FacultyController;
import testSpringBoot.tests.model.Faculty;
import testSpringBoot.tests.model.Student;
import testSpringBoot.tests.repository.FacultyRepository;
import testSpringBoot.tests.repository.StudentRepository;
import testSpringBoot.tests.service.FacultyService;
import testSpringBoot.tests.service.StudentService;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest
public class FacultyControllerTestWeb {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private StudentRepository studentRepository;

    @MockBean
    private FacultyRepository facultyRepository;

    @SpyBean
    private StudentService studentService;

    @SpyBean
    private FacultyService facultyService;

    @InjectMocks
    private FacultyController facultyController;

    @Test
    public void testGetFaculty() throws Exception {
        Faculty faculty = new Faculty();
        faculty.setFacultyId(1L);
        faculty.setName("Small");
        faculty.setColor("Blue");
        when(facultyService.getFacultyById(1)).thenReturn(faculty);

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/faculty/", 1)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.facultyId").value(1))
                .andExpect(jsonPath("$.name").value("Small"))
                .andExpect(jsonPath("$.color").value("Blue"));
    }

    @Test
    public void testCreateFaculty() throws Exception {
        Faculty faculty = new Faculty();
        faculty.setFacultyId(1L);
        faculty.setName("Faculty");
        faculty.setColor("Red");

        when(facultyService.createFaculty("Faculty", "Red")).thenReturn(faculty);


        mockMvc.perform(MockMvcRequestBuilders.post("/faculty")
                        .param("name", "Faculty")
                        .param("color", "Red"))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(1L))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("Faculty"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.color").value("Red"));
    }

    @Test
    public void testUpdateFaculty() throws Exception {
        Faculty faculty = new Faculty();
        faculty.setFacultyId(1L);
        faculty.setName("Faculty");
        faculty.setColor("Red");

        when(facultyService.updateFaculty(1L, "Faculty", "Red")).thenReturn(faculty);


        mockMvc.perform(MockMvcRequestBuilders.put("/faculty/1")
                        .param("name", "Faculty")
                        .param("color", "Red"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(1L))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("Faculty"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.color").value("Red"));
    }

    @Test
    public void testDeleteFaculty() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                        .delete("/faculty/{id}", 1))
                .andExpect(status().isOk())
                .andExpect(content().string("Faculty deleted successfully"));
    }

    @Test
    public void testGetFacultyByColor() throws Exception {
        Faculty faculty1 = new Faculty();
        faculty1.setFacultyId(1L);
        faculty1.setName("Faculty1");
        faculty1.setColor("Red");

        Faculty faculty2 = new Faculty();
        faculty2.setFacultyId(2L);
        faculty2.setName("Faculty2");
        faculty2.setColor("Red");

        List<Faculty> faculties = Arrays.asList(faculty1, faculty2);

        when(facultyService.getFacultyByColor("Red")).thenReturn(faculties);

        mockMvc.perform(MockMvcRequestBuilders.get("/faculty/color/Red"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void testGetStudentByFaculty() throws Exception {
        Faculty faculty = new Faculty();
        faculty.setFacultyId(1L);
        faculty.setName("Big");

        Student student1 = new Student();
        student1.setStudentId(1L);
        student1.setName("Nasty");

        Student student2 = new Student();
        student2.setStudentId(2L);
        student2.setName("Lena");

        List<Student> students = Arrays.asList(student1, student2);

        Mockito.when(facultyService.getStudentByFaculty(1L)).thenReturn(students);

        mockMvc.perform(MockMvcRequestBuilders.get("/1/students"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].id").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].name").value("Student 1"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].id").value(2))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].name").value("Student 2"));
    }
}
