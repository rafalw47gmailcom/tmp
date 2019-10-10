package pl.com.tt.testgenerator.dto;

import lombok.Data;
import pl.com.tt.testgenerator.entieties.ProgrammingLanguage;
import pl.com.tt.testgenerator.entieties.Users;
import pl.com.tt.testgenerator.entieties.enumclasses.SkillLevel;
import pl.com.tt.testgenerator.entieties.enumclasses.Technology;

import java.util.ArrayList;
import java.util.List;

@Data
public class GeneralDataDto {

    SkillLevel[] skillLevels = SkillLevel.values();
    Technology[] technologies = Technology.values();
    List<UsersDto> usersDto = new ArrayList<>();
    List<ProgrammingLanguage> programmingLanguages;

    public void setUsersList(List<Users> users) {
        for (Users user : users) {
            this.usersDto.add(new UsersDto(user.getId(),
                    user.getUsername(),
                    user.getName(),
                    user.getSurname(),
                    user.getType()));
        }
    }
}