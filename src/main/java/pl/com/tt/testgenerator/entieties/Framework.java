package pl.com.tt.testgenerator.entieties;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.ToString;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
@ToString(exclude = {"programmingLanguage"})
public class Framework {
    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn
    @JsonIgnoreProperties("frameworks")
    private ProgrammingLanguage programmingLanguage;

    @OneToMany(mappedBy = "framework")
    @JsonIgnore
    private List<OpenQuestion> openQuestions;

    @OneToMany(mappedBy = "framework")
    @JsonIgnore
    private List<ClosedQuestion> closedQuestions;

    @OneToMany(mappedBy = "framework", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<RecruiterTable> recruiterTable;

    // @OneToMany(fetch = FetchType.EAGER)
    // @JsonIgnoreProperties("programmingLanguage")
    // private List<RecruiterTable> recruiterTables;

    private String name;
}
