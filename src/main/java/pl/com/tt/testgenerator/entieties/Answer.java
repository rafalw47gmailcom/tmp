package pl.com.tt.testgenerator.entieties;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@Data
public class Answer {
    @Id
    @GeneratedValue
    private Long id;

    private String content;

    private boolean correct;

}
