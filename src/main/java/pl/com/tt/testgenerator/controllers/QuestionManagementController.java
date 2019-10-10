package pl.com.tt.testgenerator.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.com.tt.testgenerator.entieties.ClosedQuestion;
import pl.com.tt.testgenerator.entieties.OpenQuestion;
import pl.com.tt.testgenerator.repositories.ClosedQuestionRepository;
import pl.com.tt.testgenerator.repositories.OpenQuestionRepository;

import java.util.List;

@RestController
@RequestMapping("/questions")
public class QuestionManagementController {

    @Autowired
    private ClosedQuestionRepository closedQuestionRepository;

    @Autowired
    private OpenQuestionRepository openQuestionRepository;

    @PostMapping("/addclose")
    public void addCloseQuestion(@RequestBody ClosedQuestion closedQuestion) {
        closedQuestionRepository.save(closedQuestion);
    }

    @PostMapping("/addopen")
    public void addOpenQuestion(@RequestBody OpenQuestion openQuestion) {
        openQuestionRepository.save(openQuestion);
    }

    @PostMapping("/deleteclose")
    public void deleteCloseQuestion(@RequestBody Long id) {
        closedQuestionRepository.deleteById(id);
    }

    @DeleteMapping("/deleteopen")
    public void deleteOpenQuestion(@RequestBody Long id) {
        openQuestionRepository.deleteById(id);
    }

    @PostMapping("/findallclose")
    public List<ClosedQuestion> findAllCloseQuestion() {
        return closedQuestionRepository.findAll();
    }

    @PostMapping("/findallopen")
    public List<OpenQuestion> findAllOpenQuestion() {
        return openQuestionRepository.findAll();
    }
}