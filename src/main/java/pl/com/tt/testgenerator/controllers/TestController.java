package pl.com.tt.testgenerator.controllers;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import pl.com.tt.testgenerator.dto.CurrentQuestionDto;
import pl.com.tt.testgenerator.dto.OneQuestionDto;
import pl.com.tt.testgenerator.dto.TestDto;
import pl.com.tt.testgenerator.entieties.Answer;
import pl.com.tt.testgenerator.entieties.GeneratedTest;
import pl.com.tt.testgenerator.entieties.TestResult;
import pl.com.tt.testgenerator.entieties.enumclasses.RecruitmentState;
import pl.com.tt.testgenerator.repositories.AnswerRepository;
import pl.com.tt.testgenerator.repositories.GeneratedTestRepository;
import pl.com.tt.testgenerator.repositories.RecruiterTableRepository;
import pl.com.tt.testgenerator.repositories.TestResultRepository;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static pl.com.tt.testgenerator.security.SecurityConstants.JWT_SECRET;
@RestController
@RequestMapping("/test")
public class TestController {

    @Autowired
    private GeneratedTestRepository generatedTestRepository;
    @Autowired
    private AnswerRepository answerRepository;
    @Autowired
    private TestResultRepository testResultRepository;
    @Autowired
    private RecruiterTableRepository recruiterTableRepository;

    @PostMapping("/start/{signature}")
    public TestDto recruitTest(HttpServletRequest request, @PathVariable(name = "signature") String signatureToken)
            throws Exception {

        DecodedJWT decodedJWT = JWT.require(Algorithm.HMAC512(JWT_SECRET.getBytes()))
                .build()
                .verify(signatureToken);

        //String subject = decodedJWT.getSubject();

        Claim claim = decodedJWT.getClaim("id");

        Date currentDate = new Date(System.currentTimeMillis());
        GeneratedTest generatedTest = generatedTestRepository.findById(claim.asLong()).orElseThrow(Exception::new);
        if ((!generatedTest.isFinished()) && generatedTest.getValidFrom().after(currentDate) && generatedTest.getValidUntil().before(currentDate)) {
            Map<Long, OneQuestionDto> oneQuestionDTOMap = new HashMap<>();
            HttpSession session = request.getSession();
            session.setAttribute("closeQuestions", oneQuestionDTOMap);
            session.setAttribute("openQuestions", oneQuestionDTOMap);
            session.setAttribute("id", claim.asLong());
            return new TestDto(generatedTest);
        } else {
            throw new Exception();
        }
    }

    @PostMapping("/first")
    public void firstQuestion(HttpServletRequest request, @RequestBody CurrentQuestionDto currentQuestion) throws NullPointerException {
        Map<Long, OneQuestionDto> oneQuestionMap = (Map<Long, OneQuestionDto>) request.getSession(false).getAttribute(currentQuestion.getType());
        oneQuestionMap.put(currentQuestion.getThisQuestionId(), new OneQuestionDto(System.currentTimeMillis()));
        request.getSession().setAttribute(currentQuestion.getType(), oneQuestionMap);
    }

    @PostMapping("/next")
    public void nextQuestion(HttpServletRequest request, @RequestBody CurrentQuestionDto currentQuestion) throws NullPointerException {
        Map<Long, OneQuestionDto> oneQuestionMap = (Map<Long, OneQuestionDto>) request.getSession(false).getAttribute(currentQuestion.getType());
        OneQuestionDto oneQuestionDTO = oneQuestionMap.get(currentQuestion.getThisQuestionId());
        long interval = System.currentTimeMillis() - oneQuestionDTO.getTime();

        if (!currentQuestion.isMarkedAnswer()) {
            oneQuestionDTO.setTime(interval);
        }

        oneQuestionDTO.setAnswer(currentQuestion.getAnswer());

        if (currentQuestion.isMarkedAnswer() || interval >= ((currentQuestion.getType().equals("closeQuestions")) ? 60000 : 120000)) {
            oneQuestionDTO.setAnswered(true);
        }
        oneQuestionMap.put(currentQuestion.getThisQuestionId(), oneQuestionDTO);

        if (currentQuestion.getNextQuestionId() != null) {
            oneQuestionMap.put(currentQuestion.getNextQuestionId(), new OneQuestionDto(System.currentTimeMillis()));
        }

        request.getSession().setAttribute(currentQuestion.getType(), oneQuestionMap);
    }

