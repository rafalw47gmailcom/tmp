package pl.com.tt.testgenerator.entieties;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Data;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import pl.com.tt.testgenerator.entieties.enumclasses.UserType;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
public class Users {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;

    private String password;
    // @Column(nullable = false)
    private String name;
    // @Column(nullable = false)
    private String surname;
    // @Column(nullable = false)
    private String email;

    // nie wiem jak zrobic lazzy fetch-a
    @OneToMany(mappedBy = "recruiter")
    @JsonManagedReference(value = "recruiter")
    @LazyCollection(LazyCollectionOption.FALSE)
    private List<RecruiterTable> recruiterTableRecruiter;

    @OneToMany(mappedBy = "coordinator")
    @JsonManagedReference(value = "coordinator")
    @LazyCollection(LazyCollectionOption.FALSE)
    private List<RecruiterTable> recruiterTableCoordinator;

    @Enumerated(EnumType.STRING)
    private UserType type;
}