package pl.com.tt.testgenerator.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.com.tt.testgenerator.entieties.GeneratedTest;

@Repository
public interface GeneratedTestRepository extends JpaRepository<GeneratedTest, Long> {
}
