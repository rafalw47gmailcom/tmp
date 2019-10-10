package pl.com.tt.testgenerator.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import pl.com.tt.testgenerator.dto.FinalCheckDto;
import pl.com.tt.testgenerator.dto.OpenAnswerCheckDto;
import pl.com.tt.testgenerator.services.EvaluationService;

import java.io.IOException;

@RestController
public class EvaluationController {

    @Autowired
    private EvaluationService evaluationService;

    @PostMapping("/checkopenanswers")
    public OpenAnswerCheckDto checkOpenAnswers(long id) {
        return evaluationService.checkOpenAnswer(id);
    }

    @PostMapping("/finalcheck")
    public int[] finalCheckDto(@RequestBody FinalCheckDto finalCheckDto) throws Exception {
        return evaluationService.finalCheck(finalCheckDto);
    }

    @PostMapping("/summary")
    public void summary(long id, String finalComment) throws IOException {
        evaluationService.summary(id, finalComment);
    }
}
