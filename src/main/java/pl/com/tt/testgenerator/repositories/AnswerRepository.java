package pl.com.tt.testgenerator.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import pl.com.tt.testgenerator.entieties.Answer;

import java.util.List;

@Repository
public interface AnswerRepository extends JpaRepository<Answer, Long> {

    @Query(value = "Select a From Answer a Where a.id In (:answerIdTable)")
    List<Answer> allSelectedAnswers(@Param("answerIdTable") Long[] answerIdTable);
}