package testSpringBoot.tests.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.*;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
public class Faculty {
    @Id
    @GeneratedValue
    private Long facultyId;
    private String name;
    private String color;

    @OneToMany(mappedBy = "faculty")
    private List<Student> students;
}
