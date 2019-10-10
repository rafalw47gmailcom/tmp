package pl.com.tt.testgenerator.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.com.tt.testgenerator.entieties.ProgrammingLanguage;

public interface ProgrammingLanguageRepository extends JpaRepository<ProgrammingLanguage, Long> {

    //  @Query(value = "Select p From ProgrammingLanguage p left join fetch p.frameworks")
    //List<ProgrammingLanguage> findAllWithFramework();

    // @EntityGraph(value = "ProgrammingLanguage.frameworks")
    // List<ProgrammingLanguage> findAll();

    ProgrammingLanguage findByName(String name);
}
