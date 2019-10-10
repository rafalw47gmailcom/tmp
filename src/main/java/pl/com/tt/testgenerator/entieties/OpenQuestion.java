package pl.com.tt.testgenerator.entieties;

        import com.fasterxml.jackson.annotation.JsonIgnore;
        import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
        import lombok.Data;

        import javax.persistence.Entity;
        import javax.persistence.JoinColumn;
        import javax.persistence.ManyToMany;
        import javax.persistence.ManyToOne;
        import java.util.List;


@Entity
@Data
public class OpenQuestion extends AbstractQuestion {

    private String suggestedAnswer;

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