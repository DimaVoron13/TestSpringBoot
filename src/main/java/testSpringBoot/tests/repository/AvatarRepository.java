package testSpringBoot.tests.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import testSpringBoot.tests.model.Avatar;

import java.util.Optional;

@Repository
public interface AvatarRepository extends JpaRepository<Avatar, Long> {

    public Optional<Avatar> findByStudentId(Long studentId);

    public boolean existsByStudentId(Long studentId);

    public void deleteByStudentId(Long studentId);

}
