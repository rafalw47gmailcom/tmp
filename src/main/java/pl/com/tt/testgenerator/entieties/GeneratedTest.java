package pl.com.tt.testgenerator.entieties;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Data
public class GeneratedTest {
    @Id
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @MapsId
    private RecruiterTable recruiterTable;

    @Column
    String link;
    @ManyToMany(mappedBy = "generatedTests", fetch = FetchType.LAZY)
    @JsonIgnoreProperties({"frameworks", "programmingLanguage"})
    private List<ClosedQuestion> closedQuestionList;
    @ManyToMany(mappedBy = "generatedTests", fetch = FetchType.LAZY)
    @JsonIgnoreProperties({"frameworks", "programmingLanguage"})
    private List<OpenQuestion> openQuestionList;

    @Temporal(TemporalType.TIMESTAMP)
    private Date validFrom;
    @Temporal(TemporalType.TIMESTAMP)
    private Date validUntil;

    private boolean finished;
}