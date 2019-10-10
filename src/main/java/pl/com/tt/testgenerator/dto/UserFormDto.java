package pl.com.tt.testgenerator.dto;

import lombok.Getter;
import pl.com.tt.testgenerator.entieties.enumclasses.UserType;

@Getter
public class UserFormDto {

    private String username;

    private String password;

    private String name;

    private String surname;

    private String email;

    private UserType type;
}