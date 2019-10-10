package pl.com.tt.testgenerator.services;

import pl.com.tt.testgenerator.dto.FinalCheckDto;
import pl.com.tt.testgenerator.dto.OpenAnswerCheckDto;

import java.io.IOException;

public interface EvaluationService {
    OpenAnswerCheckDto checkOpenAnswer(long id);

    int[] finalCheck(FinalCheckDto finalCheckDto) throws Exception;

    void summary(long id, String finalComment) throws IOException;
}
