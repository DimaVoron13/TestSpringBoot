package testSpringBoot.tests.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import testSpringBoot.tests.model.Student;

import java.util.List;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {
    List<Student> getByAge(int age);

    List<Student> findByAgeBetween(int minAge, int maxAge);

    Student getById(Long studentId);
}
