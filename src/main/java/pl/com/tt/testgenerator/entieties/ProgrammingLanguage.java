package pl.com.tt.testgenerator.entieties;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.ToString;

import javax.persistence.*;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Data
@Entity
@ToString(exclude = {"frameworks"})
//@NamedEntityGraph(name = "ProgrammingLanguage.frameworks",
//      attributeNodes = @NamedAttributeNode("frameworks"))
public class ProgrammingLanguage {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    private String name;

    @OneToMany(mappedBy = "programmingLanguage", fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnoreProperties("programmingLanguage")
    //@JsonIgnore
    private List<Framework> frameworks;

    @OneToMany(mappedBy = "programmingLanguage", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private List<OpenQuestion> openQuestions;

    @OneToMany(mappedBy = "programmingLanguage", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private List<ClosedQuestion> closedQuestions;

    @OneToMany(mappedBy = "programmingLanguage", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private List<RecruiterTable> recruiterTable;

    public ProgrammingLanguage() {
    }
    public ProgrammingLanguage(String name, Framework... frameworks) {
        this.name = name;
        this.frameworks = Stream.of(frameworks).collect(Collectors.toList());
        this.frameworks.forEach(x -> x.setProgrammingLanguage(this));
    }
}

