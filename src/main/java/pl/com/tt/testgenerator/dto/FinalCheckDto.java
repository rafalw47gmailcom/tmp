package pl.com.tt.testgenerator.dto;

import lombok.Data;

@Data
public class FinalCheckDto {

    private long id;
    private String[] comments;
    private int openQuestionAnswersPoints;
}
