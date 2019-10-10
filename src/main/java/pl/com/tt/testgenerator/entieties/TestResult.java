package pl.com.tt.testgenerator.entieties;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
public class TestResult {

    @Id
    private Long id;
    private int closeAnswerPoints;
    private int openAnswerPoints;
    private int sumPoints;
    @OneToOne(fetch = FetchType.LAZY)
    @MapsId
    private GeneratedTest generatedTest;

    private Long[] closeQuestionAnswers;
    private String[] openQuestionAnswers;
    private String[] comments;
    private String finalComment;
}
