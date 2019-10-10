package pl.com.tt.testgenerator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import pl.com.tt.testgenerator.dto.GeneralDataDto;
import pl.com.tt.testgenerator.repositories.FrameworkRepository;
import pl.com.tt.testgenerator.repositories.ProgrammingLanguageRepository;
import pl.com.tt.testgenerator.repositories.UsersRepository;

@SpringBootApplication
//@EnableSwagger2
public class TestGeneratorApplication {

    //misja zrobic porządek
    public static final int CLOSED_QUESTION_SIZE = 20;
    public static final int OPEN_QUESTION_SIZE = 10;
    public static final String HOST_NAME = "localhost:8080";

    @Autowired
    private ProgrammingLanguageRepository programmingLanguageRepository;

    @Autowired
    private FrameworkRepository frameworkRepository;

    @Autowired
    private UsersRepository usersRepository;

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    public static void main(String[] args) {
        SpringApplication.run(TestGeneratorApplication.class, args);
    }
/*
        @Transactional
        @Bean
        CommandLineRunner commandLineRunner(){
            return args -> {

                Framework spring = new Framework();
                spring.setName("Spring");
                ProgrammingLanguage programmingLanguage1 = new ProgrammingLanguage("Java", spring);


                //programmingLanguageRepository.save(programmingLanguage1);
                //frameworkRepository.save(spring);

                Framework angular = new Framework();
                angular.setName("Angular");

                Framework react = new Framework();
                react.setName("React");

                Framework vue = new Framework();
                vue.setName("Vue.js");


                ProgrammingLanguage programmingLanguage2 = new ProgrammingLanguage("JavaScript",
                        angular, react, vue);


                Framework django = new Framework();
                django.setName("Django");
                ProgrammingLanguage programmingLanguage3 = new ProgrammingLanguage("Python", django);

                programmingLanguageRepository.save(programmingLanguage1);
                programmingLanguageRepository.save(programmingLanguage2);
                programmingLanguageRepository.save(programmingLanguage3);

            };
        }
 */
    @Bean
    public GeneralDataDto generalDataDto() {
        GeneralDataDto generalDataDto = new GeneralDataDto();
        generalDataDto.setProgrammingLanguages(programmingLanguageRepository.findAll());
        generalDataDto.setUsersList(usersRepository.findAll());
        return generalDataDto;
    }
}
