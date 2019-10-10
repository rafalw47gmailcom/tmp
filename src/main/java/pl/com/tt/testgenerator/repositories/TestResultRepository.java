package pl.com.tt.testgenerator.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import pl.com.tt.testgenerator.entieties.TestResult;

@Repository
public interface TestResultRepository extends JpaRepository<TestResult, Long> {

    @Modifying
    @Query(value = "Update TestResult t Set t.comments = :comments, t.openAnswerPoints = :openAnswerPoints" +
            ",t.sumPoints = :sumPoints Where t.id = :id")
    void setFinalCheck(@Param("comments") String[] comments, @Param("openAnswerPoints") int openAnswerPoints,
                       @Param("sumPoints") int sumPoints, @Param("id") long id);

    @Modifying
    @Query(value = "Update TestResult t Set t.finalComment = :finalComment Where t.id = :id")
    void setFinalComment(@Param("finalComment") String finalComment, @Param("id") long id);

}
