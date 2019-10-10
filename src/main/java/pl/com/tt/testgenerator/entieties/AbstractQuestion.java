package pl.com.tt.testgenerator.entieties;

import lombok.Data;
import pl.com.tt.testgenerator.entieties.enumclasses.SkillLevel;
import pl.com.tt.testgenerator.entieties.enumclasses.Technology;

import javax.persistence.*;

@MappedSuperclass
@Data
abstract class AbstractQuestion {
    @Id
    @GeneratedValue
    private Long id;

    @Column(unique = true)
    private String content;

    @Enumerated(EnumType.STRING)
    private Technology technology;

    @Enumerated(EnumType.STRING)
    private SkillLevel skillLevel;
}
