package pl.com.tt.testgenerator.services;

import pl.com.tt.testgenerator.dto.GeneralDataDto;
import pl.com.tt.testgenerator.dto.RecruiterTableDto;
import pl.com.tt.testgenerator.entieties.RecruiterTable;
import pl.com.tt.testgenerator.entieties.TestResult;

import java.util.Date;
import java.util.Map;

public interface RecruitmentService {

    GeneralDataDto basicData();

    void generateTest(long id, Date fromDate, Date untilDate) throws Exception;

    void saveRecruit(RecruiterTableDto recruiterTableDto);

    RecruiterTableDto edit(long id) throws Exception;

    void saveEdit(RecruiterTableDto recruiterTableDto) throws Exception;

    void delete(long id);

    Map<RecruiterTable, TestResult> allRrecrutations();
}
