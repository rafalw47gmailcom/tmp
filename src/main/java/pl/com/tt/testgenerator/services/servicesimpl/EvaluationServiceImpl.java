package pl.com.tt.testgenerator.services.servicesimpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.com.tt.testgenerator.dto.FinalCheckDto;
import pl.com.tt.testgenerator.dto.OpenAnswerCheckDto;
import pl.com.tt.testgenerator.entieties.OpenQuestion;
import pl.com.tt.testgenerator.entieties.TestResult;
import pl.com.tt.testgenerator.entieties.enumclasses.RecruitmentState;
import pl.com.tt.testgenerator.repositories.GeneratedTestRepository;
import pl.com.tt.testgenerator.repositories.RecruiterTableRepository;
import pl.com.tt.testgenerator.repositories.TestResultRepository;
import pl.com.tt.testgenerator.services.EvaluationService;
import pl.com.tt.testgenerator.services.MailService;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

@Service
public class EvaluationServiceImpl implements EvaluationService {

    @Autowired
    private GeneratedTestRepository generatedTestRepository;
    @Autowired
    private TestResultRepository testResultRepository;
    @Autowired
    private RecruiterTableRepository recruiterTableRepository;
    @Autowired
    private MailService mailService;


    @Override
    public OpenAnswerCheckDto checkOpenAnswer(long id) {
        String[] answers = testResultRepository.findById(id).get().getOpenQuestionAnswers();
        List<String> content = new ArrayList<>();
        List<String> suggestedAnswers = new ArrayList<>();

        List<OpenQuestion> questions = generatedTestRepository.findById(id).get().getOpenQuestionList();
        for (OpenQuestion openQuestion : questions) {
            content.add(openQuestion.getContent());
            suggestedAnswers.add(openQuestion.getSuggestedAnswer());
        }
        return new OpenAnswerCheckDto(content, suggestedAnswers, answers);
    }

    @Override
    @Transactional
    public int[] finalCheck(FinalCheckDto finalCheckDto) throws Exception {
        TestResult testResult = testResultRepository.findById(finalCheckDto.getId()).orElseThrow(Exception::new);
        int sumPoints = testResult.getCloseAnswerPoints() + finalCheckDto.getOpenQuestionAnswersPoints();
        testResultRepository.setFinalCheck(finalCheckDto.getComments(), finalCheckDto.getOpenQuestionAnswersPoints(),
                sumPoints, finalCheckDto.getId());
        return new int[]{testResult.getCloseAnswerPoints(), finalCheckDto.getOpenQuestionAnswersPoints(),
                sumPoints};
    }

    @Override
    @Transactional
    public void summary(long id, String finalComment) throws IOException {
        //testResultRepository.setFinalComment(finalComment, id);
        TestResult testResult = testResultRepository.findById(id).get();
        int sumPoints = testResult.getSumPoints();
        testResult.setFinalComment(finalComment);
        recruiterTableRepository.setState(id, RecruitmentState.FINISHED);
//moze zamienic miejscami

        StringBuilder stringBuilder = new StringBuilder();
        URL resource = this.getClass().getClassLoader().getResource("finish/start.html");
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(resource.openStream()));
        while (bufferedReader.readLine() != null) {
            stringBuilder.append(bufferedReader.readLine());
        }
        bufferedReader.close();
        String text = stringBuilder.toString();
        text = text.replaceFirst("\\$points\\$", String.valueOf(sumPoints));
        text = text.replaceFirst("\\$accepted\\$", (sumPoints > 30) ? "" : "nie");
        mailService.sendMail(recruiterTableRepository.findById(id).get().getCandidateEmail(), "", text);
    }
}
