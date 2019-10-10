package pl.com.tt.testgenerator.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import pl.com.tt.testgenerator.entieties.enumclasses.UserType;

@Data
@AllArgsConstructor
public class UsersDto {
    private Long id;
    private String username;
    private String name;
    private String surname;
    private UserType type;

    public UsersDto() {
    }
}
