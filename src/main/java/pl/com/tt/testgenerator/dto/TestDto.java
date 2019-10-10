package pl.com.tt.testgenerator.dto;

import pl.com.tt.testgenerator.entieties.Answer;
import pl.com.tt.testgenerator.entieties.ClosedQuestion;
import pl.com.tt.testgenerator.entieties.GeneratedTest;
import pl.com.tt.testgenerator.entieties.OpenQuestion;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TestDto {

    private Long id;
    private Map<String, List<Map<Long, String>>> closedQuestionMap = new HashMap<>();
    private List<String> openQuestionList = new ArrayList<>();

    public TestDto(GeneratedTest generatedTest) {
        id = generatedTest.getId();
        List<Map<Long, String>> bufList = new ArrayList<>();
        Map<Long, String> mapElementBuf = new HashMap<>();

        for (ClosedQuestion closedQuestion : generatedTest.getClosedQuestionList()) {
            bufList.clear();
            mapElementBuf.clear();

            for (Answer answer : closedQuestion.getAnswerList()) {
                mapElementBuf.put(answer.getId(), answer.getContent());
                bufList.add(mapElementBuf);
            }
            closedQuestionMap.put(closedQuestion.getContent(), bufList);
        }
        for (OpenQuestion openQuestion : generatedTest.getOpenQuestionList()) {
            openQuestionList.add(openQuestion.getContent());
        }
    }
}
