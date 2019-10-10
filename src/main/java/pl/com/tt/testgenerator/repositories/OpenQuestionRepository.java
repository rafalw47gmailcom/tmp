package pl.com.tt.testgenerator.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import pl.com.tt.testgenerator.entieties.Framework;
import pl.com.tt.testgenerator.entieties.OpenQuestion;
import pl.com.tt.testgenerator.entieties.ProgrammingLanguage;
import pl.com.tt.testgenerator.entieties.enumclasses.SkillLevel;
import pl.com.tt.testgenerator.entieties.enumclasses.Technology;

import java.util.List;

@Repository
public interface OpenQuestionRepository extends JpaRepository<OpenQuestion, Long> {

    @Query(value = "Select q From OpenQuestion q Where q.skillLevel = :skillLevel " +
            "And q.technology = :technology And q.programmingLanguage = :programmingLanguage " +
            "And (q.framework = :framework Or q.framework is null)")
    List<OpenQuestion> findAppropriateQuestions(@Param("skillLevel") SkillLevel skillLevel,
                                                @Param("technology") Technology technology,
                                                @Param("programmingLanguage") ProgrammingLanguage programmingLanguage,
                                                @Param("framework") Framework framework);
}
