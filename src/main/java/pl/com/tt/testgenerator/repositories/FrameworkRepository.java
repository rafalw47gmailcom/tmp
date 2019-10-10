package pl.com.tt.testgenerator.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.com.tt.testgenerator.entieties.Framework;

public interface FrameworkRepository extends JpaRepository<Framework, Long> {
}
