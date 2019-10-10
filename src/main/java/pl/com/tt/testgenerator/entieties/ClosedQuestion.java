package pl.com.tt.testgenerator.entieties;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import javax.persistence.*;
import java.util.List;
@Entity
@Data
public class ClosedQuestion extends AbstractQuestion {

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "closedQuestionId")
    private List<Answer> answerList;

    @ManyToOne()
    @JoinColumn
    @JsonIgnoreProperties({"frameworks"})
    private ProgrammingLanguage programmingLanguage;

    @ManyToOne()
    @JoinColumn
    @JsonIgnoreProperties({"programmingLanguage"})
    private Framework framework;

    @ManyToMany
    @JsonIgnore
    private List<GeneratedTest> generatedTests;
}