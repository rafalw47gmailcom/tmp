package pl.com.tt.testgenerator.dto;

import lombok.Data;
import org.springframework.lang.Nullable;
import org.springframework.web.multipart.MultipartFile;
import pl.com.tt.testgenerator.entieties.Framework;
import pl.com.tt.testgenerator.entieties.ProgrammingLanguage;
import pl.com.tt.testgenerator.entieties.Users;
import pl.com.tt.testgenerator.entieties.enumclasses.SkillLevel;
import pl.com.tt.testgenerator.entieties.enumclasses.Technology;

import javax.validation.constraints.Email;
import java.util.Date;

@Data
public class RecruiterTableDto {

    @Nullable
    private Long verificationId;

    private Users coordinator;

    private Users recruiter;

    private String candidateName;

    private String candidateSurname;
    @Email
    private String candidateEmail;

    private MultipartFile cvFile;

    private Technology technology;

    private ProgrammingLanguage programmingLanguage;

    private Framework framework;

    private SkillLevel skillLevel;

    private String targetClient;

    private Date createdTime;

    public RecruiterTableDto() {
    }
}