package pl.com.tt.testgenerator.controllers;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import pl.com.tt.testgenerator.dto.GeneralDataDto;
import pl.com.tt.testgenerator.dto.GenerateTestDto;
import pl.com.tt.testgenerator.dto.RecruiterTableDto;
import pl.com.tt.testgenerator.entieties.RecruiterTable;
import pl.com.tt.testgenerator.entieties.TestResult;
import pl.com.tt.testgenerator.repositories.ProgrammingLanguageRepository;
import pl.com.tt.testgenerator.repositories.RecruiterTableRepository;
import pl.com.tt.testgenerator.repositories.UsersRepository;
import pl.com.tt.testgenerator.services.RecruitmentService;
import pl.com.tt.testgenerator.services.StorageService;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;

@RestController
public class RecruitmentController {

    @Autowired
    private RecruitmentService recruitmentService;

    @Autowired
    RecruiterTableRepository recruiterTableRepository;

    @Autowired
    ProgrammingLanguageRepository programmingLanguageRepository;

    @Autowired
    UsersRepository usersRepository;

    @Autowired
    StorageService storageService;

    //dokonczyc
    @PostMapping("/save")
    public void saveRecruiter(@Valid RecruiterTableDto recruiterTableDto) {
        storageService.store(recruiterTableDto.getCvFile());
        RecruiterTable recruiterTable = new RecruiterTable();
        recruiterTable.setCandidateEmail(recruiterTableDto.getCvFile().getName());
        BeanUtils.copyProperties(recruiterTableDto, recruiterTable);
        recruiterTableRepository.save(recruiterTable);
/*
        recruiterTable.setProgrammingLanguage(programmingLanguageRepository.findByName("Java"));
        recruiterTable.setSkillLevel(SkillLevel.JUNIOR);
        recruiterTable.setCandidateEmail("ffsdfs@gmail.com");
        recruiterTable.setCreatedTime(new Date(System.currentTimeMillis()));
        recruiterTable.setCvFiles("fwefewf");
        recruiterTable.setCoordinator(usersRepository.findByUsername(coordinator));
        recruiterTable.setRecruiter(usersRepository.findByUsername(recruiter));

        recruiterTable.setTargetClient("TT");
        recruiterTable.setTechnology(Technology.BACK_END);
        recruiterTable.setCandidateName("Paweł");
        recruiterTable.setCandidateSurname("Kowalski");
 */
        // return "Udało sie !";
    }

    @PostMapping("/savefile")
    public void saveFile(MultipartFile file) {
        storageService.store(file);
    }

    @GetMapping("/files/{filename:.+}")
    @ResponseBody
    public ResponseEntity<Resource> serveFile(@PathVariable String filename) {

        Resource file = storageService.loadAsResource(filename);
        return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION,
                "attachment; filename=\"" + file.getFilename() + "\"").body(file);
    }

    @PostMapping("/list")
    public List<RecruiterTable> recruiterTableList() {
        return recruiterTableRepository.findAll();
    }
    @PostMapping("/basicdata")
    public GeneralDataDto startRecruitment() {
        return recruitmentService.basicData();
    }

    @PostMapping("/saverecruit")
    public void saveRecruiter(@Valid RecruiterTableDto recruiterTableDto, BindingResult bindingResult) throws Exception {
        if (bindingResult.hasErrors()) {
            throw new Exception();
        } else {
            recruitmentService.saveRecruit(recruiterTableDto);
        }
    }

    @PostMapping("/edit")
    public RecruiterTableDto edit(@RequestBody long id) throws Exception {
        return recruitmentService.edit(id);
    }

    @PostMapping("/saveedit")
    public void saveEdit(@Valid RecruiterTableDto recruiterTableDto, BindingResult bindingResult) throws Exception {
        if (bindingResult.hasErrors()) {
            throw new Exception();
        } else {
            recruitmentService.saveEdit(recruiterTableDto);
        }
    }

    @PostMapping("/delete")
    public void delete(@RequestBody() long id) {
        recruitmentService.delete(id);
    }

    @PostMapping("/generatetest")
    public void setGeneratedTestRepository(@RequestBody GenerateTestDto generateTestDto, BindingResult bindingResult) throws Exception {
        if (bindingResult.hasErrors()) {
            throw new Exception();
        } else {
            // dodać zmienne
            try {
                recruitmentService.generateTest(generateTestDto.getId(), generateTestDto.getValidFrom(), generateTestDto.getValidUntil());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @GetMapping("/allrecrutations")
    public Map<RecruiterTable, TestResult> allRrecrutations() {
        return recruitmentService.allRrecrutations();
    }
}


