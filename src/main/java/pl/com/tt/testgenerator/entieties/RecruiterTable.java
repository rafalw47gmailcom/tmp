package pl.com.tt.testgenerator.entieties;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import pl.com.tt.testgenerator.entieties.enumclasses.RecruitmentState;
import pl.com.tt.testgenerator.entieties.enumclasses.SkillLevel;
import pl.com.tt.testgenerator.entieties.enumclasses.Technology;

import javax.persistence.*;
import java.util.Date;

@Entity
@Data
public class RecruiterTable {
    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne
    @JoinColumn
    @JsonBackReference(value = "coordinator")
    private Users coordinator;

    @ManyToOne
    @JoinColumn
    @JsonBackReference(value = "recruiter")
    private Users recruiter;

    @Column(nullable = false)
    private String candidateName;
    @Column(nullable = false)
    private String candidateSurname;

    @Column(nullable = false)
    private String candidateEmail;

    @Column(nullable = false)
    private String cvFiles;

    @Enumerated(EnumType.STRING)
    private Technology technology;

    @ManyToOne
    @JoinColumn
    @JsonIgnoreProperties({"frameworks"})
    private ProgrammingLanguage programmingLanguage;

    @ManyToOne
    @JoinColumn
    @JsonIgnoreProperties("programmingLanguage")
    private Framework framework;

    @Column
    @Enumerated(EnumType.STRING)
    private SkillLevel skillLevel;

    @Column
    private String targetClient;

    @Temporal(TemporalType.TIMESTAMP)
    private Date createdTime;

    @Enumerated(EnumType.STRING)
    private RecruitmentState state;
}