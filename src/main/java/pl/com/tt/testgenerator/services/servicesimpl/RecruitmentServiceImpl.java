package pl.com.tt.testgenerator.services.servicesimpl;

import com.auth0.jwt.JWT;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.com.tt.testgenerator.TestGeneratorApplication;
import pl.com.tt.testgenerator.dto.GeneralDataDto;
import pl.com.tt.testgenerator.dto.RecruiterTableDto;
import pl.com.tt.testgenerator.entieties.*;
import pl.com.tt.testgenerator.entieties.enumclasses.RecruitmentState;
import pl.com.tt.testgenerator.repositories.*;
import pl.com.tt.testgenerator.services.MailService;
import pl.com.tt.testgenerator.services.RecruitmentService;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.*;

import static com.auth0.jwt.algorithms.Algorithm.HMAC512;
import static pl.com.tt.testgenerator.TestGeneratorApplication.HOST_NAME;
import static pl.com.tt.testgenerator.security.SecurityConstants.JWT_SECRET;

@Service
public class  RecruitmentServiceImpl implements RecruitmentService {

    @Autowired
    private ClosedQuestionRepository closedQuestionRepository;

    @Autowired
    private OpenQuestionRepository openQuestionRepository;

    @Autowired
    private RecruiterTableRepository recruiterTableRepository;

    @Autowired
    private GeneratedTestRepository generatedTestRepository;

    @Autowired
    private GeneralDataDto generalDataDto;

    @Autowired
    private TestResultRepository testResultRepository;

    @Autowired
    MailService mailService;

    private static <T> List<T> losulosu(int size, List<T> questionList) {
        Random random = new Random();
        int buff;
        int randomNumber;
        List<T> buffList = new ArrayList();
        int numberOfQuestion = questionList.size();
        int[] questionArray = new int[numberOfQuestion];
        for (int i = 1; i <= numberOfQuestion; i++) {
            questionArray[i - 1] = i;
        }
        for (int i = 0; i < size; i++) {
            randomNumber = random.nextInt(questionArray.length - i);
            buff = questionArray[randomNumber];
            questionArray[randomNumber] = questionArray[questionArray.length - i - 1];
            buffList.add(questionList.get(buff));
            // questionArray[questionArray.length - i-1] = buff;
        }
        return buffList;
    }

    @Override
    public GeneralDataDto basicData() {
        return generalDataDto;
    }

    @Override
    public void saveRecruit(RecruiterTableDto recruiterTableDto) {

        RecruiterTable recruiterTable = new RecruiterTable();
        BeanUtils.copyProperties(recruiterTableDto, recruiterTable);
        recruiterTable.setState(RecruitmentState.NEW);
        recruiterTableRepository.save(recruiterTable);
    }

    @Override
    public RecruiterTableDto edit(long id) throws Exception {
        RecruiterTable recruiterTable = recruiterTableRepository.findById(id).orElseThrow(() -> new Exception());
        RecruiterTableDto recruiterTableDto = new RecruiterTableDto();
        BeanUtils.copyProperties(recruiterTable, recruiterTableDto);
        recruiterTableDto.setVerificationId(id);
        return recruiterTableDto;
    }

    @Override
    public void saveEdit(RecruiterTableDto recruiterTableDto) throws Exception {
        RecruiterTable recruiterTable = recruiterTableRepository.findById(recruiterTableDto.getVerificationId()).orElseThrow(() -> new Exception());
        BeanUtils.copyProperties(recruiterTableDto, recruiterTable);
        recruiterTableRepository.save(recruiterTable);
    }

    @Override
    public void delete(long id) {
        recruiterTableRepository.deleteById(id);
    }

    @Transactional
    @Override
    public void generateTest(long id, Date fromDate, Date untilDate) throws Exception {
        RecruiterTable recruit = recruiterTableRepository.findById(id).orElseThrow(Exception::new);
        GeneratedTest generatedTest = new GeneratedTest();

        String token = JWT.create()
                //   .withExpiresAt(untilDate)
                .withClaim("id", recruit.getId())
                .withSubject(recruit.getCandidateName() + "_" + recruit.getCandidateSurname())
                .sign(HMAC512(JWT_SECRET.getBytes()));

        //dodać losulosu
        List<ClosedQuestion> appropriateClosedQuestions = closedQuestionRepository.findAppropriateQuestions(
                recruit.getSkillLevel(),
                recruit.getTechnology(),
                recruit.getProgrammingLanguage(),
                recruit.getFramework());
        appropriateClosedQuestions = losulosu(TestGeneratorApplication.CLOSED_QUESTION_SIZE, appropriateClosedQuestions);

        List<OpenQuestion> appropriateOpenQuestions = openQuestionRepository.findAppropriateQuestions(
                recruit.getSkillLevel(),
                recruit.getTechnology(),
                recruit.getProgrammingLanguage(),
                recruit.getFramework());
        appropriateOpenQuestions = losulosu(TestGeneratorApplication.OPEN_QUESTION_SIZE, appropriateOpenQuestions);


        generatedTest.setRecruiterTable(recruit);
        generatedTest.setValidFrom(fromDate);
        generatedTest.setValidUntil(untilDate);
        generatedTest.setClosedQuestionList(appropriateClosedQuestions);
        generatedTest.setOpenQuestionList(appropriateOpenQuestions);
        generatedTest.setLink(token);
        generatedTestRepository.save(generatedTest);
        recruiterTableRepository.setState(id, RecruitmentState.WAITING_FOR_TEST_RESULT);

        StringBuilder stringBuilder = new StringBuilder();
        URL resource = this.getClass().getClassLoader().getResource("static/start.html");
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(resource.openStream()));
        while (bufferedReader.readLine() != null) {
            stringBuilder.append(bufferedReader.readLine());
        }
        bufferedReader.close();
        String text = stringBuilder.toString();
        text = text.replaceFirst("\\$link\\$", token);
        mailService.sendMail(recruit.getCandidateEmail(), "", HOST_NAME + "/test/start/" + text);
    }

    @Override
    public Map<RecruiterTable, TestResult> allRrecrutations() {
        List<RecruiterTable> recruiterTables = recruiterTableRepository.findAll();
        Map<RecruiterTable, TestResult> map = new HashMap<>();
        recruiterTables.forEach(r -> map.put(r, testResultRepository.findById(r.getId()).orElse(null)));
        return map;
    }
}