    @PostMapping("/repeat")
    public void repeatQuestion(HttpServletRequest request, @RequestBody CurrentQuestionDto currentQuestion) throws NullPointerException {
        Map<Long, OneQuestionDto> oneQuestionMap = (Map<Long, OneQuestionDto>) request.getSession(false).getAttribute(currentQuestion.getType());
        OneQuestionDto oneQuestionDTO = oneQuestionMap.get(currentQuestion.getThisQuestionId());
        oneQuestionDTO.setTime(System.currentTimeMillis() - oneQuestionDTO.getTime());
        oneQuestionMap.put(currentQuestion.getThisQuestionId(), oneQuestionDTO);
        request.getSession().setAttribute(currentQuestion.getType(), oneQuestionMap);
    }

    @PostMapping("/answerlist")
    public Map<String, Map<Long, OneQuestionDto>> answerList(HttpServletRequest request) throws NullPointerException {
        Map<String, Map<Long, OneQuestionDto>> listMap = new HashMap<>();
        listMap.put("CloseQuestions", (Map<Long, OneQuestionDto>) request.getSession(false).getAttribute("closeQuestions"));
        listMap.put("OpenQuestions", (Map<Long, OneQuestionDto>) request.getSession(false).getAttribute("openQuestions"));
        return listMap;
    }


    @Transactional
    @PostMapping("/finish")
    public void saveResult(HttpServletRequest request) throws Exception {
    HttpSession session = request.getSession(false);

    int points = 0;
    TestResult testResult = new TestResult();
        testResult.setGeneratedTest(generatedTestRepository.findById((Long) session.getAttribute("id"))
                .orElseThrow(Exception::new));
                Long[] closeAnswerArray = ((Map<Long, OneQuestionDto>) session.getAttribute("closeQuestions"))
        .values().stream().map(e -> Long.parseLong(e.getAnswer())).toArray(Long[]::new);

        testResult.setCloseQuestionAnswers(closeAnswerArray);

        testResult.setOpenQuestionAnswers(((Map<Long, OneQuestionDto>) session.getAttribute("openQuestions"))
        .values().stream().map(OneQuestionDto::getAnswer).toArray(String[]::new));

        for (Answer answer : answerRepository.allSelectedAnswers(closeAnswerArray)) {
        if (answer.isCorrect()) {
        points++;
        }
        }
        testResult.setCloseAnswerPoints(points);
        testResultRepository.save(testResult);
        recruiterTableRepository.setState(testResult.getId(), RecruitmentState.WAITING_FOR_REVIEW);
        session.invalidate();
        }

    /*
        @Transactional
        @PostMapping("/finish")
        public void saveResult(@RequestBody FinishedTestDto finishedTest, HttpServletRequest request) throws Exception {
            int points = 0;
            TestResult testResult = new TestResult();
            testResult.setGeneratedTest(generatedTestRepository.findById(finishedTest.getId()).get());
            testResult.setCloseQuestionAnswers(finishedTest.getClosedAnswersTable());
            testResult.setOpenQuestionAnswers(finishedTest.getOpenAnswersTable());

            for (Answer answer : answerRepository.allSelectedAnswers(finishedTest.getClosedAnswersTable())) {
                if (answer.isCorrect()) {
                    points++;
                }
            }
            testResult.setCloseAnswerPoints(points);
            testResultRepository.save(testResult);
            recruiterTableRepository.setState(testResult.getId(), RecruitmentState.WAITING_FOR_REVIEW);
            //request.getSession().invalidate();
        }

        @PostMapping("/sesja")
    public  Map<String,Long> trySession(HttpServletRequest request){
            Map<String,Long> map =  new HashMap<>();
            map.put("Data", System.currentTimeMillis());

            request.getSession().setAttribute("list",map);

    return map;
        }


            return  map;
        }

        @PostMapping("/sesjadestroy")
        public void destroySession(HttpServletRequest request){
            request.getSession().invalidate();
        }

     */
@PostMapping("/sesjaadd")
public Map<String, Long> trySessionAgain(HttpServletRequest request) throws NullPointerException {

@SuppressWarnings("unchecked")
        Map<String, Long> map = (Map<String, Long>) request.getSession(false).getAttribute("list");

        map.put(System.currentTimeMillis() + "", System.currentTimeMillis() - map.get("Data"));
        request.setAttribute("list", map);
        return map;
        }
        }

