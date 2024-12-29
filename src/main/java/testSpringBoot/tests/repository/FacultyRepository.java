package testSpringBoot.tests.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import testSpringBoot.tests.model.Faculty;

import java.util.Collection;
import java.util.Set;

@Repository
public interface FacultyRepository extends JpaRepository<Faculty, Long> {

    Collection<Faculty> findByColorIgnoreCaseOrNameIgnoreCase(String color, String name);
}
